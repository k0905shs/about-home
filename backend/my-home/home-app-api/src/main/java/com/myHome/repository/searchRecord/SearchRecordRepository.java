package com.myHome.repository.searchRecord;

import com.myHome.collection.SearchRecord;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface SearchRecordRepository extends CrudRepository<SearchRecord, ObjectId> {
}
