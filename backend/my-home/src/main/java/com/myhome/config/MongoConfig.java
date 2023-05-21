package com.myhome.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

@Configuration
//@EnableMongoRepositories(basePackages = "")
public class MongoConfig {
// ref : https://www.baeldung.com/spring-data-mongodb-transactions
    @Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        // TODO 해당옵션 공부가 더 필요해..
        //        TransactionOptions transactionOptions = TransactionOptions.builder().readConcern(ReadConcern.MAJORITY).writeConcern(WriteConcern.W1).build();
        return new MongoTransactionManager(dbFactory);
    }
}

