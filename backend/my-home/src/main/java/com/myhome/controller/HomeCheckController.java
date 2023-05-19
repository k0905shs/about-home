package com.myhome.controller;

import com.myhome.model.homeCheck.HomeCheckDto;
import com.myhome.service.HomeCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/home/*")
public class HomeCheckController {

    private final HomeCheckService homeCheckService;

    @PostMapping("check-land-price")
    public HomeCheckDto.checkLandPriceResult checkLandPrice(@RequestBody final HomeCheckDto.checkLandPriceParam checkLandPriceParam) throws Exception {
        return homeCheckService.checkLandPrice(checkLandPriceParam);
    }

    @PostMapping("check-building-sale")
    public List<HomeCheckDto.checkBuildingSaleResult> checkBuildingSaleParam(@RequestBody final HomeCheckDto.checkBuildingSaleParam checkBuildingSaleParam) throws Exception {
        return homeCheckService.checkBuildingSale(checkBuildingSaleParam);
    }

    @PostMapping("check-building-rent")
    public List<HomeCheckDto.checkBuildingRentResult> checkBuildingRentParam(@RequestBody final HomeCheckDto.checkBuildingRentParam checkBuildingRentParam) throws Exception {
        return homeCheckService.checkBuildingRent(checkBuildingRentParam);
    }

}
