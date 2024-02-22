package ru.otus.hw.help;

import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionHelper {

    public static final String EN_LOCALE_VALUE = "en-US";


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

    public static Map<String, String> getQuestionFileName() {
        var fileNameByLocaleTag = new HashMap<String, String>(1);
        fileNameByLocaleTag.put(EN_LOCALE_VALUE, "questions.csv");
        return fileNameByLocaleTag;
    }

    public static Map<String, String> getNotExistQuestionFileName() {
        var fileNameByLocaleTag = new HashMap<String, String>(1);
        fileNameByLocaleTag.put(EN_LOCALE_VALUE, "not_exist.csv");
        return fileNameByLocaleTag;
    }

}
