package com.myhome.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public enum GovRequestUrl {
    LAND_PRICE("apis.data.go.kr", "1611000/nsdi/IndvdLandPriceService/attr/getIndvdLandPriceAttr"); //국토교통부_개별공시지가정보서비스

    private final String host;
    private final String path;
}
