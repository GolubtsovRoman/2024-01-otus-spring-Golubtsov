package ru.otus.hw.shellcommand;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.service.TestRunnerService;

@ShellComponent(value = "Application command")
@RequiredArgsConstructor
public class ApplicationCommand {

    private final TestRunnerService testRunnerService;

    @ShellMethod(value = "run student test", key = {"run", "r"})
    void run() {
        testRunnerService.run();
    }

}
