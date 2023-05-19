package com.myhome.model.homeCheck;

import com.myhome.collection.BuildingRent;
import com.myhome.collection.BuildingSale;
import com.myhome.type.BuildingType;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class HomeCheckDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class checkLandPriceParam {
        @Size(min = 20)@NotBlank
        private String buildingCode; //1111017700102110000
        @Size(min = 1)@NotBlank
        private String jibun;
        @Min(1)
        private int searchYear; //총 검색 년도
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class checkLandPriceResult {
        private long count;
        private List<landPriceInfo> landPriceInfoList;

        @Builder
        public checkLandPriceResult(List<landPriceInfo> landPriceInfoList, long count) {
            this.count = count;
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
        @Size(min = 1)
        @NotBlank
        private String jibun;
        @Size(min = 20)
        @NotBlank
        private String buildingCode;
        @Min(1)
        private int searchMonth; //총 검색 월
        @NotBlank
        private BuildingType buildingType; //건물 타입
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class checkBuildingSaleResult {
        private String date; //거래 월
        private BigDecimal totalPrice;
        private int count;
        private List<buildingSaleInfo> buildingSaleInfoList;

        // entity to Dto
        public checkBuildingSaleResult(BuildingSale buildingSale) {
            this.date = buildingSale.getRequest().getDealYmd();
            this.buildingSaleInfoList = buildingSale.getResponse().getList().stream()
                    .map(buildingSaleInfo::new).collect(Collectors.toList());
            this.totalPrice = buildingSale.getResponse().getList().stream()
                    .map(BuildingSale.detail::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            this.count = buildingSale.getResponse().getList().size();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class buildingSaleInfo{
        private BigDecimal price; //거래 가격
        private String saleDate; //거래 일자
        private String floor; //층수
        private String area; //전용 면적

        //building_sale 도큐먼트의 detail 데이터를 dto로 변환
        public buildingSaleInfo(BuildingSale.detail buildingSaleDetail) {
            this.floor = buildingSaleDetail.getFloor();
            this.price = buildingSaleDetail.getPrice();
            this.saleDate = buildingSaleDetail.getMonth() + "-" + buildingSaleDetail.getDay();
            this.area = buildingSaleDetail.getArea();
        }

    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    public static class checkBuildingRentParam {
        @Size(min = 1)
        @NotBlank
        private String jibun;

        @Size(min = 20)
        @NotBlank
        private String buildingCode;

        @Min(1)
        private int searchMonth; //총 검색 월

        @NotBlank
        private BuildingType buildingType; //건물 타입
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class checkBuildingRentResult {
        private String date; //거래 월
        private List<buildingRentInfo> buildingSaleInfoList;

        // entity to Dto
        public checkBuildingRentResult(BuildingRent BuildingRent) {
            this.date = BuildingRent.getRequest().getDealYmd();
            this.buildingSaleInfoList = BuildingRent.getResponse().getList().stream()
                    .map(buildingRentInfo::new).collect(Collectors.toList());
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class buildingRentInfo{
        private String saleDate; //거래 일자
        private String floor; //층수
        private String area; //전용 면적
        private BigDecimal deposit;
        private BigDecimal rentPrice;

        //building_sale 도큐먼트의 detail 데이터를 dto로 변환
        public buildingRentInfo(BuildingRent.detail buildingRentDetail) {
            this.floor = buildingRentDetail.getFloor();
            this.rentPrice = buildingRentDetail.getRentPrice();
            this.deposit = buildingRentDetail.getDeposit();
            this.saleDate = buildingRentDetail.getMonth() + "-" + buildingRentDetail.getDay();
            this.area = buildingRentDetail.getArea();
        }

    }
}
