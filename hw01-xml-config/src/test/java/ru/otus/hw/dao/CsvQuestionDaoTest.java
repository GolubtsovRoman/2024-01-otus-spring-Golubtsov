package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvQuestionDaoTest {

    @Test
    @DisplayName("Process cvs-file and convert to domain - good case")
    void findAllGood() {
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(new AppProperties("questions.csv"));

        List<Question> expectedQuestions = List.of(
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
        List<Question> resultQuestions = csvQuestionDao.findAll();

        assertThat(expectedQuestions).hasSameElementsAs(resultQuestions);
    }

    @Test
    @DisplayName("Process cvs-file and convert to domain - exception case")
    void findAllException() {
        String notExistFileName = "not_exist.csv";
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(new AppProperties(notExistFileName));

        Exception exception = assertThrows(QuestionReadException.class, csvQuestionDao::findAll);
        assertThat(exception.getMessage()).isEqualTo("Can't read the file: " + notExistFileName);
    }

}
