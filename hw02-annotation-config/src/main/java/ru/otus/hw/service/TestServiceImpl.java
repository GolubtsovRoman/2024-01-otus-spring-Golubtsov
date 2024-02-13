package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            ioService.printLine(question.text());
            var studentAnswer = ioService.readString();
            var isAnswerValid = checkAnswer(question, studentAnswer);
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    private Boolean checkAnswer(Question question, String studentAnswer) {
        for (var answer : question.answers()) {
            if (studentAnswer.equalsIgnoreCase(answer.text())) {
                return answer.isCorrect();
            }
        }
        return false;
    }

}
