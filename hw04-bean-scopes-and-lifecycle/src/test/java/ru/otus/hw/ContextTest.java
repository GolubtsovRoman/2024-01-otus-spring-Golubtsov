package ru.otus.hw;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("Context should be")
@SpringBootTest
public class ContextTest {

    @DisplayName("up")
    @Test
    void upContext() {
        System.out.println("the context was up");
    }

}
