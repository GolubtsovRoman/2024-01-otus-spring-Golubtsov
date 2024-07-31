package ru.otus.project.report.sender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.project.report.sender.api.BirthdayInfo;
import ru.otus.project.report.sender.api.DisabledAccounts;
import ru.otus.project.report.sender.api.ExpireAccounts;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageProcessService {

    private final EmailSenderService emailSenderService;

    private static final String SIGN = System.lineSeparator() + """
            --
            Автоматическая система уведомлений. Не отвечайте на это письмо""";


    public void processOnMessage(BirthdayInfo birthdayInfo) {
        String subject;
        if (birthdayInfo.isToday()) {
            subject = "Дни рождения сегодня!";
        } else {
            subject = "Ближайшие дни рождения";
        }

        String text = birthdayInfo.birthdayBoys().stream().collect(Collectors.joining(System.lineSeparator()));
        emailSenderService.sendEmail(subject, text + SIGN);
    }

    public void processOnMessage(ExpireAccounts expireAccounts) {
        String subject;
        if (expireAccounts.expireAfterDays() > 1) {
            subject = "Пароли истекаут через " + expireAccounts.expireAfterDays() + " дня (дней)";
        } else {
            subject = "Пароли истекают СЕГОДНЯ!";
        }

        String text = expireAccounts.logins().stream().collect(Collectors.joining(System.lineSeparator()));
        emailSenderService.sendEmail(subject, text + SIGN);
    }

    public void processOnMessage(DisabledAccounts disabledAccounts) {
        String subject = "Аккаунты были отключены";
        String text = disabledAccounts.logins().stream().collect(Collectors.joining(System.lineSeparator()));
        emailSenderService.sendEmail(subject, text + SIGN);
    }

}
