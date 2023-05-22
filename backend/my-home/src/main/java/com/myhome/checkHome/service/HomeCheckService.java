package com.myhome.checkHome.service;

import com.myhome.checkHome.model.homeCheck.HomeCheckDto;

import java.util.List;

public interface HomeCheckService {

    /**
     * n년치 개별 공시지가 정보 조회
     */
    HomeCheckDto.checkLandPriceResult checkLandPrice(final HomeCheckDto.checkLandPriceParam checkLandPriceParam) throws Exception;

    /**
     *  n개월 치 건물 실거래가 조회
     */
    List<HomeCheckDto.checkBuildingSaleResult> checkBuildingSale(final HomeCheckDto.checkBuildingSaleParam buildingSaleParam) throws Exception;

    /**
     * n개월 치 건물 전/월세 정보 조회
     */
    List<HomeCheckDto.checkBuildingRentResult> checkBuildingRent(final HomeCheckDto.checkBuildingRentParam checkBuildingRentParam) throws Exception;

    /**
     * 건물 검색기록 저장
     */
    HomeCheckDto.searchRecordResult saveRecord(final HomeCheckDto.searchRecordParam searchRecordParam) throws Exception;

    /**
     * 저당권 설정일 기준 해당 년도 최우선 변제권 정보
     */
    HomeCheckDto.checkPriorityRepayResult checkPriorityRepay(final HomeCheckDto.checkPriorityRepayParam checkPriorityRepayParam) throws Exception;
}
