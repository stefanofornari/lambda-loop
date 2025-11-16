package ste.lloop.comparison;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import ste.lloop.Loop;

public class StreamComparison {

    private static final int LIST_SIZE = 50;
    private static final int SEARCH_ITERATIONS = 1000000;

    public static void main(String[] args) throws Exception {
        List<String> names = generateNames();
        Random random = new Random();

        long lloopTime = 0;
        long streamTime = 0;

        System.out.println("starting");
        System.out.println("Total time for " + SEARCH_ITERATIONS + " iterations:");

        final String[] randomNames = new String[SEARCH_ITERATIONS];

        Loop.on().from(0).to(SEARCH_ITERATIONS-1).loop((i) -> {
            randomNames[i] = names.get(random.nextInt(LIST_SIZE));
        });
        for (int i = 0; i < SEARCH_ITERATIONS; i++) {
            long startStream = System.nanoTime();
            final String NAME = randomNames[i];
            Optional<String> found = names.stream()
                                          .filter(name -> name.equals(NAME))
                                          .findFirst();
            streamTime += (System.nanoTime() - startStream);
        }
        System.out.println("Stream: " + streamTime / 1000000 + " ms");

        for (int i = 0; i < SEARCH_ITERATIONS; i++) {
            // lloop
            final String NAME = randomNames[i];
            long startLloop = System.nanoTime();
            Loop.on(names).loop(name -> {
                if (name.equals(NAME)) {
                    Loop._break_(NAME);
                }
            });
            lloopTime += (System.nanoTime() - startLloop);
        }

        System.out.println("λLoop: " + lloopTime / 1000000 + " ms");
    }

    private static List<String> generateNames() {
        return List.of(
            "Maria", "Nushi", "Mohammed", "Jose", "Muhammad", "Mohamed", "Wei", "Mohammad", "Ahmed", "Yan",
            "Ali", "John", "David", "Li", "Abdul", "Ana", "Ying", "Michael", "Juan", "Anna",
            "Mary", "Jean", "Robert", "Daniel", "Luis", "Carlos", "James", "Antonio", "Joseph", "Hui",
            "Elena", "Francisco", "Hong", "Marie", "Min", "Lei", "Yu", "Ibrahim", "Peter", "Fatima",
            "Aleksandr", "Richard", "Xin", "Bin", "Paul", "Ping", "Lin", "Olga", "Sri", "Pedro"
        );
    }
}
