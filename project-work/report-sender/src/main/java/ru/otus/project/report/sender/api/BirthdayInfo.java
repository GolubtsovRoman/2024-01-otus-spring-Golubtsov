package ru.otus.project.report.sender.api;

import java.util.List;

public record BirthdayInfo(boolean isToday, List<String> birthdayBoys) {
}
