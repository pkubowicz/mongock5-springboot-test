package com.example.migrations;

import com.example.testcontainers.MongoInitializer;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.test.context.ContextConfiguration;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(initializers = MongoInitializer.class)
abstract class BaseCountingMigrationTest {

    @Autowired
    private MigrationExecutor migrationExecutor;

    @Value("${mongock.change-log-collection-name}")
    private String migrationCollectionName;

    private MongoDatabase mongoDatabase;

    @Autowired
    private void initDatabase(MongoDatabaseFactory mongoDatabaseFactory) {
        mongoDatabase = mongoDatabaseFactory.getMongoDatabase();
    }

    @Test
    public void works() {
        mongoDatabase.getCollection("user").deleteMany(new Document());
        mongoDatabase.getCollection("user").insertMany(
                Stream.of("alice", "bob", "chris")
                        .map(name -> new Document().append("email", name + "@mail.com"))
                        .collect(toList())
        );
        mongoDatabase.getCollection("summary").deleteMany(new Document());

        migrationExecutor.runAllMigrations(mongoDatabase, migrationCollectionName);

        var found = mongoDatabase.getCollection("summary").find();
        assertThat(found).hasSize(1);
        //noinspection ConstantConditions
        assertThat(found.first().getInteger("userCount")).isEqualTo(3);
    }
}
