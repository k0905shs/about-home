package com.myhome.repository.buildingRent;

import com.myhome.model.homeCheck.HomeCheckDto;

import java.util.List;

public interface BuildingRentCustomRepository<T>{
    /**
     * DB에 저장되어 있는 건물 타입별 전월세 정보 조회
     * @param checkBuildingRentParam
     * @return
     */
    List<T> findBuildingRentList(final HomeCheckDto.checkBuildingRentParam checkBuildingRentParam);

    /**
     * index key duplicated key 예외 발생 가능성 문제
     * @param t
     * @return
     */
    T save(final T t);
}
