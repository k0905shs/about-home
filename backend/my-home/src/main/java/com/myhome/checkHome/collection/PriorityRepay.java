package com.myhome.checkHome.collection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.List;

/**
 * 최우선 변제 정보 데이터 Doc
 */
@Getter
@Document("priority_repay")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PriorityRepay {
    @MongoId
    private ObjectId id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String startDate; //정책 시작일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String endDate; // 정책 종료일
    private List<PriorityRepay.policy> policyList;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class policy {
        private String region; //지역 정보
        private BigDecimal deposit; //보증금 범위
        private BigDecimal repayAmount; //변제 금액
    }
}
