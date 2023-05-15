package com.myhome.model.homeCheck;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class HomeCheckDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class checkLandPriceParam {
        private String buildingCode; //1111017700102110000
        private String address; //주소
        private int searchYear; //총 검색 년도
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class checkLandPriceResult {
        private String pnu;
        private List<landPriceInfo> landPriceInfoList;

        @Builder
        public checkLandPriceResult(String pnu, List<landPriceInfo> landPriceInfoList) {
            this.pnu = pnu;
            this.landPriceInfoList = landPriceInfoList;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class landPriceInfo {
        private String year;
        private String price;

        @Builder
        public landPriceInfo(String year, String price) {
            this.year = year;
            this.price = price;
        }
    }

}
