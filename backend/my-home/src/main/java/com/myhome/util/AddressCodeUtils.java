package com.myhome.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 광역시도코드(2자리) + 시/군/구 코드(3자리) + 읍/면/동 코드(3자리) +
 * 리 코드(2자리) + 토지/임야 코드(1자리) +
 * 본번 코드(4자리) + 부번 코드(4자리) = pnu
 */
@Slf4j
public class AddressCodeUtils {

    /**
     * 빌딩코드로 pnu code 확인
     * @param buildingCode 건물 코드
     * @return pnu code
     */
    public static String getPnu(String buildingCode, String jibun) {
        StringBuilder sb = new StringBuilder(buildingCode.substring(0, 11));

        if (jibun.contains("-")) {
            String[] arr = jibun.split("-");
            sb.append(addZero(arr[0], 4));
            sb.append(addZero(arr[1], 4));
        } else {
            sb.append(addZero(jibun, 4)).append("0000");
        }

        return sb.toString();
    }

    /**
     * 건물 코드로 지번 확인
     * @param buildingCode
     * @return
     */
    public static String getPostCode(String buildingCode) {
        //1126010200101260039
        String mainCode = removeZero(buildingCode.substring(11, 15));
        String subCode = removeZero(buildingCode.substring(15, 19));
        return StringUtils.isEmpty(subCode) ? mainCode : mainCode + "-" + subCode;
    }

    /**
     * 건물 코드로 lawd_cd(지역별 코드) 확인
     * @param buildingCode
     * @return
     */
    public static String getLawdCd(String buildingCode) {
        return buildingCode.substring(0, 5);
    }

    /**
     * 입력받은 숫자 코드에서 첫번쨰 숫자가 나오기 전까지 0 제거
     * @param code
     * @return 0을 제거한 코드값
     */
    private static String removeZero(String code) {
        StringBuilder sb = new StringBuilder(code);
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '0') {
                sb.setCharAt(i, ' ');
            } else {
                break;
            }
        }
        return sb.toString().replace(" ", "");
    }

    /**
     * 입력받은 숫자 코드에서 max 자리수 될때가지 앞에 0 추가
     * @param code
     * @return 0을 제거한 코드값
     */
    private static String addZero(String code, int max) {
        StringBuilder sb = new StringBuilder();
        if (code.length() < max) {
            for (int i = 0; i < max - code.length(); i++) {
                sb.append("0");
            }
        }
        return sb.append(code).toString();
    }

}