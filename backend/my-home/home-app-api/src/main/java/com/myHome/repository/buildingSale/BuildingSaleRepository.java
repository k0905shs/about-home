package com.myHome.repository.buildingSale;

import com.myHome.collection.BuildingSale;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface BuildingSaleRepository extends CrudRepository<BuildingSale, ObjectId> , BuildingSaleCustomRepository<BuildingSale> {

}

