package com.myhome.repository.LandPrice;

import com.myhome.collection.LandPrice;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface LandPriceRepository extends CrudRepository<LandPrice, ObjectId> {



}
