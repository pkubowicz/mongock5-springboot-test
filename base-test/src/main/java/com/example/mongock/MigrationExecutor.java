package com.example.mongock;

import com.mongodb.client.MongoDatabase;

public interface MigrationExecutor {
    void runAllMigrations(MongoDatabase mongoDatabase, String migrationCollectionName);
}
