package ru.otus.project.employee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.project.employee.dto.PersonalInfoDto;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BirthdayScheduler {

    private final PersonalInfoService personalInfoService;


    @Scheduled(cron = "0 8 * * * ") // At 08:00 AM
    public void birthdayToday() {
        String birthdayBoys = personalInfoService.birthdayToday()
                .stream()
                .map(PersonalInfoDto::fullName)
                .collect(Collectors.joining(System.lineSeparator()));

        if (!birthdayBoys.isEmpty()) {
            // todo отпарвка во внешнюю систему
            System.out.println("Сегодня отмечают ДР: " + System.lineSeparator() + birthdayBoys);
        }
    }

    @Scheduled(cron = "0 8 28 * *") // At 08:00 AM, on day 28 of the month
    public void birthdayNextMonth() {
        String birthdayBoys = personalInfoService.birthdayNextMonth()
                .stream()
                .map(PersonalInfoDto::fullName)
                .collect(Collectors.joining(System.lineSeparator()));

        if (!birthdayBoys.isEmpty()) {
            // todo отпарвка во внешнюю систему
            System.out.println("В следующем месяце ДР отмечают: " + System.lineSeparator() + birthdayBoys);
        }
    }

}
