package com.homeApi.repository.LandPrice;

import com.core.collection.LandPrice;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface LandPriceRepository extends CrudRepository<LandPrice, ObjectId>, LandPriceCustomRepository<LandPrice>{
}
