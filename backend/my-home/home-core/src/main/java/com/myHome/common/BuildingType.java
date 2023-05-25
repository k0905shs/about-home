package com.myHome.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 조회가 가능한 건물 타입 Enum
 */
@AllArgsConstructor
@Getter
public enum BuildingType {
    APART("아파트", GovRequestUri.BUILDING_SALE_APART, GovRequestUri.BUILDING_RENT_APART),
    TOWNHOUSE("다세대주택 / 연립주택 / 빌라 / 도시형 생활주택", GovRequestUri.BUILDING_SALE_TOWNHOUSE, GovRequestUri.BUILDING_RENT_TOWNHOUSE),
    OFFICETEL("오피스텔", GovRequestUri.BUILDING_SALE_OFFICETEL, GovRequestUri.BUILDING_RENT_OFFICETEL),
    HOUSE("단독/다가구", GovRequestUri.BUILDING_SALE_HOUSE, GovRequestUri.BUILDING_RENT_HOUSE);

    private final String desc; //설명
    private final GovRequestUri buildingSaleUri; // 실거래 정보
    private final GovRequestUri buildingRentUri; // 전/월세 정보
}
