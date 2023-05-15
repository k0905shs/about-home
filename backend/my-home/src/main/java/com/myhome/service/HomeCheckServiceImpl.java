package com.myhome.service;


import com.myhome.collection.LandPrice;
import com.myhome.model.homeCheck.HomeCheckDto;
import com.myhome.model.openApi.LandPriceDto;
import com.myhome.util.GovApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
    private final MongoTemplate mongoTemplate;


    @Override
    @Transactional
    public HomeCheckDto.checkLandPriceResult checkLandPrice(HomeCheckDto.checkLandPriceParam checkLandPriceParam) {
        LocalDate searchDate = LocalDate.now().minusYears(checkLandPriceParam.getYear());
        final String pnu = checkLandPriceParam.getBuildingCode().substring(0, 19);
        System.out.println(pnu);
        System.out.println(searchDate.getYear());
        //pnu, year 로 이미 요청한 기록이 있는지 확인
        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("request.pnu").is(pnu),
                        Criteria.where("request.stdrYear").gte(searchDate.getYear())
                )
        ).with(Sort.by(Sort.Direction.ASC, "request.stdrYear"))
        ;

        System.out.println(query.toString());

        List<LandPrice> landPriceList = mongoTemplate.find(query,LandPrice.class);
        log.info("{}", landPriceList.size());
        return null;
    }
}
