package ru.otus.project.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.project.account.dto.AccountDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PasswordScheduler {

    private final AccountService accountService;


    @Scheduled(cron = "0 8 * * *") // At 08:00 AM
    public void expirePassword() {
        String loginsWithExpiredPassword = accountService.loginsWithExpiredPassword(LocalDate.now())
                .stream()
                .collect(Collectors.joining(System.lineSeparator()));

        if (!loginsWithExpiredPassword.isEmpty()) {
            // todo отпарвка во внешнюю систему
            System.out.println("Сегодня истекают пароли у: " + System.lineSeparator() + loginsWithExpiredPassword);
        }

        String loginsWith3daysExpiredPassword = accountService.loginsWithExpiredPassword(LocalDate.now().plusDays(3))
                .stream()
                .collect(Collectors.joining(System.lineSeparator()));
        if (!loginsWith3daysExpiredPassword.isEmpty()) {
            // todo отпарвка во внешнюю систему
            System.out.println("Через 3 дня истекают пароли у: " + System.lineSeparator() + loginsWithExpiredPassword);
        }
    }

    @Scheduled(cron = "1 0 * * *") // At 12:01 AM
    public void disableAccounts() {
        List<String> expiredLoginAccounts = accountService.offExpired()
                .stream()
                .map(AccountDto::login)
                .toList();

        if (!expiredLoginAccounts.isEmpty()) {
            // todo отпарвка во внешнюю систему
            System.out.println("Отключено аккаутов: " + expiredLoginAccounts.size());
        }
    }

}
