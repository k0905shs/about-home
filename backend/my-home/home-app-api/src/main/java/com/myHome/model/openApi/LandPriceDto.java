package com.myHome.model.openApi;

import com.myHome.collection.LandPrice;
import com.myHome.common.ResponseFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * 개별 공시지가 정보 Data Transfer Object
 */
public class LandPriceDto {

    @Getter
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

        public LandPrice.response toDocument() {
            return LandPrice.response.builder()
                    .regstrSeCode(this.regstrSeCode).pnu(this.pnu).ldCodeNm(this.ldCodeNm)
                    .ldCode(this.ldCode).stdrYear(this.stdrYear).stdrMt(this.stdrMt)
                    .pblntfPclnd(this.pblntfPclnd).pblntfDe(this.pblntfDe)
                    .stdLandAt(this.stdLandAt).mnnmSlno(this.mnnmSlno).regstrSeCodeNm(this.regstrSeCodeNm).lastUpdtDt(this.lastUpdtDt)
                    .build();
        }
    }
}
