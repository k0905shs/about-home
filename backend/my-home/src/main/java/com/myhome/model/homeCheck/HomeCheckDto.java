package com.myhome.model.homeCheck;

import com.myhome.collection.BuildingSale;
import com.myhome.type.BuildingType;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class HomeCheckDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class checkLandPriceParam {
        private String buildingCode; //1111017700102110000
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

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    public static class checkBuildingSaleParam {
        private BuildingType buildingType; //건물 타입
        private String lawdCd; //시군구 코드 41135
        private String postCode; // 지번
        private int searchMonth; //총 검색 월

        @Builder
        public checkBuildingSaleParam(BuildingType buildingType, String lawdCd, String postCode, int searchMonth) {
            this.buildingType = buildingType;
            this.lawdCd = lawdCd;
            this.postCode = postCode;
            this.searchMonth = searchMonth;
        }
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class checkBuildingSaleResult {
        private String date; //거래 월
        private List<buildingSaleInfo> buildingSaleInfoList;

        public checkBuildingSaleResult(BuildingSale buildingSale) {
            this.date = buildingSale.getRequest().getDealYmd();
            this.buildingSaleInfoList = buildingSale.getResponse().getList().stream()
                    .map(buildingSaleInfo::new).collect(Collectors.toList());
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class buildingSaleInfo{
        private BigDecimal price; //거래 가격
        private String saleDate; //거래 일자
        private String floor; //층수

        //building_sale 도큐먼트의 detail 데이터를 dto로 변환
        public buildingSaleInfo(BuildingSale.detail buildingSaleDetail) {
            this.floor = buildingSaleDetail.getFloor();
            this.price = buildingSaleDetail.getPrice();
            this.saleDate = buildingSaleDetail.getMonth() + "-" + buildingSaleDetail.getDay();
        }

    }
}
