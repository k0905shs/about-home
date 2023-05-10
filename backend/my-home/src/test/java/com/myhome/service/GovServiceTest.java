package com.myhome.service;

import com.myhome.model.openApi.LandPriceDto;
import com.myhome.model.openApi.StanReginDto;
import com.myhome.openApi.GovApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GovServiceTest {

    @Autowired
    private GovService govService;

    @Test
    void requestLandPriceApi() throws Exception {
        LandPriceDto.openApiRequestParam requestParam = new LandPriceDto.openApiRequestParam("1111017700102110000", "2015", 10, 1);
        LandPriceDto.openApiResponse returnData = govService.requestLandPriceApi(requestParam);
        assertThat(returnData.getField().getLandPriceList().get(0).getPnu()).isEqualTo("1111017700102110000");

    }

    @Test
    void requestStanReginApi() throws Exception {
        StanReginDto.openApiRequestParam requestParam = new StanReginDto.openApiRequestParam("서울특별시 은평구 응암동" , 10, 1);
        StanReginDto.openApiResponse returnData = govService.requestStanReginApi(requestParam);

        assertThat(returnData.getLocataddNm()).isEqualTo("서울특별시 은평구 응암동");
    }
}