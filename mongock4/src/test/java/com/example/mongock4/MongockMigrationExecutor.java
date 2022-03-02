package com.example.mongock4;

import com.example.mongock.MigrationExecutor;
import com.github.cloudyrock.spring.v5.MongockSpring5.MongockApplicationRunner;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.stereotype.Component;

import static com.mongodb.WriteConcern.MAJORITY;

@Component
public class MongockMigrationExecutor implements MigrationExecutor {
    private final MongockApplicationRunner migrationRunner;

    public MongockMigrationExecutor(MongockApplicationRunner migrationRunner) {
        this.migrationRunner = migrationRunner;
    }

    @Override
    public void runAllMigrations(MongoDatabase mongoDatabase, String migrationCollectionName) {
        mongoDatabase.getCollection(migrationCollectionName).withWriteConcern(MAJORITY).deleteMany(new Document());
        migrationRunner.run(new DefaultApplicationArguments());
    }
}
