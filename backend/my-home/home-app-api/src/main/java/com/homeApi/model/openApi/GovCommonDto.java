package com.homeApi.model.openApi;

import com.core.common.ResponseFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 공공데이터 공통 데이터
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
