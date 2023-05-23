package com.myhome.checkHome.collection;

import com.myhome.checkHome.model.homeCheck.HomeCheckDto;
import com.myhome.checkHome.type.BuildingType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 건물 검색 바디 저장 Doc
 */
@Getter
@Document("search_record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchRecord {

    @MongoId
    private ObjectId id;
    private String buildingCode;
    private String sidoCode;
    private String jibun;
    private BuildingType buildingType;
    private final String searchTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss"));

    @Builder
    public SearchRecord(String buildingCode, String sidoCode, String jibun, BuildingType buildingType) {
        this.buildingCode = buildingCode;
        this.sidoCode = sidoCode;
        this.jibun = jibun;
        this.buildingType = buildingType;
    }

    //TODO : 검색 테이저 저장을 위한 임시 생성자 추후 제거 예정
    public SearchRecord(HomeCheckDto.checkBuildingSaleParam checkBuildingSaleParam) {
        this.buildingType = checkBuildingSaleParam.getBuildingType();
        this.buildingCode = checkBuildingSaleParam.getBuildingCode();
        this.jibun = checkBuildingSaleParam.getJibun();
        this.sidoCode = checkBuildingSaleParam.getBuildingCode().substring(0, 5);
    }
}
