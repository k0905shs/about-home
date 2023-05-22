package com.myhome.checkHome.repository.LandPrice;

import com.myhome.checkHome.model.homeCheck.HomeCheckDto;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface LandPriceCustomRepository <T>{
    /**
     * DB에 저장되어 공시지가 조회
     */
    List<T> findLandPriceList(final HomeCheckDto.checkLandPriceParam checkLandPriceParam) throws DataAccessException;

    /**
     * index key duplicated key 예외 발생 가능성 문제
     */
    T save(final T t) throws DataAccessException;
}
