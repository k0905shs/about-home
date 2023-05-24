package com.homeApi.repository.buildingSale;

import com.core.collection.BuildingSale;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface BuildingSaleRepository extends CrudRepository<BuildingSale, ObjectId> , BuildingSaleCustomRepository<BuildingSale> {

}

