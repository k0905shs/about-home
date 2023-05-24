package com.homeApi.model.homeCheck;

import com.core.collection.BuildingRent;
import com.core.collection.BuildingSale;
import com.core.collection.PriorityRepay;
import com.core.collection.SearchRecord;
import com.core.common.BuildingType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class HomeCheckDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class checkLandPriceParam {
        @Size(min = 20)@NotBlank
        private String buildingCode; //1111017700102110000
        @Size(min = 1)@NotBlank
        private String jibun;
        @Min(1)
        private int searchYear; //총 검색 년도
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class checkLandPriceResult {
        private long count;
        private List<landPriceInfo> landPriceInfoList;

        @Builder
        public checkLandPriceResult(List<landPriceInfo> landPriceInfoList, long count) {
            this.count = count;
            this.landPriceInfoList = landPriceInfoList;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class landPriceInfo {
        private String year;
        private String price;

        @Builder
        public landPriceInfo(String year, String price) {
            this.year = year;
            this.price = price;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    public static class checkBuildingSaleParam {
        @Size(min = 1)
        @NotBlank
        private String jibun;
        @Size(min = 20)
        @NotBlank
        private String buildingCode;
        @Min(1)
        private int searchMonth; //총 검색 월
        @NotNull
        private BuildingType buildingType; //건물 타입

        //FIXME : 검색어 저장을 위한 임시 코드
        public SearchRecord toSearchRecord() {
            return SearchRecord.builder()
                    .buildingCode(this.buildingCode)
                    .sidoCode(this.buildingCode.substring(0, 5))
                    .buildingType(this.buildingType)
                    .jibun(this.jibun)
                    .build();
        }
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class checkBuildingSaleResult {
        private String date; //거래 월
        private BigDecimal totalPrice;
        private int count;
        private List<buildingSaleInfo> buildingSaleInfoList;

        // entity to Dto
        public checkBuildingSaleResult(BuildingSale buildingSale) {
            this.date = buildingSale.getRequest().getDealYmd();
            this.buildingSaleInfoList = buildingSale.getResponse().getList().stream()
                    .map(buildingSaleInfo::new).collect(Collectors.toList());
            this.totalPrice = buildingSale.getResponse().getList().stream()
                    .map(BuildingSale.detail::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            this.count = buildingSale.getResponse().getList().size();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class buildingSaleInfo{
        private BigDecimal price; //거래 가격
        private String saleDate; //거래 일자
        private String floor; //층수
        private String area; //전용 면적

        //building_sale 도큐먼트의 detail 데이터를 dto로 변환
        public buildingSaleInfo(BuildingSale.detail buildingSaleDetail) {
            this.floor = buildingSaleDetail.getFloor();
            this.price = buildingSaleDetail.getPrice();
            this.saleDate = buildingSaleDetail.getMonth() + "-" + buildingSaleDetail.getDay();
            this.area = buildingSaleDetail.getArea();
        }

    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    public static class checkBuildingRentParam {
        @Size(min = 1)
        @NotBlank
        private String jibun;
        @Size(min = 20)
        @NotBlank
        private String buildingCode;
        @Min(1)
        private int searchMonth; //총 검색 월
        @NotNull
        private BuildingType buildingType; //건물 타입
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class checkBuildingRentResult {
        private String date; //거래 월
        private List<buildingRentInfo> buildingSaleInfoList;

        // entity to Dto
        public checkBuildingRentResult(BuildingRent BuildingRent) {
            this.date = BuildingRent.getRequest().getDealYmd();
            this.buildingSaleInfoList = BuildingRent.getResponse().getList().stream()
                    .map(buildingRentInfo::new).collect(Collectors.toList());
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class buildingRentInfo{
        private String saleDate; //거래 일자
        private String floor; //층수
        private String area; //전용 면적
        private BigDecimal deposit;
        private BigDecimal rentPrice;

        //building_sale 도큐먼트의 detail 데이터를 dto로 변환
        public buildingRentInfo(BuildingRent.detail buildingRentDetail) {
            this.floor = buildingRentDetail.getFloor();
            this.rentPrice = buildingRentDetail.getRentPrice();
            this.deposit = buildingRentDetail.getDeposit();
            this.saleDate = buildingRentDetail.getMonth() + "-" + buildingRentDetail.getDay();
            this.area = buildingRentDetail.getArea();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class searchRecordParam{
        @Size(min = 20)@NotBlank
        private String buildingCode; //빌딩 코드
        @Size(min = 1)@NotBlank
        private String jibun; //지번
        @NotNull
        private BuildingType buildingType;

        public SearchRecord toDocument() {
            return SearchRecord.builder()
                    .buildingCode(this.buildingCode)
                    .jibun(this.jibun)
                    .sidoCode(this.buildingCode.substring(0,5))
                    .buildingType(buildingType)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class searchRecordResult{
        private boolean success;
        public searchRecordResult(boolean success) {
            this.success = success;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class checkPriorityRepayParam{
        // TODO 나중에 추가적으로 필드값 받아서 검색하면 해당 데이터 기반 최우선 변제금 추출 로직 개발
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate targetDate;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class checkPriorityRepayResult{
        private String startDate; //정책 시작일
        private String endDate; //정책 종료일
        List<HomeCheckDto.checkPriorityPolicy> policyList; //정책 리스트

        public checkPriorityRepayResult(PriorityRepay priorityRepay) {
            this.startDate = priorityRepay.getStartDate();
            this.endDate = priorityRepay.getEndDate();
            this.policyList = priorityRepay.getPolicyList().stream()
                    .map(checkPriorityPolicy::new).collect(Collectors.toList());

        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class checkPriorityPolicy{
        private String region; //지역 정보
        private BigDecimal deposit; //보증금 범위
        private BigDecimal repayAmount; //변제 금액

        public checkPriorityPolicy(PriorityRepay.policy policy) {
            this.region = policy.getRegion();
            this.deposit = policy.getDeposit();
            this.repayAmount = policy.getRepayAmount();
        }
    }
}
