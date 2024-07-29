package ru.otus.project.report.sender.api;

import java.util.List;

public record ExpireAccounts(int expireAfterDays, List<String> logins) {
}
