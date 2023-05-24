package com.homeApi.repository.buildingRent;

import com.homeApi.model.homeCheck.HomeCheckDto;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface BuildingRentCustomRepository<T>{
    /**
     * DB에 저장되어 있는 건물 타입별 전월세 정보 조회
     */
    List<T> findBuildingRentList(final HomeCheckDto.checkBuildingRentParam checkBuildingRentParam) throws DataAccessException;

    /**
     * index key duplicated key 예외 발생 가능성 문제
     */
    T save(final T t) throws DataAccessException;
}
