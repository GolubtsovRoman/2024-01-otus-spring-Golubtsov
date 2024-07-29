package ru.otus.project.api;

import java.util.List;

public record BirthdayInfo(boolean isToday, List<String> birthdayBoys) {
}
