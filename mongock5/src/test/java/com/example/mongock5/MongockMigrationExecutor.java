package com.example.mongock5;

import com.example.mongock.MigrationExecutor;
import com.mongodb.client.MongoDatabase;
import io.mongock.driver.mongodb.springdata.v3.SpringDataMongoV3Driver;
import io.mongock.runner.standalone.MongockStandalone;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import static com.mongodb.WriteConcern.MAJORITY;

@Component
public class MongockMigrationExecutor implements MigrationExecutor {
    private final MongoTemplate mongoTemplate;
    private final String changeLogsPackage;

    public MongockMigrationExecutor(
            MongoTemplate mongoTemplate,
            @Value("${mongock.change-logs-scan-package}") String changeLogsPackage
    ) {
        this.mongoTemplate = mongoTemplate;
        this.changeLogsPackage = changeLogsPackage;
    }

    @Override
    public void runAllMigrations(MongoDatabase mongoDatabase, String migrationCollectionName) {
        var res = mongoDatabase.getCollection(migrationCollectionName).withWriteConcern(MAJORITY)
                .deleteMany(new Document());
        System.out.println("Cleaned progress of migrations: " + res);

        // https://docs.mongock.io/v5/runner/standalone/index.html#example
        var driver = SpringDataMongoV3Driver.withDefaultLock(mongoTemplate);
        driver.setMigrationRepositoryName(migrationCollectionName);
        var runner = MongockStandalone.builder()
                .setDriver(driver)
                .addMigrationScanPackage(changeLogsPackage)
                .buildRunner();

        runner.execute();
    }
}
