package com.myhome.repository.buildingSale;

import com.myhome.collection.BuildingSale;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface BuildingSaleRepository extends CrudRepository<BuildingSale, ObjectId> {

}

