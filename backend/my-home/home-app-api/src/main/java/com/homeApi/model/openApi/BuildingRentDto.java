package com.homeApi.model.openApi;

import com.core.collection.BuildingRent;
import com.core.common.BuildingType;
import com.core.common.ResponseFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 공공데이터 건물 전/월세 가격 정보 Data Transfer Object
 */
public class BuildingRentDto {

    @Getter
    public static class openApiRequestParam extends GovCommonDto{
        @JsonProperty("LAWD_CD")
        private String lawdCd; // 각 지역별 코드 // 11110
        @JsonProperty("DEAL_YMD")
        private String dealYmd; // 월 단위 신고자료 // 201512
        @JsonIgnore
        private final BuildingType buildingType;

        @Builder
        public openApiRequestParam(String lawdCd, String dealYmd, BuildingType buildingType) {
            super(0, 0, ResponseFormat.XML); //해당 request는 pageNo, numOfRows 중요하지 않음
            this.buildingType = buildingType;
            this.lawdCd = lawdCd;
            this.dealYmd = dealYmd;
        }
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown=true)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class openApiResponse{
        private List<BuildingRentDto.rentInfo> infoList;
        private int totalCount;

        @Builder
        public openApiResponse(List<rentInfo> infoList, int totalCount) {
            this.infoList = infoList;
            this.totalCount = totalCount;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonIgnoreProperties(ignoreUnknown=true)
    public static class rentInfo{
        private BigDecimal rentPrice; //월세금액
        private BigDecimal deposit; //보증금액
        private String buildingName;
        @JsonProperty("년")
        private String year;
        @JsonProperty("월")
        private String month;
        @JsonProperty("일")
        private String day;
        @JsonProperty("지번")
        private String postCode;
        @JsonProperty("지역코드")
        private String reginCode;
        @JsonProperty("법정동")
        private String streetName;
        @JsonProperty("층")
        private String floor;
        @JsonProperty("전용면적")
        private String area;


        @JsonSetter("월세금액")
        public void setRentPrice(String rentPrice) {
            this.rentPrice = new BigDecimal(rentPrice.replace(",", ""));
        }

        @JsonSetter("월세")
        public void setRentPrice1(String rentPrice) {
            this.rentPrice = new BigDecimal(rentPrice.replace(",", ""));
        }

        @JsonSetter("보증금액")
        public void setDeposit(String deposit) {
            this.deposit = new BigDecimal(deposit.replace(",", ""));
        }

        @JsonSetter("보증금")
        public void setDeposit1(String deposit) {
            this.deposit = new BigDecimal(deposit.replace(",", ""));
        }

        @JsonSetter("아파트")
        public void setBuildingName1(String buildingName) {
            this.buildingName = buildingName;
        }
        @JsonSetter("단지")
        public void setBuildingName2(String buildingName) {
            this.buildingName = buildingName;
        }
        @JsonSetter("연립다세대")
        public void setBuildingName3(String buildingName) {
            this.buildingName = buildingName;
        }

        public BuildingRent.detail toDocument(){
            return BuildingRent.detail.builder()
                    .rentPrice(this.rentPrice).deposit(this.deposit)
                    .buildingName(this.buildingName).year(this.year)
                    .month(this.month).day(this.day).postCode(this.postCode)
                    .reginCode(this.reginCode).streetName(this.streetName)
                    .floor(this.floor).area(this.area)
                    .build();
        }
    }
}
