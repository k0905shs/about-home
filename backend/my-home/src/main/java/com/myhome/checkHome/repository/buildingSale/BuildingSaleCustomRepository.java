package com.myhome.checkHome.repository.buildingSale;

import com.myhome.checkHome.model.homeCheck.HomeCheckDto;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface BuildingSaleCustomRepository <T>{
    /**
     * DB에 저장되어 있는 건물 타입별 실 거래가 조회
     */
    List<T> findBuildingSaleList(final HomeCheckDto.checkBuildingSaleParam checkBuildingSaleParam) throws DataAccessException;

    /**
     * index key duplicated key 예외 발생 가능성 문제
     */
    T save(final T t) throws DataAccessException;
}
