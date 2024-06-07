package ru.otus.hw.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;

@SuppressWarnings("unused")
@ChangeLog
public class DatabaseChangelog {

    private static final String ROMAN_GOLUBTSOV = "Roman Golubtsov"; //changelog author

    @ChangeSet(order = "001", id = "dropDb", author = ROMAN_GOLUBTSOV, runAlways = true)
    public void dropDb(MongoDatabase mdb) {
        mdb.drop();
    }

}
