package com.myhome.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GovRequestUrl {
    LAND_PRICE("apis.data.go.kr", "1611000/nsdi/IndvdLandPriceService/attr/getIndvdLandPriceAttr"), //국토교통부_개별공시지가정보서비스
    STAN_REGIN("apis.data.go.kr", "1741000/StanReginCd/getStanReginCdList"); //행정안전부_행정표준코드_법정동코드
    private final String host;
    private final String path;
}
