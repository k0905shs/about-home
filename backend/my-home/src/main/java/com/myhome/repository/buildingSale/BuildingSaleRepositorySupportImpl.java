package com.myhome.repository.buildingSale;

import com.myhome.collection.BuildingSale;
import com.myhome.model.homeCheck.HomeCheckDto;
import com.myhome.type.BuildingType;
import com.myhome.util.BuildingCodeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        String buildingCode = checkBuildingSaleParam.getBuildingCode();
        BuildingType buildingType = checkBuildingSaleParam.getBuildingType();

        String lawdCd = BuildingCodeUtils.getLawdCd(buildingCode);
        String postCode = BuildingCodeUtils.getPostCode(buildingCode);
        String dealYmd = LocalDate.now().minusMonths(checkBuildingSaleParam.getSearchMonth())
                .format(DateTimeFormatter.ofPattern("YYYYMM"));

        //$match 조건 추가
        MatchOperation filterRequest = Aggregation.match( new Criteria().andOperator(
                Criteria.where("request.dealYmd").gte(dealYmd),
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

        SortOperation sort = Aggregation.sort(Sort.Direction.ASC, "request.dealYmd");

        Aggregation aggregation = newAggregation(
                filterRequest, project, sort
        );

        return mongoTemplate.aggregate(aggregation,"building_sale" ,BuildingSale.class).getMappedResults();
    }
}
