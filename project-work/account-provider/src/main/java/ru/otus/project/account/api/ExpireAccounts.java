package ru.otus.project.account.api;

import java.util.List;

public record ExpireAccounts(int expireAfterDays, List<String> logins) {
}
