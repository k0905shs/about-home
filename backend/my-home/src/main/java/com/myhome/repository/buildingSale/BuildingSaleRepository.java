package com.myhome.repository.buildingSale;

import com.myhome.collection.BuildingSale;
import com.myhome.model.homeCheck.HomeCheckDto;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BuildingSaleRepository extends CrudRepository<BuildingSale, ObjectId> {

}

