package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");

        int widthStr = 100;
        String format = "%-" + widthStr + "s%S";
        List<Question> questionList = questionDao.findAll();
        for (Question question : questionList) {
            ioService.printFormattedLine(question.text());
            question.answers()
                    .forEach(answer -> ioService.printFormattedLine(format, answer.text(), answer.isCorrect()));
            ioService.printLine("-".repeat(widthStr + 5));
        }

        ioService.printFormattedLine("Thank you for pass this test!%n");
    }
}
