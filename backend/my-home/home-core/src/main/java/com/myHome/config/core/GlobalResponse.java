package com.myHome.config.core;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * API Response 에 대한 전역 객체 선언
 */
public class GlobalResponse {
    
    @Getter
    @Setter
    public static class defualtData {
        // API 자체에 대한 실행성공 여부
        protected boolean isSuccess = true;
        // API 종료시간 (Advice 에서 실행시 호출)
        protected LocalDateTime endTime = LocalDateTime.now();
        // Request URI
        protected String path;
    }

    /**
     * 성공시 반환 Class
     */
    @Getter
    @Setter
    public static class success<T> extends defualtData {
        private final T data;
        public success(T data) {
            this.data = data;
        }
    }

    /**
     * 실패시 반환 Class
     */
    @Getter
    @Setter
    public static final class error<T> extends defualtData {
        private final T errors;
        public error(T data) {
            this.errors = data;
        }
    }
}
