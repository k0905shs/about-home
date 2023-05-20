package com.myhome.checkHome.service;

import com.myhome.checkHome.model.homeCheck.HomeCheckDto;

import java.util.List;

public interface HomeCheckService {
    HomeCheckDto.checkLandPriceResult checkLandPrice(final HomeCheckDto.checkLandPriceParam checkLandPriceParam) throws Exception;

    List<HomeCheckDto.checkBuildingSaleResult> checkBuildingSale(final HomeCheckDto.checkBuildingSaleParam buildingSaleParam) throws Exception;

    List<HomeCheckDto.checkBuildingRentResult> checkBuildingRent(final HomeCheckDto.checkBuildingRentParam checkBuildingRentParam) throws Exception;
}
