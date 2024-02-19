package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.help.QuestionHelper;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.otus.hw.help.QuestionHelper.EN_LOCALE_VALUE;

@DisplayName("Process cvs-file and convert to domain")
class CsvQuestionDaoTest {

    @Test
    @DisplayName("should be correct read CSV")
    void findAllGood() {
        AppProperties properties = new AppProperties();
        properties.setLocale(EN_LOCALE_VALUE);
        properties.setFileNameByLocaleTag(QuestionHelper.getQuestionFileName());
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(properties);

        List<Question> expectedQuestions = QuestionHelper.makeExampleQuestions();
        List<Question> resultQuestions = csvQuestionDao.findAll();

        assertThat(expectedQuestions).hasSameElementsAs(resultQuestions);
    }

    @Test
    @DisplayName("should be throw exception")
    void findAllException() {
        Map<String, String> notExistFileNameByLocalTag = QuestionHelper.getNotExistQuestionFileName();
        AppProperties properties = new AppProperties();
        properties.setLocale(EN_LOCALE_VALUE);
        properties.setFileNameByLocaleTag(notExistFileNameByLocalTag);
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(properties);

        Exception exception = assertThrows(QuestionReadException.class, csvQuestionDao::findAll);
        String notExistFileName = notExistFileNameByLocalTag.get(EN_LOCALE_VALUE);
        assertThat(exception.getMessage()).isEqualTo("Can't read the file: " + notExistFileName);
    }

}
