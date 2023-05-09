package com.myhome.openApi;

import com.myhome.model.openApi.LandPriceDto;
import com.myhome.type.GovRequestUrl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GovApiTest {

    @Autowired
    private GovApi govApi;

    @Test
    void requestGov() {
        LandPriceDto.openApiRequestParam requestParam = new LandPriceDto.openApiRequestParam("1111017700102110000", "2015", 10, 1);
        String responseBody = govApi.requestGov(requestParam, GovRequestUrl.LAND_PRICE);
        System.out.println(responseBody);
    }
}