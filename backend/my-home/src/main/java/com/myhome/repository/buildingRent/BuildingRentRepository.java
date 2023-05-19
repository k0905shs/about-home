package com.myhome.repository.buildingRent;

import com.myhome.collection.BuildingRent;
import org.springframework.data.repository.CrudRepository;

public interface BuildingRentRepository extends CrudRepository<BuildingRent, String> , BuildingRentCustomRepository<BuildingRent> {

}

