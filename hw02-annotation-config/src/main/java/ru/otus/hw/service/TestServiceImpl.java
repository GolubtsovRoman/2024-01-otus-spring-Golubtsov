package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
            printBorder();
            ioService.printLine(question.text());

            List<Answer> answers = question.answers();
            printAnswers(answers);

            int studentAnswer = getStudentAnswer(question.answers().size());
            var isStudentAnswerCorrect = answers.get(studentAnswer - 1).isCorrect();
            testResult.applyAnswer(question, isStudentAnswerCorrect);
        }
        printBorder();
        return testResult;
    }

    private void printBorder() {
        ioService.printLine("-".repeat(80));
    }

    private void printAnswers(List<Answer> answers) {
        AtomicInteger questionNumber = new AtomicInteger(1);
        answers.forEach(answer ->
                ioService.printFormattedLine("%s %s", questionNumber.getAndIncrement(), answer.text()));
    }

    private int getStudentAnswer(int questionsSize) {
        int min = 1;
        String prompt = "Please, write number of answer (from " + min + " to " + questionsSize + "): ";
        String errorMessage = "Invalid input! Choose correct number.";
        return ioService.readIntForRangeWithPrompt(min, questionsSize, prompt, errorMessage);
    }

}
