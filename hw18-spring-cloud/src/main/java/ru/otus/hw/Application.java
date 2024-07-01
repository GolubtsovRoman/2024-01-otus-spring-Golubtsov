package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
        printUsage();
    }


    private static void printUsage() {
        System.out.printf(
                """
                        Main page: %s
                        -------------
                        Actuator: %s
                        -------------
                        Healthcheck: %s
                        Metrics: %s
                        """,

                "http://localhost:8080",
                "http://localhost:8080/actuator",
                "http://localhost:8080/actuator/health",
                "http://localhost:8080/actuator/metrics"
        );
    }

}
