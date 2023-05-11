package com.myhome.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseFormat {
    JSON("json"),
    XML("xml");

    private final String type; //response 타입
}
