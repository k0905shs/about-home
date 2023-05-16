package com.myhome.repository.buildingSale;

import com.myhome.collection.BuildingSale;
import com.myhome.model.homeCheck.HomeCheckDto;

import java.util.List;

public interface BuildingSaleRepositorySupport {

    List<BuildingSale> findBuildingSaleList(final HomeCheckDto.checkBuildingSaleParam checkBuildingSaleParam);

}
