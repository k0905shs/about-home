package com.myhome.collection;

import com.myhome.type.BuildingType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Document(collection = "building_sale")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class BuildingSale {

    @MongoId
    private String id;

    private BuildingSale.request request;
    private BuildingSale.response response;

    public BuildingSale(BuildingSale.request request, List<BuildingSale.detail> detailList) {
        this.id = request.getDealYmd() + request.getBuildingType() + request.getLawdCd();
        this.request = request;
        this.response = new BuildingSale.response(detailList);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class request {
        private String lawdCd; // 각 지역별 코드  11110
        private String dealYmd; // 월 단위 신고자료  201512
        private BuildingType buildingType;

        public request(String lawdCd, String dealYmd, BuildingType buildingType) {
            this.lawdCd = lawdCd;
            this.dealYmd = dealYmd;
            this.buildingType = buildingType;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class response{
        List<detail> list;
        public response(List<detail> list) {
            this.list = list;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class detail{
        private BigDecimal price;
        private String buildingName;
        private String year;
        private String month;
        private String day;
        private String postCode;
        private String reginCode;
        private String streetName;
        private String floor;

        @Builder
        public detail(BigDecimal price, String buildingName, String year, String month, String day, String postCode, String reginCode, String streetName, String floor) {
            this.price = price;
            this.buildingName = buildingName;
            this.year = year;
            this.month = month;
            this.day = day;
            this.postCode = postCode;
            this.reginCode = reginCode;
            this.streetName = streetName;
            this.floor = floor;
        }
    }
}
