package com.myhome.service;

import com.myhome.model.openApi.BuildingSaleDto;
import com.myhome.model.openApi.LandPriceDto;
import com.myhome.model.openApi.StanReginDto;
import com.myhome.type.BuildingType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
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

    @Test
    void requestBuildingSalesApi() throws Exception {
        BuildingSaleDto.openApiRequestParam requestParamApart = new BuildingSaleDto.openApiRequestParam("11110" , "201512", BuildingType.APART);
        BuildingSaleDto.openApiRequestParam requestParamHouse = new BuildingSaleDto.openApiRequestParam("11110" , "201512", BuildingType.HOUSE);
        BuildingSaleDto.openApiRequestParam requestParamOfficetel = new BuildingSaleDto.openApiRequestParam("11110", "201512", BuildingType.OFFICETEL);
        BuildingSaleDto.openApiRequestParam requestParamTownHouse = new BuildingSaleDto.openApiRequestParam("11110", "201512", BuildingType.TOWNHOUSE);

        BuildingSaleDto.openApiResponse responseApart = govService.requestBuildingSalesApi(requestParamApart);
        BuildingSaleDto.openApiResponse responseHouse = govService.requestBuildingSalesApi(requestParamHouse);
//        BuildingSaleDto.openApiResponse responseOfficetel = govService.requestBuildingSalesApi(requestParamOfficetel);
        BuildingSaleDto.openApiResponse responseTownHouse = govService.requestBuildingSalesApi(requestParamTownHouse);

        assertThat(responseApart.getField().getBuildingSales().getInfoList().get(0).getReginCode()).isEqualTo("11110");
        assertThat(responseHouse.getField().getBuildingSales().getInfoList().get(0).getReginCode()).isEqualTo("11110");
//        assertThat(responseOfficetel.getField().getBuildingSales().getInfoList().get(0).getReginCode()).isEqualTo("11110");
        assertThat(responseTownHouse.getField().getBuildingSales().getInfoList().get(0).getReginCode()).isEqualTo("11110");

    }

    @Test
    public void pnuSave() {

//        StanReginDto.openApiRequestParam requestParam = new StanReginDto.openApiRequestParam("서울특별시 은평구 응암동" , 10, 1);
//        StanReginDto.openApiResponse returnData = govService.requestStanReginApi(requestParam);


    }

}