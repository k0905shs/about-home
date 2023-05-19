package com.myhome.repository.LandPrice;

import com.myhome.model.homeCheck.HomeCheckDto;

import java.util.List;

public interface LandPriceCustomRepository <T>{
    List<T> findLandPriceList(final HomeCheckDto.checkLandPriceParam checkLandPriceParam);
}
