package com.myhome.repository.buildingSale;

import com.myhome.collection.BuildingSale;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BuildingSaleRepository extends MongoRepository<BuildingSale, ObjectId> {
}
