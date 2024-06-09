package ru.otus.hw.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.Message;
import ru.otus.hw.model.Butterfly;
import ru.otus.hw.model.Caterpillar;
import ru.otus.hw.model.Cocoon;

import java.util.random.RandomGenerator;

@Configuration
public class IntegrationConfig {

    private final RandomGenerator randomGenerator = RandomGenerator.getDefault();


    @Bean
    public MessageChannelSpec<?, ?> caterpillarChannel() {
        return MessageChannels.queue(10);
    }

    @Bean
    public MessageChannelSpec<?, ?> cocoonChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2);
    }

    @Bean
    public IntegrationFlow faunaFlow() {
        return IntegrationFlow.from(caterpillarChannel())
                .split()
                .<Caterpillar, Cocoon>transform(caterpillar -> new Cocoon(
                        caterpillar.lengthMm() + 3,
                        randomGenerator.nextInt(3, 7))
                )
                .<Cocoon, Butterfly>transform(cocoon -> new Butterfly(
                        randomGenerator.nextInt(200, 500),
                        randomGenerator.nextBoolean() // часть бабочек родятся мертвыми ):
                ))
                .filter(Butterfly::notDead)
                .<Butterfly>log(LoggingHandler.Level.INFO, "Butterfly Roll Call", Message::getPayload)
                .aggregate()
                .channel(cocoonChannel())
                .get();
    }

}
