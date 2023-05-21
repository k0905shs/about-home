package com.myhome.checkHome.repository.buildingSale;

import com.myhome.checkHome.collection.BuildingSale;
import com.myhome.checkHome.model.homeCheck.HomeCheckDto;
import com.myhome.checkHome.type.BuildingType;
import com.myhome.util.AddressCodeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.ArrayOperators.Filter.filter;
import static org.springframework.data.mongodb.core.aggregation.ComparisonOperators.Eq.valueOf;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BuildingSaleRepositoryImpl implements BuildingSaleCustomRepository<BuildingSale> {
    //반드시 클래스명은 XXXRepositoryImpl 이어야 Spring data 에서 구현체로 인식할 수 있다.있다

    private final MongoTemplate mongoTemplate;

    @Override
    public List<BuildingSale> findBuildingSaleList(final HomeCheckDto.checkBuildingSaleParam checkBuildingSaleParam) {
        String buildingCode = checkBuildingSaleParam.getBuildingCode();
        BuildingType buildingType = checkBuildingSaleParam.getBuildingType();

        String lawdCd = AddressCodeUtils.getLawdCd(buildingCode);
        String jibun = checkBuildingSaleParam.getJibun();
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
                                .equalToValue(jibun)))
                .as("response.list")
                .andInclude("request");

        SortOperation sort = Aggregation.sort(Sort.Direction.ASC, "request.dealYmd");

        Aggregation aggregation = newAggregation(
                filterRequest, project, sort
        );

        return mongoTemplate.aggregate(aggregation,"building_sale" ,BuildingSale.class).getMappedResults();
    }

    @Override
    public BuildingSale save(BuildingSale buildingSale) {
        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("request.dealYmd").is(buildingSale.getRequest().getDealYmd()),
                        Criteria.where("request.buildingType").is(buildingSale.getRequest().getBuildingType()),
                        Criteria.where("request.lawdCd").is(buildingSale.getRequest().getLawdCd())
                )
        );
        BuildingSale result = mongoTemplate.findOne(query, BuildingSale.class);
        return result == null ? mongoTemplate.save(buildingSale) : result;
    }
}
