package ru.otus.hw.help;

import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

public class QuestionHelper {

    public static List<Question> makeExampleQuestions() {
        return List.of(
                new Question(
                        "Test question One?",
                        List.of(new Answer("Answer One", true),
                                new Answer("Answer Two", false))
                ),
                new Question(
                        "Test question Two?",
                        List.of(new Answer("Answer One", false),
                                new Answer("Answer Two", true))
                )
        );
    }

    public static String getQuestionFileName() {
        return "questions.csv";
    }

    public static String getNotExistQuestionFileName() {
        return "not_exist.csv";
    }

}
