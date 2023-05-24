package com.homeApi.repository.buildingRent;

import com.core.collection.BuildingRent;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface BuildingRentRepository extends CrudRepository<BuildingRent, ObjectId>, BuildingRentCustomRepository<BuildingRent> {

}

