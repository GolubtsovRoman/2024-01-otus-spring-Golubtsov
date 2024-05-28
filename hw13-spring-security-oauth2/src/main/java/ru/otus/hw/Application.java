package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
		SpringApplication.run(Application.class);
		System.out.printf("Go to main page: %s%n", "http://localhost:8080");
    }

}
