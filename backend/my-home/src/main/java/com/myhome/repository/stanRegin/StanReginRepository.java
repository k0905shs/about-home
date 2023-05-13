package com.myhome.repository.stanRegin;

import com.myhome.collection.StanRegin;

public interface StanReginRepository {

    /**
     * pnu코드 저장
     * @param stanRegin gov response
     */
    void save(StanRegin stanRegin);
    
}
