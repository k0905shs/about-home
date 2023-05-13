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
    @Indexed(unique = true)
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

    @Builder
    public StanRegin(String regionCd, String sidoCd, String sggCd, String umdCd, String riCd, String locatjuminCd, String locatjijukCd, String locataddNm, String locatOrder, String locatRm, String locathighCd, String locallowNm, String adptDe) {
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
