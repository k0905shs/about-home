package com.myhome.model.openApi;

import lombok.*;

public class OpenApiDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class landPriceRequestParam {
        private String pnu;
        private String stdrYear;
        private final String format = "json";
        private final int numOfRows = 1;
        private final int pageNo = 1;

        public landPriceRequestParam(String pnu, String stdrYear) {
            this.pnu = pnu;
            this.stdrYear = stdrYear;
        }



    }


}
