package com.myhome.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhome.collection.BuildingSale;
import com.myhome.collection.LandPrice;
import com.myhome.model.openApi.BuildingSaleDto;
import com.myhome.model.openApi.LandPriceDto;
import com.myhome.model.openApi.StanReginDto;
import com.myhome.repository.LandPrice.LandPriceRepository;
import com.myhome.repository.buildingSale.BuildingSaleRepository;
import com.myhome.type.GovRequestUri;
import com.myhome.util.GovApi;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class GovServiceImpl implements GovService{

    private final GovApi govApi;
    private final ObjectMapper objectMapper;
    private final LandPriceRepository landPriceRepository;
    private final BuildingSaleRepository buildingSaleRepository;

    @Override
    public LandPriceDto.openApiResponse requestLandPriceApi(final LandPriceDto.openApiRequestParam requestParam) throws Exception{
        String response = govApi.requestGov(requestParam, GovRequestUri.LAND_PRICE);
        LandPriceDto.openApiResponse openApiResponse = 
                objectMapper.readValue(response, LandPriceDto.openApiResponse.class);

        //request 결과 저장
        LandPrice.response landPriceResponse =
                openApiResponse.getField().getLandPriceList().get(0).toDocument();
        LandPrice.request landPriceRequest
                = new LandPrice.request(requestParam.getPnu(), requestParam.getStdrYear());

        landPriceRepository.save(new LandPrice(landPriceRequest, landPriceResponse));

        return openApiResponse;
    }

    @Override
    public StanReginDto.openApiResponse requestStanReginApi(final StanReginDto.openApiRequestParam requestParam) throws Exception{
        String response = govApi.requestGov(requestParam, GovRequestUri.STAN_REGIN);

        // TODO : 해당 API response를 DTO 로 매핑하기는 데이터 자체가 너무 복잡해서 문자열에서 직접 필요한 데이터만 절삭해서 사용함 추후 가능하면 수정
        StringBuilder json = new StringBuilder(response);
        json.delete(0, json.indexOf("row"))
                .delete(json.indexOf("}"), json.length() - 1)
                .delete(0, json.indexOf("[") + 1);

        return objectMapper.readValue(json.toString(), StanReginDto.openApiResponse.class);
    }

    @Override
    public BuildingSaleDto.openApiResponse requestBuildingSalesApi(BuildingSaleDto.openApiRequestParam requestParam) throws Exception {
        GovRequestUri requestUrl = requestParam.getBuildingType().getGovRequestUri();
        String response = govApi.requestGov(requestParam, requestUrl);

        //XML to Json parsing
        JSONObject jsonObj = XML.toJSONObject(response);
        String json = jsonObj.get("response").toString();

        BuildingSaleDto.openApiResponse openApiResponse =
                objectMapper.readValue(json, BuildingSaleDto.openApiResponse.class);

        //request 결과 저장
        List<BuildingSaleDto.salesInfo> infoList =
                openApiResponse.getField().getBuildingSales().getInfoList();

        List<BuildingSale.detail> responseList =
                infoList.stream().map(BuildingSaleDto.salesInfo::toDocument).collect(Collectors.toList());
        BuildingSale.request request = new BuildingSale.request(requestParam.getLawdCd(), requestParam.getDealYmd(), requestParam.getBuildingType());
        BuildingSale buildingSale = new BuildingSale(request, responseList);
        buildingSaleRepository.save(buildingSale);

        return openApiResponse;
    }
}
