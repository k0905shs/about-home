package com.homeApi.repository.PriorityRepay;

import com.core.collection.PriorityRepay;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface PriorityRepayRepository extends CrudRepository<PriorityRepay, ObjectId>, PriorityRepayCustomRepository <PriorityRepay>{
}
