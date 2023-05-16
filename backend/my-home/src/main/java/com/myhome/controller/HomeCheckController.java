package com.myhome.controller;

import com.myhome.collection.BuildingSale;
import com.myhome.model.homeCheck.HomeCheckDto;
import com.myhome.repository.LandPrice.LandPriceRepository;
import com.myhome.repository.buildingSale.BuildingSaleRepository;
import com.myhome.repository.buildingSale.BuildingSaleRepositorySupport;
import com.myhome.service.HomeCheckService;
import com.myhome.type.BuildingType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/home/*")
public class HomeCheckController {

    private final HomeCheckService homeCheckService;

    @PostMapping("check-land-price")
    public HomeCheckDto.checkLandPriceResult checkLandPrice(@RequestBody final HomeCheckDto.checkLandPriceParam checkLandPriceParam) throws Exception {
        return homeCheckService.checkLandPrice(checkLandPriceParam);
    }

    @PostMapping("check-building-sale")
    public HomeCheckDto.checkBuildingSaleParam checkBuildingSaleParam(@RequestBody final HomeCheckDto.checkBuildingSaleParam buildingSaleParam) {
        return null;
    }

}
