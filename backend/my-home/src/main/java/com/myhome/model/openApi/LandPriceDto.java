package com.myhome.model.openApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myhome.type.ResponseFormat;
import lombok.*;

import java.util.List;

/**
 * 개별 공시 시가 정보 API 관련 DTO
 * https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15059127
 */
public class LandPriceDto {

    @Getter
    @Setter
    public static class openApiRequestParam extends GovCommonDto{
        private String pnu;
        private String stdrYear;

        @Builder
        public openApiRequestParam(String pnu, String stdrYear, int numOfRows, int pageNo) {
            super(numOfRows, pageNo, ResponseFormat.JSON);
            this.pnu = pnu;
            this.stdrYear = stdrYear;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class openApiResponse {
        @JsonProperty("indvdLandPrices")
        private field field;
    }
    
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonIgnoreProperties(ignoreUnknown=true)
    public static class field{
        @JsonProperty("field")
        List<landPrice> landPriceList;
    }

    @Getter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class landPrice {
        private String regstrSeCode;
        private String pnu;
        private String ldCodeNm;
        private String ldCode;
        private String stdrYear;
        private String stdrMt;
        private String pblntfPclnd;
        private String pblntfDe;
        private String stdLandAt;
        private String mnnmSlno;
        private String regstrSeCodeNm;
        private String lastUpdtDt;
    }
}
