package com.myhome.checkHome.controller;

import com.myhome.checkHome.model.homeCheck.HomeCheckDto;
import com.myhome.checkHome.service.HomeCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/home/*")
public class HomeCheckController {

    private final HomeCheckService homeCheckService;

    /**
     * n년치 개별 공시지가 조회 End Point
     */
    @PostMapping("check-land-price")
    public HomeCheckDto.checkLandPriceResult checkLandPrice(@RequestBody @Validated final HomeCheckDto.checkLandPriceParam checkLandPriceParam) throws Exception {
        return homeCheckService.checkLandPrice(checkLandPriceParam);
    }

    /**
     * n년치 건물 실거래가 조회 End Point
     */
    @PostMapping("check-building-sale")
    public List<HomeCheckDto.checkBuildingSaleResult> checkBuildingSaleParam(@RequestBody @Validated final HomeCheckDto.checkBuildingSaleParam checkBuildingSaleParam) throws Exception {
        return homeCheckService.checkBuildingSale(checkBuildingSaleParam);
    }

    /**
     * n년치 건물 전/월세 조회 End Point
     */
    @PostMapping("check-building-rent")
    public List<HomeCheckDto.checkBuildingRentResult> checkBuildingRentParam(@RequestBody @Validated final HomeCheckDto.checkBuildingRentParam checkBuildingRentParam) throws Exception {
        return homeCheckService.checkBuildingRent(checkBuildingRentParam);
    }

    /**
     * 검색기록 저장 End Point
     */
    @PostMapping("search-record")
    public HomeCheckDto.searchRecordResult saveRecord(@RequestBody @Validated final HomeCheckDto.searchRecordParam searchRecordParam) throws Exception{
        return homeCheckService.saveRecord(searchRecordParam);
    }

    /**
     * 최우선 변제권 정보 조회 End Point
     */
    @PostMapping("check-priority-repay")
    public HomeCheckDto.checkPriorityRepayResult checkPriorityRepay(@RequestBody @Validated final HomeCheckDto.checkPriorityRepayParam checkPriorityRepayParam) throws Exception{
        return homeCheckService.checkPriorityRepay(checkPriorityRepayParam);
    }

}
