package com.myhome.repository.LandPrice;

import com.myhome.collection.LandPrice;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LandPriceRepository extends MongoRepository<LandPrice, ObjectId> {
}
