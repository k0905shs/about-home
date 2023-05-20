package com.myhome.checkHome.model.openApi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.myhome.checkHome.collection.BuildingSale;
import com.myhome.checkHome.type.BuildingType;
import com.myhome.checkHome.type.ResponseFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

public class BuildingSaleDto {

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
        private List<BuildingSaleDto.salesInfo> infoList;
        private int totalCount;

        @Builder
        public openApiResponse(List<salesInfo> infoList, int totalCount) {
            this.infoList = infoList;
            this.totalCount = totalCount;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonIgnoreProperties(ignoreUnknown=true)
    public static class salesInfo{
        private BigDecimal price; //만 단위
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

        @JsonSetter("거래금액")
        public void setPrice(String price) {
            this.price = new BigDecimal(price.replace(" ", "").replace(",", ""));
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

        public BuildingSale.detail toDocument(){
            return BuildingSale.detail.builder()
                    .price(this.price).buildingName(this.buildingName).year(this.year)
                    .month(this.month).day(this.day).postCode(this.postCode)
                    .reginCode(this.reginCode).streetName(this.streetName)
                    .floor(this.floor).area(this.area)
                    .build();
        }
    }
}
