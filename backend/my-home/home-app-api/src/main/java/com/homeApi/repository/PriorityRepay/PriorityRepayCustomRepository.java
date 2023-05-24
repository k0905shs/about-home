package com.homeApi.repository.PriorityRepay;
import com.homeApi.model.homeCheck.HomeCheckDto;
import org.springframework.dao.DataAccessException;

public interface PriorityRepayCustomRepository <T>{

    /**
     * 건물 저당일 기준 해당년도 최우선변제권 정보 반환
     */
    T findPolicyByTargetDate(HomeCheckDto.checkPriorityRepayParam checkPriorityRepayParam) throws DataAccessException;

}
