package com.myhome.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GovRequestUri {
    LAND_PRICE("apis.data.go.kr", "80","1611000/nsdi/IndvdLandPriceService/attr/getIndvdLandPriceAttr", ResponseFormat.JSON), //국토교통부_개별공시지가정보서비스
    STAN_REGIN("apis.data.go.kr",  "80", "1741000/StanReginCd/getStanReginCdList", ResponseFormat.JSON), //행정안전부_행정표준코드_법정동코드
    BUILDING_SALE_APART("openapi.molit.go.kr", "8081", "OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade", ResponseFormat.XML), // 아파트 매매 실거래자료
    BUILDING_SALE_HOUSE("openapi.molit.go.kr", "8081", "OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcSHTrade", ResponseFormat.XML), //단독 다가구 주택 매매 실거래 자료
    BUILDING_SALE_OFFICETEL("openapi.molit.go.kr", "80", "OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcOffiTrade", ResponseFormat.XML),// 오피스텔 매매 실거래자료
    BUILDING_SALE_TOWNHOUSE("openapi.molit.go.kr", "8081", "OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcRHTrade", ResponseFormat.XML); // 연립다세대 매매 실거래자료

    private final String host; //호스트
    private final String port; //포트 번호
    private final String path; //path 경로
    private final ResponseFormat responseFormat; //response 포맷
}
