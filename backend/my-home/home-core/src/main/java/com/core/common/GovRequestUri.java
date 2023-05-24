package com.core.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 사용중인 공공데이터 uri 정보 및 반환 타입 Enum
 */
@AllArgsConstructor
@Getter
public enum GovRequestUri {
    //공시지가
    LAND_PRICE("apis.data.go.kr", "80","1611000/nsdi/IndvdLandPriceService/attr/getIndvdLandPriceAttr", ResponseFormat.JSON),
    
    //pnu코드
    STAN_REGIN("apis.data.go.kr",  "80", "1741000/StanReginCd/getStanReginCdList", ResponseFormat.JSON),
    
    //아파트 실거래 정보
    BUILDING_SALE_APART("openapi.molit.go.kr", "8081", "OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade", ResponseFormat.XML),
    //단독/다가구 실거래 정보
    BUILDING_SALE_HOUSE("openapi.molit.go.kr", "8081", "OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcSHTrade", ResponseFormat.XML),
    //오피스텔 실거래 정보
    BUILDING_SALE_OFFICETEL("openapi.molit.go.kr", "80", "OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcOffiTrade", ResponseFormat.XML),
    //연립다주택 실거래 정보
    BUILDING_SALE_TOWNHOUSE("openapi.molit.go.kr", "8081", "OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcRHTrade", ResponseFormat.XML),

    //아파트 전/월세 정보
    BUILDING_RENT_APART("openapi.molit.go.kr", "8081", "OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent", ResponseFormat.XML),
    //단독/다가구 전/월세 정보
    BUILDING_RENT_HOUSE("openapi.molit.go.kr", "8081", "OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcSHRent", ResponseFormat.XML),
    //오피스텔 전/월세 정보
    BUILDING_RENT_OFFICETEL("openapi.molit.go.kr", "80", "OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcOffiRent", ResponseFormat.XML),
    //연립다주택 전/월세 정보
    BUILDING_RENT_TOWNHOUSE("openapi.molit.go.kr", "8081", "OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcRHRent", ResponseFormat.XML);


    private final String host; //호스트
    private final String port; //포트 번호
    private final String path; //path 경로
    private final ResponseFormat responseFormat; //response 포맷
}
