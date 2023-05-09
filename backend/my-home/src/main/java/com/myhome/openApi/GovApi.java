package com.myhome.openApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhome.model.openApi.GovCommonDto;
import com.myhome.type.GovRequestUrl;
import com.myhome.type.ResponseFormat;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Component
public class GovApi {

    /**
     * 공공데이터 request 분기
     *
     * @param requestDto 공공데이터 요청 DTO
     * @param <T>        공공데이터 요청 DTO 상속 받은 DTO
     * @return String
     */
    public <T extends GovCommonDto> String requestGov(final T requestDto, GovRequestUrl url) {
        String responseBody = "";
        if (requestDto.getResponseFormat().equals(ResponseFormat.JSON)) {
            responseBody = responseToJson(requestDto, url);
        } else if (requestDto.getResponseFormat().equals(ResponseFormat.XML)) {
            responseBody = responseToXml(requestDto, url);
        }
        return responseBody;
    }

    /**
     *
     * @return
     */
    private <T extends GovCommonDto> String responseToJson(final T requestDto, GovRequestUrl url) {
        // TODO : 해당 코드 xml api 사용 승인 나면 공통화 진행해야함!!! Util로 넘기는게 목표
        ObjectMapper objectMapper = new ObjectMapper();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Map<String, String> maps = objectMapper.convertValue(requestDto, new TypeReference<Map<String, String>>() {});
        params.setAll(maps);

        URI uri = UriComponentsBuilder.newInstance()
        .scheme("http")
        .host(url.getHost())
        .path(url.getPath())
        .queryParams(params)
        .queryParam("serviceKey", "5TNF1XMQwRcgHri+3ukTRRSzwiUJnr5JP7SBMUscTWORWs3skuuyM3YfJZpXzUD+dE6ITSrQb1YJb29HHNEzng==")
        .queryParam("format", "json")
        .build().encode()
        .toUri();

        uri = this.encodePlus(uri);
        //TODO : xml 파일 파싱 방법 확인하고 공통화 할 수 있으면 공통화 하자
        return WebClient.builder().build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    /**
     *
     * @return
     */
    private <T extends GovCommonDto> String responseToXml(final T requestDto, GovRequestUrl url) {
        return "xml";
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
