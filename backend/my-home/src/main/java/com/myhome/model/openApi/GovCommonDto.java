package com.myhome.model.openApi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myhome.type.ResponseFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GovCommonDto {
    private int numOfRows;
    private int pageNo;
    @JsonIgnore
    private ResponseFormat responseFormat;
}
