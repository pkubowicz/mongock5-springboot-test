package com.example.migrations;

import com.mongodb.client.MongoDatabase;

public interface MigrationExecutor {
    void runAllMigrations(MongoDatabase mongoDatabase, String migrationCollectionName);
}
