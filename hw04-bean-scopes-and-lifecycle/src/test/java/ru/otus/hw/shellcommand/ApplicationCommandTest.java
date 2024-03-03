package ru.otus.hw.shellcommand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.shell.InputProvider;
import org.springframework.shell.ResultHandlerService;
import org.springframework.shell.Shell;
import ru.otus.hw.service.TestRunnerService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import static ru.otus.hw.shellcommand.ApplicationShellCommand.COMMAND_RUN_TEST;
import static ru.otus.hw.shellcommand.ApplicationShellCommand.COMMAND_RT;
import static ru.otus.hw.shellcommand.ApplicationShellCommand.COMMAND_RUN;
import static ru.otus.hw.shellcommand.ApplicationShellCommand.COMMAND_R;

@DisplayName("Application command should be")
@SpringBootTest
class ApplicationCommandTest {

    private static final String BYE_PHRASE_PATTERN = "Thank you for pass test!";

    @Autowired
    private Shell shell;

    private InputProvider inputProvider;

    private ArgumentCaptor<Object> argumentCaptor;

    @SpyBean
    private ResultHandlerService resultHandlerService;

    @MockBean
    private TestRunnerService testRunnerService;


    @BeforeEach
    void setUp() {
        inputProvider = mock(InputProvider.class);
        argumentCaptor = ArgumentCaptor.forClass(Object.class);
    }

    @DisplayName("run pass test")
    @Test
    void runPassTest() throws Exception {
        doNothing().when(testRunnerService).run();

        when(inputProvider.readInput())
                .thenReturn(() -> COMMAND_RUN_TEST)
                .thenReturn(() -> COMMAND_RT)
                .thenReturn(() -> COMMAND_RUN)
                .thenReturn(() -> COMMAND_R)
                .thenReturn(null);

        shell.run(inputProvider);
        verify(resultHandlerService, times(4)).handle(argumentCaptor.capture());
        List<Object> results = argumentCaptor.getAllValues();
        results.forEach(result -> assertThat((String) result).isEqualTo(BYE_PHRASE_PATTERN));
    }

}
