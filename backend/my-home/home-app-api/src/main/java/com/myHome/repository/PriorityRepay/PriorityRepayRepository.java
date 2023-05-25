package com.myHome.repository.PriorityRepay;

import com.myHome.collection.PriorityRepay;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface PriorityRepayRepository extends CrudRepository<PriorityRepay, ObjectId>, PriorityRepayCustomRepository <PriorityRepay>{
}
