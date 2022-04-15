package com.example.mongock5;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 * A twin of {@link com.example.Main} with @EnableMongock removed so that Mongock does not run twice.
 */
@SpringBootConfiguration
@EnableAutoConfiguration
public class NoMongockSpringConfiguration {
}
