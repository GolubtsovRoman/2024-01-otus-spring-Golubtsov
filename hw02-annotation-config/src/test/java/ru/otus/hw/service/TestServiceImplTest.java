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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@DisplayName("Process test student")
@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    @Mock
    private IOService ioService;

    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private TestServiceImpl testServiceImpl;


    @Test
    @DisplayName("should be execute test for a student")
    void executeTestFor() {
        doNothing().when(ioService).printLine(anyString());
        doNothing().when(ioService).printFormattedLine(anyString());
        when(ioService.readString()).thenReturn("Answer One");

        var questions = QuestionHelper.makeExampleQuestions();
        var countOfQuestions = questions.size();
        when(questionDao.findAll()).thenReturn(questions);

        Student student = new Student("Golubtsov", "Roman");
        testServiceImpl.executeTestFor(student);

        verify(ioService, times(countOfQuestions + 1)).printLine(anyString());
        verify(ioService, times(1)).printFormattedLine(anyString());
        verify(ioService, times(countOfQuestions)).readString();
        verify(questionDao).findAll();
    }

}
