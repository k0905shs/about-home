package com.myhome.repository.buildingSale;

import com.myhome.model.homeCheck.HomeCheckDto;

import java.util.List;

public interface BuildingSaleCustomRepository <T>{
    /**
     * DB에 저장되어 있는 건물 타입별 실 거래가 조회
     * @param checkBuildingSaleParam
     * @return
     */
    List<T> findBuildingSaleList(final HomeCheckDto.checkBuildingSaleParam checkBuildingSaleParam);
}
