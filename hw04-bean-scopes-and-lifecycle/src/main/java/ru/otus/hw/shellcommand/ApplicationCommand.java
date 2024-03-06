package ru.otus.hw.shellcommand;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.service.TestRunnerService;

import static ru.otus.hw.shellcommand.ApplicationShellCommand.COMMAND_RUN_TEST;
import static ru.otus.hw.shellcommand.ApplicationShellCommand.COMMAND_RT;
import static ru.otus.hw.shellcommand.ApplicationShellCommand.COMMAND_RUN;
import static ru.otus.hw.shellcommand.ApplicationShellCommand.COMMAND_R;


@ShellComponent(value = "Application command")
@RequiredArgsConstructor
public class ApplicationCommand {

    private final TestRunnerService testRunnerService;

    @ShellMethod(value = "run pass test", key = {COMMAND_RUN_TEST, COMMAND_RT, COMMAND_RUN, COMMAND_R})
    String runPassTest() {
        testRunnerService.run();
        return "Thank you for pass test!";
    }

}
