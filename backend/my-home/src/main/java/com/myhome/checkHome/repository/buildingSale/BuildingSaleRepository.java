package com.myhome.checkHome.repository.buildingSale;

import com.myhome.checkHome.collection.BuildingSale;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface BuildingSaleRepository extends CrudRepository<BuildingSale, ObjectId> , BuildingSaleCustomRepository<BuildingSale> {

}

