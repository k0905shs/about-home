package com.myhome.checkHome.repository.LandPrice;

import com.myhome.checkHome.model.homeCheck.HomeCheckDto;

import java.util.List;

public interface LandPriceCustomRepository <T>{
    /**
     * DB에 저장되어 공시지가 조회
     * @param checkLandPriceParam
     * @return
     */
    List<T> findLandPriceList(final HomeCheckDto.checkLandPriceParam checkLandPriceParam);

    /**
     * index key duplicated key 예외 발생 가능성 문제
     * @param t
     * @return
     */
    T save(final T t);
}
