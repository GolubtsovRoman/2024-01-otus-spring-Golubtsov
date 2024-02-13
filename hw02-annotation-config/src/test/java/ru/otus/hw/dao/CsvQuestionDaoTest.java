package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.help.QuestionHelper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Process cvs-file and convert to domain")
class CsvQuestionDaoTest {

    @Test
    @DisplayName("should be correct read CSV")
    void findAllGood() {
        AppProperties properties = new AppProperties();
        properties.setTestFileName(QuestionHelper.getQuestionFileName());
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(properties);

        List<Question> expectedQuestions = QuestionHelper.makeExampleQuestions();
        List<Question> resultQuestions = csvQuestionDao.findAll();

        assertThat(expectedQuestions).hasSameElementsAs(resultQuestions);
    }

    @Test
    @DisplayName("should be throw exception")
    void findAllException() {
        String notExistFileName = QuestionHelper.getNotExistQuestionFileName();
        AppProperties properties = new AppProperties();
        properties.setTestFileName(notExistFileName);
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(properties);

        Exception exception = assertThrows(QuestionReadException.class, csvQuestionDao::findAll);
        assertThat(exception.getMessage()).isEqualTo("Can't read the file: " + notExistFileName);
    }

}
