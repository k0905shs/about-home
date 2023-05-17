package com.myhome.service;

import com.myhome.model.homeCheck.HomeCheckDto;

import java.util.List;

public interface HomeCheckService {
    HomeCheckDto.checkLandPriceResult checkLandPrice(final HomeCheckDto.checkLandPriceParam checkLandPriceParam) throws Exception;

    List<HomeCheckDto.checkBuildingSaleResult> checkBuildingSale(final HomeCheckDto.checkBuildingSaleParam buildingSaleParam) throws Exception;
}
