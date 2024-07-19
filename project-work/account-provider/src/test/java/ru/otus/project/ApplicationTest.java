package ru.otus.project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("Контекст должен")
@SpringBootTest
class ApplicationTest {

    @DisplayName("успешно подниматься")
    @Test
    public void applicationTest() {
        System.out.println("Application was UP");
    }

}