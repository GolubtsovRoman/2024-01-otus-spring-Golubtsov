package ru.otus.hw;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Check live test")
class ApplicationTest {

    @Test
    @DisplayName("should write only log to console")
    void init() {
        System.out.println("It live!");
    }

}
