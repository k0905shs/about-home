package com.myhome.checkHome.service;

import com.myhome.checkHome.model.openApi.BuildingRentDto;
import com.myhome.checkHome.model.openApi.BuildingSaleDto;
import com.myhome.checkHome.model.openApi.LandPriceDto;
import com.myhome.checkHome.model.openApi.StanReginDto;

/**
 * 공공데이터 api 요청 서비스
 */
public interface GovService {

    /**
     * 국토교통부_개별공시지가정보서비스
     */
    LandPriceDto.openApiResponse requestLandPriceApi(final LandPriceDto.openApiRequestParam requestParam) throws Exception;

    /**
     * 국토교통부 건물 카테고리별 매매 실거래자료
     */
    BuildingSaleDto.openApiResponse requestBuildingSaleApi(final BuildingSaleDto.openApiRequestParam requestParam) throws Exception;

    /**
     * 국토교통부 건물 카테고리별 전/월세 자료
     */
    BuildingRentDto.openApiResponse requestBuildingRentApi(final BuildingRentDto.openApiRequestParam requestParam) throws Exception;
    

    /**
     * 행정안전부_행정표준코드_법정동코드
     */
    StanReginDto.openApiResponse requestStanReginApi(final StanReginDto.openApiRequestParam requestParam) throws Exception;

}
