package ru.otus.hw.service.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.hw.model.Caterpillar;
import ru.otus.hw.model.Cocoon;

import java.util.List;

@MessagingGateway
public interface CaterpillarGateway {

    @Gateway(requestChannel  = "caterpillarChannel", replyChannel = "cocoonChannel")
    List<Cocoon> process(List<Caterpillar> caterpillars);

}
