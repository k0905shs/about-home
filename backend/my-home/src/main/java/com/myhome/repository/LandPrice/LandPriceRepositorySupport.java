package com.myhome.repository.LandPrice;

import com.myhome.collection.LandPrice;
import com.myhome.model.homeCheck.HomeCheckDto;

import java.util.List;

public interface LandPriceRepositorySupport {
    List<LandPrice> findLandPriceList(final HomeCheckDto.checkLandPriceParam checkLandPriceParam);
}
