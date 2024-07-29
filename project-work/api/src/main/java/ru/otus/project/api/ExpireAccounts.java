package ru.otus.project.api;

import java.util.List;

public record ExpireAccounts(int expireAfterDays, List<String> logins) {
}
