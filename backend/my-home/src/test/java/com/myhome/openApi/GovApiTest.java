package com.myhome.openApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhome.model.openApi.BuildingSaleDto;
import com.myhome.model.openApi.LandPriceDto;
import com.myhome.model.openApi.StanReginDto;
import com.myhome.type.BuildingType;
import com.myhome.type.GovRequestUri;
import com.myhome.util.GovApi;
import org.json.JSONObject;
import org.json.XML;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.bind.JAXBException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GovApiTest {

    @Autowired
    private GovApi govApi;

    @Test
    void landPrice() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        LandPriceDto.openApiRequestParam requestParam = new LandPriceDto.openApiRequestParam("1111017700102110000", "2015", 10, 1);
        String response = govApi.requestGov(requestParam, GovRequestUri.LAND_PRICE);
        System.out.println(response);
        LandPriceDto.openApiResponse openApiResponse =
                objectMapper.readValue(response, LandPriceDto.openApiResponse.class);

        //요청한 데이터 정상적으로 왔는지 확인
        assertThat(openApiResponse.getField().getLandPriceList().get(0).getPnu()).isEqualTo("1111017700102110000");
    }

    @Test
    void pnuCode() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        StanReginDto.openApiRequestParam requestParam = new StanReginDto.openApiRequestParam("서울특별시 은평구 응암동" , 10, 1);
        String response= govApi.requestGov(requestParam, GovRequestUri.STAN_REGIN);
        System.out.println(response);

        //해당 API response를 DTO 로 매핑하기는 데이터 자체가 너무 조잡해서 문자열에서 직접 필요한 데이터만 절삭해서 사용
        StringBuilder json = new StringBuilder(response);
        json.delete(0, json.indexOf("row"))
                .delete(json.indexOf("}"), json.length() - 1)
                .delete(0, json.indexOf("[") + 1);

        StanReginDto.openApiResponse openApiResponse =
                objectMapper.readValue(json.toString(), StanReginDto.openApiResponse.class);

        //요청한 데이터 정상적으로 왔는지 확인
        assertThat(openApiResponse.getLocataddNm()).isEqualTo("서울특별시 은평구 응암동");
    }

    @Test
    void buildingSales() throws JAXBException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        BuildingSaleDto.openApiRequestParam requestParam = new BuildingSaleDto.openApiRequestParam("11110" , "201512", BuildingType.APART);
        String response= govApi.requestGov(requestParam, requestParam.getBuildingType().getGovRequestUri());
        System.out.println(response);

        JSONObject jsonObj = XML.toJSONObject(response);
        response = jsonObj.get("response").toString();
        BuildingSaleDto.openApiResponse openApiResponse =
                objectMapper.readValue(response, BuildingSaleDto.openApiResponse.class);
        assertThat(openApiResponse.getField().getBuildingSales().getInfoList().get(0).getStreetCode()).isEqualTo("9");
    }

}