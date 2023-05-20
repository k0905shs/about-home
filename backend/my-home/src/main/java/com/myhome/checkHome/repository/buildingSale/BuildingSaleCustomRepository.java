package com.myhome.checkHome.repository.buildingSale;

import com.myhome.checkHome.model.homeCheck.HomeCheckDto;

import java.util.List;

public interface BuildingSaleCustomRepository <T>{
    /**
     * DB에 저장되어 있는 건물 타입별 실 거래가 조회
     * @param checkBuildingSaleParam
     * @return
     */
    List<T> findBuildingSaleList(final HomeCheckDto.checkBuildingSaleParam checkBuildingSaleParam);

    /**
     * index key duplicated key 예외 발생 가능성 문제
     * @param t
     * @return
     */
    T save(final T t);
}
