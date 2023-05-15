package com.myhome.repository.LandPrice;

import com.myhome.collection.LandPrice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LandPriceRepository extends MongoRepository<LandPrice, String> {
}
