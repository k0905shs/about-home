package com.core.collection;

import com.core.common.BuildingType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.util.List;

/**
 * 건물 전/월세 데이터 Doc
 */
@Getter
@Document(collection = "building_rent")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BuildingRent {
    @MongoId
    private ObjectId id;
    @Indexed(unique = true)
    private BuildingRent.request request;
    private BuildingRent.response response;

    public BuildingRent(BuildingRent.request request, List<BuildingRent.detail> detailList) {
        this.request = request;
        this.response = new BuildingRent.response(detailList);
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
        private BigDecimal rentPrice; //월세금액
        private BigDecimal deposit; //보증금액
        private String buildingName; //건물명
        private String year; //연
        private String month; //월
        private String day; //일
        private String postCode; //지번
        private String reginCode; //지역코드
        private String streetName; //법정동
        private String floor; //층
        private String area; //전용면적

        @Builder
        public detail(BigDecimal rentPrice, BigDecimal deposit, String buildingName, String year, String month, String day, String postCode, String reginCode, String streetName, String floor, String area) {
            this.rentPrice = rentPrice;
            this.deposit = deposit;
            this.year = year;
            this.month = month;
            this.day = day;
            this.postCode = postCode;
            this.buildingName = buildingName;
            this.reginCode = reginCode;
            this.streetName = streetName;
            this.floor = floor;
            this.area = area;
        }
    }
}
