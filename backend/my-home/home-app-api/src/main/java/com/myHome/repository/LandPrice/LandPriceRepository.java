package com.myHome.repository.LandPrice;

import com.myHome.collection.LandPrice;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface LandPriceRepository extends CrudRepository<LandPrice, ObjectId>, LandPriceCustomRepository<LandPrice>{
}
