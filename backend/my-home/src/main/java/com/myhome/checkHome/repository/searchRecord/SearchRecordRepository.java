package com.myhome.checkHome.repository.searchRecord;

import com.myhome.checkHome.collection.SearchRecord;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface SearchRecordRepository extends CrudRepository<SearchRecord, ObjectId> {
}
