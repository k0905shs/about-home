package com.myhome.repository.stanRegin;

import com.myhome.collection.StanRegin;
import com.myhome.model.openApi.StanReginDto;
import com.myhome.service.GovService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class StanReginRepositoryImplTest {

    @Autowired
    private GovService govService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void saveTest() throws Exception {
        StanReginDto.openApiRequestParam openApiRequestParam = new StanReginDto.openApiRequestParam("서울특별시 은평구 응암동", 1, 1);
        StanReginDto.openApiResponse response = govService.requestStanReginApi(openApiRequestParam);

        StanRegin.response documentResponse = response.toDocument();
        StanRegin.request documentRequest = new StanRegin.request(openApiRequestParam.getLocataddNm());

        StanRegin stanRegin = new StanRegin(documentRequest, documentResponse);

        StanRegin save = mongoTemplate.save(stanRegin);
        Assertions.assertThat(save.getResponse().getLocataddNm()).isEqualTo(openApiRequestParam.getLocataddNm());
    }



}