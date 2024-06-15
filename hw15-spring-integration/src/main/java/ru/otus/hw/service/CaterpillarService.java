package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.model.Caterpillar;
import ru.otus.hw.service.gateway.CaterpillarGateway;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.random.RandomGenerator;

@Service
@RequiredArgsConstructor
public class CaterpillarService {

    private final CaterpillarGateway caterpillarGateway;

    private final RandomGenerator randomGenerator = RandomGenerator.getDefault();


    public void startGenerateCaterpillarLoop() {
        ForkJoinPool.commonPool().execute(() -> {
            List<Caterpillar> caterpillars = generateCaterpillars();
            caterpillarGateway.process(caterpillars);
        });
    }

    private List<Caterpillar> generateCaterpillars() {
        int countOfCaterpillars = randomGenerator.nextInt(50, 100);
        List<Caterpillar> caterpillars = new ArrayList<>(countOfCaterpillars);
        for (int i = 0; i < countOfCaterpillars; i++) {
            caterpillars.add(generateCaterpillar());
        }
        return caterpillars;
    }

    private Caterpillar generateCaterpillar() {
        // не явяляется биологически точным!
        int weightGm = randomGenerator.nextInt(1, 10);
        int lengthMm = randomGenerator.nextInt(5, 30);
        boolean isDead = randomGenerator.nextBoolean();
        return new Caterpillar(weightGm, lengthMm, isDead);
    }

}
