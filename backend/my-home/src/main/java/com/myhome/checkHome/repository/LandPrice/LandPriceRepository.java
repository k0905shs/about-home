package com.myhome.checkHome.repository.LandPrice;

import com.myhome.checkHome.collection.LandPrice;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface LandPriceRepository extends CrudRepository<LandPrice, ObjectId>, LandPriceCustomRepository<LandPrice>{
}
