package com.myhome.service;

import com.myhome.collection.LandPrice;
import com.myhome.collection.StanRegin;
import com.myhome.model.openApi.BuildingSaleDto;
import com.myhome.model.openApi.LandPriceDto;
import com.myhome.model.openApi.StanReginDto;
import com.myhome.type.BuildingType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class GovServiceTest {

    @Autowired
    private GovService govService;

    @Autowired
    private MongoTemplate mongoTemplate;

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
    public void 공시지가_mongo_저장() throws Exception {
        LandPriceDto.openApiRequestParam requestParam =
                new LandPriceDto.openApiRequestParam("1111017700102110000", "2023", 1, 1);

        LandPriceDto.openApiResponse openApiResponse = govService.requestLandPriceApi(requestParam);
        LandPrice.response response =
                openApiResponse.getField().getLandPriceList().get(0).toDocument();

        //mongo 저장
        LandPrice landPrice = new LandPrice(new LandPrice.request("1111017700102110000", "2023`"), response);
        mongoTemplate.save(landPrice);
    }

    @Test
    void 법정동코드_저장_테스트() throws Exception {
        StanReginDto.openApiRequestParam openApiRequestParam = new StanReginDto.openApiRequestParam("서울특별시 은평구 응암동", 1, 1);
        StanReginDto.openApiResponse response = govService.requestStanReginApi(openApiRequestParam);

        StanRegin.response documentResponse = response.toDocument();
        StanRegin.request documentRequest = new StanRegin.request(openApiRequestParam.getLocataddNm());

        StanRegin stanRegin = new StanRegin(documentRequest, documentResponse);

        StanRegin save = mongoTemplate.save(stanRegin);
        Assertions.assertThat(save.getResponse().getLocataddNm()).isEqualTo(openApiRequestParam.getLocataddNm());
    }

    @Test
    void 실거래자료_저장_테스트() throws Exception{
        BuildingSaleDto.openApiRequestParam openApiRequestParam =
                new BuildingSaleDto.openApiRequestParam("11110", "201512", BuildingType.APART);

        govService.requestBuildingSalesApi(openApiRequestParam);
    }

}