package com.myhome.repository.LandPrice;

import com.myhome.collection.LandPrice;
import com.myhome.model.homeCheck.HomeCheckDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;


@Slf4j
@Repository
@RequiredArgsConstructor
public class LandPriceRepositorySupportImpl implements LandPriceRepositorySupport{

    private final MongoTemplate mongoTemplate;


    @Override
    public List<LandPrice> findLandPriceList(HomeCheckDto.checkLandPriceParam checkLandPriceParam) {
        int fromYear = LocalDate.now().minusYears(checkLandPriceParam.getSearchYear()).getYear();
        final String pnu = checkLandPriceParam.getBuildingCode().substring(0, 19);

        //$match 조건 추가
        MatchOperation filterRequest = Aggregation.match( new Criteria().andOperator(
                Criteria.where("request.pnu").is(pnu),
                Criteria.where("request.stdrYear").gte(String.valueOf(fromYear))
        ));

        Aggregation aggregation = newAggregation(
                filterRequest
        );

        return mongoTemplate.aggregate(aggregation,"land_price" , LandPrice.class).getMappedResults();
    }
}
