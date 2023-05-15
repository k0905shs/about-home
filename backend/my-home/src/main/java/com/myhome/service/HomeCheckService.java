package com.myhome.service;

import com.myhome.model.homeCheck.HomeCheckDto;

public interface HomeCheckService {

    HomeCheckDto.checkLandPriceResult checkLandPrice(final HomeCheckDto.checkLandPriceParam checkLandPriceParam);

}
