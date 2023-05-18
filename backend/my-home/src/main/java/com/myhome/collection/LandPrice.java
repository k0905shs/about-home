package com.myhome.collection;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Document(collection = "land_price")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LandPrice {

    @MongoId
    private String id;

    private LandPrice.request request;
    private LandPrice.response response;

    public LandPrice(LandPrice.request request, LandPrice.response response) {
        this.id = request.getPnu() + request.getStdrYear();
        this.request = request;
        this.response = response != null ? response : new response();
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class request{
        private String pnu;
        private String stdrYear;

        public request(String pnu, String stdrYear) {
            this.pnu = pnu;
            this.stdrYear = stdrYear;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class response{
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

        @Builder
        public response(String regstrSeCode, String pnu, String ldCodeNm, String ldCode, String stdrYear, String stdrMt, String pblntfPclnd, String pblntfDe, String stdLandAt, String mnnmSlno, String regstrSeCodeNm, String lastUpdtDt) {
            this.regstrSeCode = regstrSeCode;
            this.pnu = pnu;
            this.ldCodeNm = ldCodeNm;
            this.ldCode = ldCode;
            this.stdrYear = stdrYear;
            this.stdrMt = stdrMt;
            this.pblntfPclnd = pblntfPclnd;
            this.pblntfDe = pblntfDe;
            this.stdLandAt = stdLandAt;
            this.mnnmSlno = mnnmSlno;
            this.regstrSeCodeNm = regstrSeCodeNm;
            this.lastUpdtDt = lastUpdtDt;
        }
    }
}
