package com.myhome.repository.buildingSale;

import com.myhome.collection.BuildingSale;
import com.myhome.model.homeCheck.HomeCheckDto;
import com.myhome.type.BuildingType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;


import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.ArrayOperators.Filter.filter;
import static org.springframework.data.mongodb.core.aggregation.ComparisonOperators.Eq.valueOf;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BuildingSaleRepositorySupportImpl implements BuildingSaleRepositorySupport{

    private final MongoTemplate mongoTemplate;

    @Override
    public List<BuildingSale> findBuildingSaleList(final HomeCheckDto.checkBuildingSaleParam checkBuildingSaleParam) {
        String lawdCd = checkBuildingSaleParam.getBuildingCode().substring(0,5);
        BuildingType buildingType = checkBuildingSaleParam.getBuildingType();
        String postCode = checkBuildingSaleParam.getPostCode();

        //$match 조건 추가
        MatchOperation filterRequest = Aggregation.match( new Criteria().andOperator(
                Criteria.where("request.dealYmd").gte("201512"), //TODO : 년월 입력 변경
                Criteria.where("request.buildingType").is(buildingType),
                Criteria.where("request.lawdCd").is(lawdCd)
        ));

        //$project : response - list인에 데이터 필터링
        ProjectionOperation project = Aggregation.project()
                .and(filter("response.list")
                        .as("item")
                        .by(valueOf("item.postCode")
                                .equalToValue(postCode)))
                .as("response.list")
                .andInclude("request");

        Aggregation aggregation = newAggregation(
                filterRequest, project
        );

        return mongoTemplate.aggregate(aggregation,"building_sale" ,BuildingSale.class).getMappedResults();
    }
}
