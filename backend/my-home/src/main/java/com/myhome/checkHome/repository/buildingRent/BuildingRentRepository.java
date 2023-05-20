package com.myhome.checkHome.repository.buildingRent;

import com.myhome.checkHome.collection.BuildingRent;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface BuildingRentRepository extends CrudRepository<BuildingRent, ObjectId> , BuildingRentCustomRepository<BuildingRent> {

}

