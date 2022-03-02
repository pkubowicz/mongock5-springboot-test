package com.example.testcontainers;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

public final class MongoInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext context) {
        var addedProperties = List.of(
                "spring.data.mongodb.uri=" + MongoInstanceSingleton.getInstance().getReplicaSetUrl()
        );
        TestPropertyValues.of(addedProperties).applyTo(context.getEnvironment());
    }
}

class MongoInstanceSingleton {
    private static MongoDBContainer instance;

    static MongoDBContainer getInstance() {
        if (instance == null) {
            instance = new MongoDBContainer(DockerImageName.parse("mongo:5.0.6"))
                    .withReuse(true);
            instance.start();
        }
        return instance;
    }
}
