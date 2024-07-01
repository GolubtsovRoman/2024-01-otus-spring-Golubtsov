package ru.otus.hw.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.random.RandomGenerator;

@Component
public class SuperHelpfulHealthIndicator implements HealthIndicator {

    private final RandomGenerator randomGenerator = RandomGenerator.getDefault();


    @Override
    public Health health() {
        boolean isGoodMood = randomGenerator.nextBoolean();

        if (isGoodMood) {
            return Health.up()
                    .withDetail("message", "Сервер работает")
                    .build();
        } else {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Иди своей дорогой, сталкер")
                    .build();
        }
    }

}
