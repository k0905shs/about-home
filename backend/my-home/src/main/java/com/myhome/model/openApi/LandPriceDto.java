package com.myhome.model.openApi;

import com.myhome.type.ResponseFormat;
import lombok.*;

/**
 * 개별 공시 시가 정보 API 관련 DTO
 * https://www.data.go.kr/iim/api/selectAPIAcountView.do
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


}
