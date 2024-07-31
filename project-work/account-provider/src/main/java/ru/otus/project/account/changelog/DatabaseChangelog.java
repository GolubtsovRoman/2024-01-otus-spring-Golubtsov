package ru.otus.project.account.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.project.account.model.Account;
import ru.otus.project.account.repository.AccountRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@ChangeLog
public class DatabaseChangelog {

    private static final String ROMAN_GOLUBTSOV = "Roman Golubtsov"; //changelog author


    @ChangeSet(order = "001", id = "dropDb", author = ROMAN_GOLUBTSOV, runAlways = true)
    public void dropDb(MongoDatabase mdb) {
        mdb.drop();
    }

    @ChangeSet(order = "002", id = "insertAccounts", author = ROMAN_GOLUBTSOV, runAlways = true)
    public void insertAuthors(AccountRepository accountRepository) {
        List<Account> accounts = new ArrayList<>(10);

        accounts.add(new Account("66a52bbad153b0189d3d4af0", "a.ozerov", "SuperPass_123456789!",
                LocalDate.parse("2030-01-30"), "a.ozerov@rl.com", true));
        accounts.add(new Account("66a52bbad153b0189d3d4af1", "s.tarasova", "SuperPass_123456789!",
                LocalDate.parse("2030-01-30"), "s.tarasova@rl.com", true));
        accounts.add(new Account("66a52bbad153b0189d3d4af2", "i.kravtsov", "SuperPass_123456789!",
                LocalDate.parse("2030-01-30"), "i.kravtsov@rl.com", true));
        accounts.add(new Account("66a52bbad153b0189d3d4af3", "s.nikitin", "SuperPass_123456789!",
                LocalDate.parse("2030-01-30"), "s.nikitin@rl.com", true));
        accounts.add(new Account("66a52bbad153b0189d3d4af4", "a.vavilov", "SuperPass_123456789!",
                LocalDate.parse("2030-01-30"), "a.vavilov@rl.com", true));
        accounts.add(new Account("66a52bbad153b0189d3d4af5", "l.bogomolov", "SuperPass_123456789!",
                LocalDate.parse("2030-01-30"), "l.bogomolov@rl.com", true));
        accounts.add(new Account("66a52bbad153b0189d3d4af6", "k.kolpakova", "SuperPass_123456789!",
                LocalDate.parse("2030-01-30"), "k.kolpakova@rl.com", true));
        accounts.add(new Account("66a52bbad153b0189d3d4af9", "k.muraveva", "SuperPass_123456789!",
                LocalDate.parse("2030-01-30"), "k.muraveva@rl.com", true));

        accountRepository.insert(accounts);
    }

}
