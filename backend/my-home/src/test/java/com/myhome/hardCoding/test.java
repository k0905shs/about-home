package com.myhome.hardCoding;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhome.model.openApi.OpenApiDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

//https://annajin.tistory.com/101
//https://jojoldu.tistory.com/478
@SpringBootTest
public class test {

    @Test
    public void test() {

        ObjectMapper objectMapper = new ObjectMapper();
        // uribuild 설정을 도와주는 DefaultUriBUilderFactory 호출
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory("http://apis.data.go.kr/1611000/nsdi/IndvdLandPriceService/attr/getIndvdLandPriceAttr");
        // 인코딩 모드 설정
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        //dto to MultiValueMap
        OpenApiDto.landPriceRequestParam dto = new OpenApiDto.landPriceRequestParam("1111017700102110000", "2022");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Map<String, String> maps = objectMapper.convertValue(dto, new TypeReference<Map<String, String>>() {});
        params.setAll(maps);
        //webclient set factory
        WebClient webClient = WebClient.builder()
                .uriBuilderFactory(factory)
                .build();

        String response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("serviceKey", "tQIqgRtAf22u8t9SYXWTRMEvKjDh%2FPVT%2BTLbGh5Z78EXB56FF%2FhSXtEyzbziprw6fbfigOFTgmXre3raot2XOg%3D%3D")
                        .queryParams(params)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println(response);
    }

}
