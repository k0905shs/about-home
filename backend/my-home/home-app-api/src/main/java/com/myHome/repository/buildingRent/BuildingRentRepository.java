package com.myHome.repository.buildingRent;

import com.myHome.collection.BuildingRent;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface BuildingRentRepository extends CrudRepository<BuildingRent, ObjectId>, BuildingRentCustomRepository<BuildingRent> {

}

