package com.myhome.service;


import com.myhome.collection.LandPrice;
import com.myhome.model.homeCheck.HomeCheckDto;
import com.myhome.model.openApi.LandPriceDto;
import com.myhome.repository.LandPrice.LandPriceRepositorySupport;
import com.myhome.util.GovApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HomeCheckServiceImpl implements HomeCheckService{

    private final GovApi govApi;
    private final LandPriceRepositorySupport landPriceRepositorySupport;
    private final GovService govService;

    @Override
    @Transactional
    public HomeCheckDto.checkLandPriceResult checkLandPrice(HomeCheckDto.checkLandPriceParam checkLandPriceParam) throws Exception{
        List<LandPrice> checkLandPriceList = landPriceRepositorySupport.checkLandPrice(checkLandPriceParam);

        List<HomeCheckDto.landPriceInfo> landPriceInfoList =
                checkLandPriceList.stream()
                        .map(LandPrice::getResponse)
                        .map(v -> new HomeCheckDto.landPriceInfo(v.getStdrYear(), v.getPblntfPclnd()))
                        .collect(Collectors.toList());

        String pnu = checkLandPriceParam.getBuildingCode().substring(0, 19); //pnu코드
        int searchYear = checkLandPriceParam.getSearchYear();

        // TODO : 뎁스 너무 커서 해당 로직 반드시 리펙토링 해야함!
        if (searchYear != checkLandPriceList.size()) { //요청한 연도 수 만큼의 요청 데이터가 없으면
            //request 기록이 있는 year list 생성
            List<String> savedYearList = checkLandPriceList.stream()
                    .map(v -> v.getRequest().getStdrYear())
                    .collect(Collectors.toList());

            int nowYear = LocalDate.now().getYear(); //올해
            int startYear = nowYear - searchYear; //데이터 요청 시작 연도

            for (int i = startYear; i < nowYear; i++) { //기록이 없는 연도 공공데이터 request
                if(!savedYearList.contains(String.valueOf(i))){
                    //공공데이터 request 실행
                    LandPriceDto.openApiResponse openApiResponse =
                            govService.requestLandPriceApi(new LandPriceDto.openApiRequestParam(pnu, String.valueOf(i), 1, 1));

                    //db에 저장 안돼있는 데이터 추가
                    LandPriceDto.landPrice landPrice = openApiResponse.getField().getLandPriceList().get(0);
                    landPriceInfoList.add(new HomeCheckDto.landPriceInfo(landPrice.getStdrYear(), landPrice.getPblntfPclnd()));
                }
            }
        }

        return new HomeCheckDto.checkLandPriceResult(pnu, landPriceInfoList);
    }
}
