package com.example.mongock;

import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.MongoDatabaseFactory;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@TestConfiguration
public class InitializerContext {

    @Bean
    @Order(1)
    public InitializingBean initData(MongoDatabaseFactory mongoDatabaseFactory) {
        return new InitializingBean() {
            @Override
            public void afterPropertiesSet() throws Exception {

                MongoDatabase mongoDatabase = mongoDatabaseFactory.getMongoDatabase();
                mongoDatabase.getCollection("user").deleteMany(new Document());
                mongoDatabase.getCollection("user").insertMany(
                        Stream.of("alice", "bob", "chris")
                                .map(name -> new Document().append("email", name + "@mail.com"))
                                .collect(toList())
                );
                mongoDatabase.getCollection("summary").deleteMany(new Document());
            }
        };
    }
}
