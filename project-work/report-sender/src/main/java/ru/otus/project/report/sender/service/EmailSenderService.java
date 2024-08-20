package ru.otus.project.report.sender.service;

public interface EmailSenderService {
    void sendEmail(String subject, String text);
}
