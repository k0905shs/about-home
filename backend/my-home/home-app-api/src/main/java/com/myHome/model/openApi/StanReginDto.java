package com.myHome.model.openApi;

import com.myHome.collection.StanRegin;
import com.myHome.common.ResponseFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

/**
 * 행정안전부_행정표준코드_법정동코드 API 관련 DTO
 * https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15077871
 */
public class StanReginDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class openApiRequestParam extends GovCommonDto {
        @JsonProperty("locatadd_nm")
        private String locataddNm;

        @Builder
        public openApiRequestParam(String locataddNm, int numOfRows, int pageNo) {
            super(numOfRows, pageNo, ResponseFormat.JSON);
            this.locataddNm = locataddNm;
        }
    }

    @Getter
    @ToString
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class openApiResponse {
        private String regionCd;
        private String sidoCd;
        private String sggCd;
        private String umdCd;
        private String riCd;
        private String locatjuminCd;
        private String locatjijukCd;
        private String locataddNm;
        private String locatOrder;
        private String locatRm;
        private String locathighCd;
        private String locallowNm;
        private String adptDe;

        public StanRegin.response toDocument() {
            return StanRegin.response.builder()
                    .regionCd(this.regionCd).sidoCd(this.sidoCd).sggCd(this.sggCd)
                    .umdCd(this.getUmdCd()).riCd(this.riCd)
                    .locatjuminCd(this.locatjuminCd).locatjijukCd(this.locatjijukCd)
                    .locataddNm(this.locataddNm).locatOrder(this.locatOrder)
                    .locatRm(this.locatRm).locathighCd(this.locathighCd)
                    .locallowNm(this.locallowNm).adptDe(this.adptDe)
                    .build();
        }
    }
}