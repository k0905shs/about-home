package com.myhome.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhome.model.openApi.GovCommonDto;
import com.myhome.type.GovRequestUri;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

/**
 * 실제 공공데이터 data request 를 보내는 컴포넌트
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GovApi {

    @Value("${gov.service.key}")
    private String serviceKey;
    private final ObjectMapper objectMapper;

    /**
     * 공공데이터 reqeust
     * @param requestDto 공공데이터 요청 DTO
     * @param <T>        공공데이터 요청 DTO 상속 받은 DTO
     * @return String
     */
    public <T extends GovCommonDto> String requestGov(final T requestDto, GovRequestUri govRequestUri) {
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
