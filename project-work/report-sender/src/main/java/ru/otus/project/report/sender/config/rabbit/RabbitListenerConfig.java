package ru.otus.project.report.sender.config.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import ru.otus.project.report.sender.api.BirthdayInfo;
import ru.otus.project.report.sender.api.DisabledAccounts;
import ru.otus.project.report.sender.api.ExpireAccounts;
import ru.otus.project.report.sender.service.MessageProcessService;

@Configuration
@RequiredArgsConstructor
public class RabbitListenerConfig {

    private final MessageProcessService messageProcessService;


    @RabbitListener(queues = {"${service.mq.queues.birthdays}"})
    public void listenBirthdays(BirthdayInfo birthdayInfo) {
        messageProcessService.processOnMessage(birthdayInfo);
    }

    @RabbitListener(queues = {"${service.mq.queues.expiring-accounts}"})
    public void listenExpiringAccounts(ExpireAccounts expireAccounts) {
        messageProcessService.processOnMessage(expireAccounts);
    }

    @RabbitListener(queues = {"${service.mq.queues.disabled-accounts}"})
    public void listenDisabledAccounts(DisabledAccounts disabledAccounts) {
        messageProcessService.processOnMessage(disabledAccounts);
    }

}
