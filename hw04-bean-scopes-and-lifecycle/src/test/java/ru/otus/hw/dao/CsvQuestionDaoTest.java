package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.help.QuestionHelper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static ru.otus.hw.help.QuestionHelper.NO_EXIST_QUESTION_FILE_NAME;
import static ru.otus.hw.help.QuestionHelper.QUESTION_FILE_NAME;

@DisplayName("Process cvs-file and convert to domain")
@SpringBootTest
class CsvQuestionDaoTest {

    @Autowired
    private QuestionDao questionDao;

    @MockBean
    private AppProperties appProperties;


    @Test
    @DisplayName("should be correct read CSV")
    void findAllGood() {
        given(appProperties.getTestFileName()).willReturn(QUESTION_FILE_NAME);

        List<Question> expectedQuestions = QuestionHelper.makeExampleQuestions();
        List<Question> resultQuestions = questionDao.findAll();

        assertThat(expectedQuestions).hasSameElementsAs(resultQuestions);
    }

    @Test
    @DisplayName("should be throw exception")
    void findAllException() {
        given(appProperties.getTestFileName()).willReturn(NO_EXIST_QUESTION_FILE_NAME);

        Exception exception = assertThrows(QuestionReadException.class, questionDao::findAll);
        assertThat(exception.getMessage()).isEqualTo("Can't read the file: " + NO_EXIST_QUESTION_FILE_NAME);
    }

}
