package com.myhome.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BuildingType {
    APART("아파트", GovRequestUri.BUILDING_SALE_APART),
    HOUSE("다세대주택 / 연립주택 / 빌라 / 도시형 생활주택", GovRequestUri.BUILDING_SALE_HOUSE),
    OFFICETEL("오피스텔", GovRequestUri.BUILDING_SALE_OFFICETEL),
    TOWNHOUSE("단독/다가구", GovRequestUri.BUILDING_SALE_TOWNHOUSE);

    private final String desc;
    private final GovRequestUri govRequestUri;
}
