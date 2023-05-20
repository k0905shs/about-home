package com.myhome.checkHome.model.openApi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myhome.checkHome.type.ResponseFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 공공데이터 포탈 공통 데이터
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GovCommonDto {
    private int numOfRows;
    private int pageNo;
    @JsonIgnore
    private ResponseFormat responseFormat;
}
