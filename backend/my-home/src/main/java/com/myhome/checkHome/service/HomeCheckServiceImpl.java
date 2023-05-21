package com.myhome.checkHome.service;


import com.myhome.checkHome.collection.BuildingRent;
import com.myhome.checkHome.collection.BuildingSale;
import com.myhome.checkHome.collection.LandPrice;
import com.myhome.checkHome.model.homeCheck.HomeCheckDto;
import com.myhome.checkHome.model.openApi.BuildingRentDto;
import com.myhome.checkHome.model.openApi.BuildingSaleDto;
import com.myhome.checkHome.model.openApi.LandPriceDto;
import com.myhome.checkHome.repository.LandPrice.LandPriceRepository;
import com.myhome.checkHome.repository.buildingRent.BuildingRentRepository;
import com.myhome.checkHome.repository.buildingSale.BuildingSaleRepository;
import com.myhome.util.AddressCodeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HomeCheckServiceImpl implements HomeCheckService {

    private final LandPriceRepository landPriceRepository;
    private final BuildingSaleRepository buildingSaleRepository;
    private final BuildingRentRepository buildingRentRepository;
    private final GovService govService;

    @Override
    @Transactional
    public HomeCheckDto.checkLandPriceResult checkLandPrice(HomeCheckDto.checkLandPriceParam checkLandPriceParam) throws Exception {
        List<LandPrice> checkLandPriceList = landPriceRepository.findLandPriceList(checkLandPriceParam);

        String pnu = AddressCodeUtils.getPnu(checkLandPriceParam.getBuildingCode(), checkLandPriceParam.getJibun());
        int searchYear = checkLandPriceParam.getSearchYear();

        if (searchYear != checkLandPriceList.size()) { //요청한 연도 수 만큼의 요청 데이터가 없으면
            //request 기록이 있는 year list 생성
            List<String> savedYearList = checkLandPriceList.stream()
                    .map(v -> v.getRequest().getStdrYear())
                    .collect(Collectors.toList());

            int nowYear = LocalDate.now().getYear(); //올해
            int startYear = nowYear - searchYear; //데이터 요청 시작 연도

            for (int i = startYear; i < nowYear; i++) { //기록이 없는 연도 공공데이터 request
                if (!savedYearList.contains(String.valueOf(i))) {
                    // request 실행
                     govService.requestLandPriceApi(new LandPriceDto.openApiRequestParam(pnu, String.valueOf(i), 1, 1));
                }
            }
            checkLandPriceList = landPriceRepository.findLandPriceList(checkLandPriceParam); //쿼리 재실행
        }

        //entity to dto
        List<HomeCheckDto.landPriceInfo> landPriceInfoList = checkLandPriceList.stream()
                .filter(Objects::nonNull)
                .map(v -> new HomeCheckDto.landPriceInfo(v.getRequest().getStdrYear(), v.getResponse().getPblntfPclnd()))
                .collect(Collectors.toList());

        //공시지가 정보가 있는 데이터 수 확인
        long count = landPriceInfoList.stream().filter(v -> v.getPrice() != null).count();

        return new HomeCheckDto.checkLandPriceResult(landPriceInfoList, count);
    }


    @Override
    @Transactional
    public List<HomeCheckDto.checkBuildingSaleResult> checkBuildingSale(HomeCheckDto.checkBuildingSaleParam checkBuildingSaleParam) throws Exception {
        List<BuildingSale> buildingSaleList =
                buildingSaleRepository.findBuildingSaleList(checkBuildingSaleParam); //입력 받은 param으로 추출한 도큐먼트 리스트

        //Document list -> 검색이 완료된 'dealYmd'
        List<String> searchedDateList = buildingSaleList.stream()
                .map(v -> v.getRequest().getDealYmd())
                .collect(Collectors.toList());

        //검색 시작 년월
        LocalDate searchStartDate = LocalDate.now().minusMonths(checkBuildingSaleParam.getSearchMonth());
        List<String> searchTargetList = new ArrayList<>();

        //검색 시작일을 기준으로 검색해야 하는 날짜 리스트 추가
        for (LocalDate ld = searchStartDate; ld.isBefore(LocalDate.now()); ld = ld.plusMonths(1)) {
            String dealYmd = ld.format(DateTimeFormatter.ofPattern("YYYYMM"));
            if (!searchedDateList.contains(dealYmd)) { //건물 실거래가 api 요청을 하지 않은 년월 리스트 추가
                searchTargetList.add(dealYmd);
            }
        }

        String lawdCd = AddressCodeUtils.getLawdCd(checkBuildingSaleParam.getBuildingCode());
        boolean reCheck = false;
        for (String dealYmd : searchTargetList) { //개별 API 재실행
            BuildingSaleDto.openApiResponse openApiResponse =
                    govService.requestBuildingSaleApi(new BuildingSaleDto.openApiRequestParam(lawdCd, dealYmd, checkBuildingSaleParam.getBuildingType()));
            reCheck = true;
        }

        // 전체 로직 실행 후 request 추가 요청 있었으면 쿼리 재실행
        if (reCheck) {
            buildingSaleList =
                    buildingSaleRepository.findBuildingSaleList(checkBuildingSaleParam); //입력 받은 param으로 추출한 도큐먼트 리스트
        }
        //Document list -> dto List
        return buildingSaleList.stream()
                .map(HomeCheckDto.checkBuildingSaleResult::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<HomeCheckDto.checkBuildingRentResult> checkBuildingRent(HomeCheckDto.checkBuildingRentParam checkBuildingRentParam) throws Exception {
        List<BuildingRent> buildingRentList = buildingRentRepository.findBuildingRentList(checkBuildingRentParam);

        //Document list -> 검색이 완료된 'dealYmd'
        List<String> searchedDateList = buildingRentList.stream()
                .map(v -> v.getRequest().getDealYmd())
                .collect(Collectors.toList());

        //검색 시작 년월
        LocalDate searchStartDate = LocalDate.now().minusMonths(checkBuildingRentParam.getSearchMonth());
        List<String> searchTargetList = new ArrayList<>();

        //검색 시작일을 기준으로 검색해야 하는 날짜 리스트 추가
        for (LocalDate ld = searchStartDate; ld.isBefore(LocalDate.now()); ld = ld.plusMonths(1)) {
            String dealYmd = ld.format(DateTimeFormatter.ofPattern("YYYYMM"));
            if (!searchedDateList.contains(dealYmd)) { //건물 실거래가 api 요청을 하지 않은 년월 리스트 추가
                searchTargetList.add(dealYmd);
            }
        }

        String lawdCd = AddressCodeUtils.getLawdCd(checkBuildingRentParam.getBuildingCode());
        boolean reCheck = false;
        for (String dealYmd : searchTargetList) { //개별 API 재실행
            BuildingRentDto.openApiResponse openApiResponse =
                    govService.requestBuildingRentApi(new BuildingRentDto.openApiRequestParam(lawdCd, dealYmd, checkBuildingRentParam.getBuildingType()));
            reCheck = true;
        }

        // 전체 로직 실행 후 request 추가 요청 있었으면 쿼리 재실행
        if (reCheck) {
            buildingRentList =
                    buildingRentRepository.findBuildingRentList(checkBuildingRentParam); //입력 받은 param으로 추출한 도큐먼트 리스트
        }

        //Document list -> dto List
        return buildingRentList.stream()
                .map(HomeCheckDto.checkBuildingRentResult::new)
                .collect(Collectors.toList());
    }
}
