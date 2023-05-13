package com.myhome.repository.stanRegin;

import com.myhome.collection.StanRegin;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StanReginRepositoryImpl implements StanReginRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public void save(StanRegin stanRegin) {

    }
}

