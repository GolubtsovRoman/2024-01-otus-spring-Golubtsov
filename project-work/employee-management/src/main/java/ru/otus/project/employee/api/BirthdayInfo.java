package ru.otus.project.employee.api;

import java.util.List;

public record BirthdayInfo(boolean isToday, List<String> birthdayBoys) {
}
