package ru.otus.hw.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.hw.service.CaterpillarService;

@Component
@RequiredArgsConstructor
public class IntegrationFlowRunner implements CommandLineRunner {

    private final CaterpillarService caterpillarService;


    @Override
    public void run(String... args) {
        caterpillarService.startGenerateCaterpillarLoop();
    }

}
