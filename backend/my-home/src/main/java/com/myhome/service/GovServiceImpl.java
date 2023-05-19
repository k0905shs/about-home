package com.myhome.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhome.collection.BuildingSale;
import com.myhome.collection.LandPrice;
import com.myhome.model.openApi.BuildingSaleDto;
import com.myhome.model.openApi.GovCommonDto;
import com.myhome.model.openApi.LandPriceDto;
import com.myhome.model.openApi.StanReginDto;
import com.myhome.repository.LandPrice.LandPriceRepository;
import com.myhome.repository.buildingSale.BuildingSaleRepository;
import com.myhome.type.GovRequestUri;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GovServiceImpl implements GovService{

    @Value("${gov.service.key}")
    private String serviceKey;

    private final ObjectMapper objectMapper;
    private final LandPriceRepository landPriceRepository;
    private final BuildingSaleRepository buildingSaleRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public LandPriceDto.openApiResponse requestLandPriceApi(final LandPriceDto.openApiRequestParam requestParam) throws Exception{
        String response = this.requestGov(requestParam, GovRequestUri.LAND_PRICE); //request 요청
        LandPriceDto.openApiResponse openApiResponse =
                objectMapper.readValue(response, LandPriceDto.openApiResponse.class);

        LandPrice.response landPriceResponse = null;

        //response 결과 저장
        if(openApiResponse.getField().getLandPriceList().size() != 0){
            landPriceResponse = openApiResponse.getField().getLandPriceList().get(0).toDocument();
        }

        //request 결과 저장
        LandPrice.request landPriceRequest
                = new LandPrice.request(requestParam.getPnu(), requestParam.getStdrYear());

        landPriceRepository.save(new LandPrice(landPriceRequest, landPriceResponse));

        return openApiResponse;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public BuildingSaleDto.openApiResponse requestBuildingSalesApi(BuildingSaleDto.openApiRequestParam requestParam) throws Exception {
        GovRequestUri requestUrl = requestParam.getBuildingType().getGovRequestUri();
        String response = this.requestGov(requestParam, requestUrl); //request 요청

        //XML to Json parsing
        JSONObject jsonObj = XML.toJSONObject(response);
        JsonNode jsonNode = objectMapper.readTree(jsonObj.get("response").toString());

        List<BuildingSaleDto.salesInfo> openApiList = new ArrayList<>();
        int totalCount = jsonNode.path("body").get("totalCount").asInt();
        jsonNode = objectMapper.readTree(jsonNode.path("body").get("items").toString());

        if (totalCount == 1) { //한건만 있는 경우 리스트 변환시 예외 발생하므로 따로 관리함
            openApiList.add(objectMapper.readValue(jsonNode.path("item").toString()
                    , new TypeReference<BuildingSaleDto.salesInfo>() {}));
        } else if (totalCount > 1) { //조회 결과가 2건 이상이면
            openApiList = objectMapper.readValue(jsonNode.path("item").toString()
                    , new TypeReference<List<BuildingSaleDto.salesInfo>>(){});
        }

        //response 결과 저장
        BuildingSaleDto.openApiResponse openApiResponse = new BuildingSaleDto.openApiResponse(openApiList, totalCount);

        //mongo에 request : {} , response : {} 형태로 저장
        List<BuildingSale.detail> responseList =
                openApiList.stream().map(BuildingSaleDto.salesInfo::toDocument).collect(Collectors.toList());
        BuildingSale.request request = new BuildingSale.request(requestParam.getLawdCd(), requestParam.getDealYmd(), requestParam.getBuildingType());
        BuildingSale buildingSale = new BuildingSale(request, responseList);
        buildingSaleRepository.save(buildingSale);

        return openApiResponse;
    }

    @Override
    public StanReginDto.openApiResponse requestStanReginApi(final StanReginDto.openApiRequestParam requestParam) throws Exception{
        String response = this.requestGov(requestParam, GovRequestUri.STAN_REGIN);

        // TODO : 해당 API response를 DTO 로 매핑하기는 데이터 자체가 너무 복잡해서 문자열에서 직접 필요한 데이터만 절삭해서 사용함 추후 가능하면 수정
        StringBuilder json = new StringBuilder(response);
        json.delete(0, json.indexOf("row"))
                .delete(json.indexOf("}"), json.length() - 1)
                .delete(0, json.indexOf("[") + 1);

        return objectMapper.readValue(json.toString(), StanReginDto.openApiResponse.class);
    }



    /**
     * 공공데이터 reqeust
     * @param requestDto 공공데이터 요청 DTO
     * @param <T>        공공데이터 요청 DTO 상속 받은 DTO
     * @return String
     */
    private  <T extends GovCommonDto> String requestGov(final T requestDto, GovRequestUri govRequestUri) {
        //requestDto to MultiValueMap
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Map<String, String> maps = objectMapper.convertValue(requestDto, new TypeReference<Map<String, String>>() {});
        params.setAll(maps);

        //uri build
        URI uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(govRequestUri.getHost())
                .port(govRequestUri.getPort())
                .path(govRequestUri.getPath())
                .queryParams(params)
                .queryParam("serviceKey", serviceKey)
                .queryParam("format", govRequestUri.getResponseFormat().getType())
                .queryParam("type", govRequestUri.getResponseFormat().getType())
                .build().encode()
                .toUri();

        // '+' 인코딩 이슈 해결
        uri = this.encodePlus(uri);

        String response =
                WebClient.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(20 * 1024 * 1024)) //DataBufferLimitException  이슈
                        .build().get().uri(uri).retrieve().bodyToMono(String.class).block();
        log.info("GOV API Request : {}", uri);
        return response;
    }


    /**
     * UriComponentsBuilder + 가 인코딩 안돼는 문제 해결
     * @param uri + 인코딩 대상 URI
     * @return URI
     */
    private URI encodePlus(URI uri) {
        return UriComponentsBuilder
                .fromUriString(uri.toString().replace("+", "%2B"))
                .build(true)
                .toUri();
    }


}
