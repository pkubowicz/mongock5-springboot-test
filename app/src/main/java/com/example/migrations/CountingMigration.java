package com.example.migrations;

import com.example.data.Summary;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import org.springframework.data.mongodb.core.query.Query;

@ChangeLog
public final class CountingMigration {

    @ChangeSet(author = "joe", id = "someMigration", order = "001")
    public void run(MongockTemplate template) {
        var count = template.count(new Query(), "user");
        template.insert(new Summary((int) count));
    }
}
