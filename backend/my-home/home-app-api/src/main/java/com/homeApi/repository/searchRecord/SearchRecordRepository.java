package com.homeApi.repository.searchRecord;

import com.core.collection.SearchRecord;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface SearchRecordRepository extends CrudRepository<SearchRecord, ObjectId> {
}
