package com.example.mongock;

import com.example.testcontainers.MongoInitializer;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(InitializerContext.class)
@ContextConfiguration(initializers = MongoInitializer.class)
public abstract class BaseCountingMigrationTest {

    @Value("${mongock.change-log-repository-name}")
    private String migrationCollectionName;

    private MongoDatabase mongoDatabase;

    @Autowired
    private void initDatabase(MongoDatabaseFactory mongoDatabaseFactory) {
        mongoDatabase = mongoDatabaseFactory.getMongoDatabase();
    }

    @Test
    public void works() {
        var found = mongoDatabase.getCollection("summary").find();
        assertThat(found).hasSize(1);
        //noinspection ConstantConditions
        assertThat(found.first().getInteger("userCount")).isEqualTo(3);
    }
}
