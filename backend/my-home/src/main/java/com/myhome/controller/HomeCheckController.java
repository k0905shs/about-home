package com.myhome.controller;

import com.myhome.model.homeCheck.HomeCheckDto;
import com.myhome.service.HomeCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home/*")
public class HomeCheckController {

    private final HomeCheckService homeCheckService;

    @PostMapping("check-land-price")
    public HomeCheckDto.checkLandPriceResult checkLandPrice(@RequestBody final HomeCheckDto.checkLandPriceParam checkLandPriceParam) throws Exception {
        return homeCheckService.checkLandPrice(checkLandPriceParam);
    }



}
