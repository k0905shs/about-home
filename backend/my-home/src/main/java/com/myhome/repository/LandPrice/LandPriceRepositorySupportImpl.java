package com.myhome.repository.LandPrice;

import com.myhome.collection.LandPrice;
import com.myhome.model.homeCheck.HomeCheckDto;
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
        Query query = new Query();
        int fromYear = LocalDate.now().minusYears(checkLandPriceParam.getSearchYear()).getYear();
        final String pnu = checkLandPriceParam.getBuildingCode().substring(0, 19);

        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("request.pnu").is(pnu),
                        Criteria.where("request.stdrYear").gte(String.valueOf(fromYear))
                )
        ).with(Sort.by(Sort.Direction.ASC, "request.stdrYear"));

        return mongoTemplate.find(query, LandPrice.class);
    }
}
