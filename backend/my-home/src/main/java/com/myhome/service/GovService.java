package com.myhome.service;

import com.myhome.model.openApi.BuildingSaleDto;
import com.myhome.model.openApi.LandPriceDto;
import com.myhome.model.openApi.StanReginDto;

/**
 * 공공데이터 api 요청 서비스
 */
public interface GovService {

    /**
     * 국토교통부_개별공시지가정보서비스
     * @param requestParam
     * @return openApiResponse
     * @throws Exception
     */
    LandPriceDto.openApiResponse requestLandPriceApi(final LandPriceDto.openApiRequestParam requestParam) throws Exception;

    /**
     * 국토교통부 건물 카테고리별 매매 실거래자료
     * @param requestParam
     * @return openApiResponse
     * @throws Exception
     */
    BuildingSaleDto.openApiResponse requestBuildingSalesApi(final BuildingSaleDto.openApiRequestParam requestParam) throws Exception;

    /**
     * 행정안전부_행정표준코드_법정동코드
     * @param requestParam
     * @return openApiResponse
     * @throws Exception
     */
    StanReginDto.openApiResponse requestStanReginApi(final StanReginDto.openApiRequestParam requestParam) throws Exception;
}
