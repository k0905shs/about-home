package com.myhome.checkHome.repository.PriorityRepay;

import com.myhome.checkHome.collection.PriorityRepay;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface PriorityRepayRepository extends CrudRepository<PriorityRepay, ObjectId>, PriorityRepayCustomRepository <PriorityRepay>{
}
