package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Student;
import ru.otus.hw.help.QuestionHelper;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DisplayName("Process test student")
@SpringBootTest
class TestServiceImplTest {

    @Autowired
    private TestServiceImpl testServiceImpl;

    @MockBean
    private LocalizedIOService ioService;

    @MockBean
    private QuestionDao questionDao;


    @Test
    @DisplayName("should be execute test for a student")
    void executeTestFor() {
        doNothing().when(ioService).printLine(anyString());
        doNothing().when(ioService).printFormattedLine(anyString(), anyInt(), anyString());
        doNothing().when(ioService).printFormattedLineLocalized(anyString());

        given(ioService.getMessage(anyString(), anyInt(), anyInt())).willReturn("Some string");
        given(ioService.getMessage(anyString())).willReturn("Some string");
        given(ioService.readIntForRangeWithPrompt(anyInt(), anyInt(), anyString(), anyString())).willReturn(1);

        var questions = QuestionHelper.makeExampleQuestions();
        var countOfQuestions = questions.size();
        given(questionDao.findAll()).willReturn(questions);

        Student student = new Student("Golubtsov", "Roman");
        testServiceImpl.executeTestFor(student);

        int countOfPrintLine = 1 + countOfQuestions * 2 + 1; // empty line + questions * (border + question) +  border
        verify(ioService, times(countOfPrintLine)).printLine(anyString());
        verify(ioService).printFormattedLineLocalized(anyString());
        verify(ioService, times(countOfQuestions)).readIntForRangeWithPrompt(anyInt(), anyInt(), anyString(), anyString());
        verify(questionDao).findAll();
    }

}
