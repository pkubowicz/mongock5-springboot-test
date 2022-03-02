package com.example.migrations;

import com.example.mongock.MigrationExecutor;
import com.mongodb.client.MongoDatabase;
import io.mongock.runner.springboot.base.MongockApplicationRunner;
import org.bson.Document;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.stereotype.Component;

import static com.mongodb.WriteConcern.MAJORITY;

@Component
public class Mongock5MigrationExecutor implements MigrationExecutor {
    private final MongockApplicationRunner migrationRunner;

    public Mongock5MigrationExecutor(MongockApplicationRunner migrationRunner) {
        this.migrationRunner = migrationRunner;
    }

    @Override
    public void runAllMigrations(MongoDatabase mongoDatabase, String migrationCollectionName) {
        var res = mongoDatabase.getCollection(migrationCollectionName).withWriteConcern(MAJORITY)
                .deleteMany(new Document());
        System.out.println("Cleaned progress of migrations: " + res);
        try {
            migrationRunner.run(new DefaultApplicationArguments());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
