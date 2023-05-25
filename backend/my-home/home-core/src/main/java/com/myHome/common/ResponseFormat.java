package com.myHome.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * response데이터 반환 타입 Enum
 */
@AllArgsConstructor
@Getter
public enum ResponseFormat {
    JSON("json"),
    XML("xml");

    private final String type; //response 타입
}
