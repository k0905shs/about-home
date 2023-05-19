package com.myhome.repository.buildingSale;

import com.myhome.collection.BuildingSale;
import org.springframework.data.repository.CrudRepository;

public interface BuildingSaleRepository extends CrudRepository<BuildingSale, String> , BuildingSaleCustomRepository<BuildingSale> {

}

