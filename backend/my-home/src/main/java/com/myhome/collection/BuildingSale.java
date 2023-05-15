package com.myhome.collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Document(collection = "building_sale")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BuildingSale {
    @Id
    private String id;

    private BigDecimal price; //만 단위
    private String buildingName;
    private String year;
    private String month;
    private String day;
    private String streetCode;
    private String reginCode;
    private String streetName;
}
