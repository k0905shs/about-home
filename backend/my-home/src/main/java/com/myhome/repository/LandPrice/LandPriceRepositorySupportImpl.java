package com.myhome.repository.LandPrice;

import com.myhome.collection.LandPrice;
import com.myhome.model.homeCheck.HomeCheckDto;
import com.myhome.model.openApi.LandPriceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LandPriceRepositorySupportImpl implements LandPriceRepositorySupport{

    private final MongoTemplate mongoTemplate;


    @Override
    public List<LandPrice> checkLandPrice(HomeCheckDto.checkLandPriceParam checkLandPriceParam) {
        LocalDate searchDate = LocalDate.now().minusYears(checkLandPriceParam.getYear()); //작년 기준
        final String pnu = checkLandPriceParam.getBuildingCode().substring(0, 19);
        //pnu, year 로 이미 요청한 기록이 있는지 확인
        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("request.pnu").is(pnu),
                        Criteria.where("request.stdrYear").gte(searchDate.getYear())
                )
        ).with(Sort.by(Sort.Direction.ASC, "request.stdrYear"));
        log.info(query.toString());
        return mongoTemplate.find(query, LandPrice.class);
    }
}
