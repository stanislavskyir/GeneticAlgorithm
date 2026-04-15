import java.util.Arrays;

class GeneticAlgorithm {

    private SelectParentStrategy selectParentStrategy;
    private CrossingParentsStrategy crossingParentsStrategy;
    private MutationStrategy mutationStrategy;
    private FitnessStrategy fitnessStrategy;

    public GeneticAlgorithm(
            SelectParentStrategy selectParentStrategy,
            CrossingParentsStrategy crossingParentsStrategy,
            MutationStrategy mutationStrategy,
            FitnessStrategy fitnessStrategy
    ) {
        this.selectParentStrategy = selectParentStrategy;
        this.crossingParentsStrategy = crossingParentsStrategy;
        this.mutationStrategy = mutationStrategy;
        this.fitnessStrategy = fitnessStrategy;
    }

    private static int[][] startPopulation = {
            {0, 1, 2, 3, 4},
            {0, 1, 4, 2, 3},

            {0, 2, 1, 3, 4},
            {0, 2, 1, 4, 3},
            {0, 2, 3, 1, 4},
            {0, 2, 4, 1, 3},

            {0, 3, 1, 2, 4},
            {0, 3, 2, 4, 1},
            {0, 3, 4, 1, 2},

            {0, 4, 1, 2, 3},
            {0, 4, 2, 1, 3},
            {0, 4, 3, 1, 2},
            {0, 4, 3, 2, 1}
    };


    private int[][] sortPopulation(int[][] population) {

        PairChildWithWeight[] pairs = new PairChildWithWeight[population.length];
        for (int i = 0; i < population.length; i++) {

            int length = fitnessStrategy.fitnessMethod(population[i]);
            pairs[i] = new PairChildWithWeight(population[i], length);
            System.out.print(length + " ");
        }

        System.out.println();
        System.out.println("Sorting population...");

        PairChildWithWeight[] sorted =
                Arrays.stream(pairs)
                        .sorted()
                        .toArray(PairChildWithWeight[]::new);

        int[][] afterSortingPopulation = new int[population.length - 2][];

        for (int i = 0; i < afterSortingPopulation.length; i++) {
            afterSortingPopulation[i] = sorted[i].getGens();
        }

        return afterSortingPopulation;

    }

    public void run() {
        System.out.println("Start population..");

        for (int i = 0; i < startPopulation.length; i++) {
            System.out.print(Arrays.toString(startPopulation[i]) + ": ");
            int length = fitnessStrategy.fitnessMethod(startPopulation[i]);
            System.out.println(length);
        }

        int start = 0, end = 100;
        while (start < end) {
            System.out.println("Поколение: " + start);
            startPopulation = generations(startPopulation, selectParentStrategy);
            start++;
        }

        System.out.println("End population..");
        for (int i = 0; i < startPopulation.length; i++) {
            System.out.print(Arrays.toString(startPopulation[i]) + ": ");
            int length = fitnessStrategy.fitnessMethod(startPopulation[i]);
            System.out.println(length);
        }
    }

    public int[][] generations(int[][] currentPopulation, SelectParentStrategy selectParentStrategy) {

        int[][] randomParents = selectParentStrategy.selectParent(currentPopulation);

        int[] parent1 = randomParents[0].clone();
        int[] parent2 = randomParents[1].clone();

        int[][] resultCrossingParents = crossingParents(new int[][]{parent1, parent2});
        int[][] afterMutationChildren = mutationStrategy.startMutation(resultCrossingParents);

        int[][] afterPopulation = new int[currentPopulation.length + 2][];

        for (int i = 0; i < afterPopulation.length - 2; i++) {
            afterPopulation[i] = currentPopulation[i].clone();
        }

        afterPopulation[afterPopulation.length - 2] = afterMutationChildren[0].clone();
        afterPopulation[afterPopulation.length - 1] = afterMutationChildren[1].clone();

        int[][] afterSortPopulation = sortPopulation(afterPopulation);

        return afterSortPopulation;
    }


    public int[][] crossingParents(int[][] randomParents) {

        int[] firstParent = randomParents[0];
        int[] secondParent = randomParents[1];

        int[] firstChild = crossingParentsStrategy.returnCrossingParents(firstParent, secondParent);
        int[] secondChild = crossingParentsStrategy.returnCrossingParents(secondParent, firstParent);
        return new int[][]{
                firstChild,
                secondChild
        };
    }
}
