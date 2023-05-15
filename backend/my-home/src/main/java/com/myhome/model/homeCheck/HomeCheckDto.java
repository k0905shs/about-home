package com.myhome.model.homeCheck;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class HomeCheckDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class checkLandPriceParam {
        private String buildingCode;
        private String address;
        private int year;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class checkLandPriceResult {

    }

}
