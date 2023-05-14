package com.myhome.collection;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "stan_regin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StanRegin {

    @Id
    private String id;
    private request request;
    private response response;

    @Builder
    public StanRegin(StanRegin.request request, StanRegin.response response) {
        this.id = id;
        this.request = request;
        this.response = response;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class request{
        private String locataddNm;

        @Builder
        public request(String locataddNm) {
            this.locataddNm = locataddNm;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class response{
        @Indexed(unique = true)
        private String regionCd; //1138010700 '리'가 없으니까 뒤에 00
        private String sidoCd; //11
        private String sggCd; //380
        private String umdCd; // 107
        private String riCd; //00
        private String locatjuminCd ;//1138010700
        private String locatjijukCd; //1138010700
        private String locataddNm; //서울특별시 은평구 응암동
        private String locatOrder;
        private String locatRm;
        private String locathighCd;
        private String locallowNm;
        private String adptDe;

        @Builder
        public response(String regionCd, String sidoCd, String sggCd, String umdCd, String riCd, String locatjuminCd, String locatjijukCd, String locataddNm, String locatOrder, String locatRm, String locathighCd, String locallowNm, String adptDe) {
            this.regionCd = regionCd;
            this.sidoCd = sidoCd;
            this.sggCd = sggCd;
            this.umdCd = umdCd;
            this.riCd = riCd;
            this.locatjuminCd = locatjuminCd;
            this.locatjijukCd = locatjijukCd;
            this.locataddNm = locataddNm;
            this.locatOrder = locatOrder;
            this.locatRm = locatRm;
            this.locathighCd = locathighCd;
            this.locallowNm = locallowNm;
            this.adptDe = adptDe;
        }
    }
}


