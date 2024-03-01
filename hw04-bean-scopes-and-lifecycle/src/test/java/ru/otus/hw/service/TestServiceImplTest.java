package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Student;
import ru.otus.hw.help.QuestionHelper;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@DisplayName("Process test student")
@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    @Mock
    private LocalizedIOService ioService;

    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private TestServiceImpl testServiceImpl;


    @Test
    @DisplayName("should be execute test for a student")
    void executeTestFor() {
        doNothing().when(ioService).printLine(anyString());
        doNothing().when(ioService).printFormattedLine(anyString(), anyInt(), anyString());
        doNothing().when(ioService).printFormattedLineLocalized(anyString());
        when(ioService.getMessage(anyString(), anyInt(), anyInt())).thenReturn("Some string");
        when(ioService.getMessage(anyString())).thenReturn("Some string");
        when(ioService.readIntForRangeWithPrompt(anyInt(), anyInt(), anyString(), anyString())).thenReturn(1);

        var questions = QuestionHelper.makeExampleQuestions();
        var countOfQuestions = questions.size();
        when(questionDao.findAll()).thenReturn(questions);

        Student student = new Student("Golubtsov", "Roman");
        testServiceImpl.executeTestFor(student);

        int countOfPrintLine = 1 + countOfQuestions * 2 + 1; // empty line + questions * (border + question) +  border
        verify(ioService, times(countOfPrintLine)).printLine(anyString());
        verify(ioService).printFormattedLineLocalized(anyString());
        verify(ioService, times(countOfQuestions)).readIntForRangeWithPrompt(anyInt(), anyInt(), anyString(), anyString());
        verify(questionDao).findAll();
    }

}
