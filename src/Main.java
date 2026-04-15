import java.util.*;

interface SelectParentStrategy{
    int[][] selectParent(int[][] startPopulation);
}

class SelectRandomParentStrategy implements SelectParentStrategy{

    @Override
    public int[][] selectParent(int[][] startPopulation) {
        Random random = new Random();

        int firstRandomParent = random.nextInt(startPopulation.length);
        int secondRandomParent = random.nextInt(startPopulation.length);

        while (firstRandomParent == secondRandomParent) {
            secondRandomParent = random.nextInt(startPopulation.length);
        }

        int[][] randomParens = {
                startPopulation[firstRandomParent],
                startPopulation[secondRandomParent]
        };

        return randomParens;
    }
}

interface CrossingParentsStrategy {
    int[] returnCrossingParents(int[] firstParent, int[] secondParent);
}

class SimpleCrossover implements CrossingParentsStrategy{

    @Override
    public int[] returnCrossingParents(int[] firstParent, int[] secondParent) {
        int lengthBreakPoint = 2;

        int[] firstChild = new int[firstParent.length];

        Set<Integer> fromParentToChildDoNotCopy = new HashSet<>();

        int i = 0;
        int firstParentLength = firstParent.length;

        while (i < firstParentLength) {

            if (i < lengthBreakPoint) {
                firstChild[i] = firstParent[i];
                fromParentToChildDoNotCopy.add(firstParent[i]);
            }

            if (i >= lengthBreakPoint) {
                if (fromParentToChildDoNotCopy.contains(secondParent[i])) {
                    int j = i + 1;
                    boolean validNext = true;
                    while (j < firstParentLength && validNext) {

                        if (!fromParentToChildDoNotCopy.contains(secondParent[j])) {
                            firstChild[i] = secondParent[j];
                            validNext = false;
                            fromParentToChildDoNotCopy.add(secondParent[j]);
                        }

                        j++;
                    }
                } else {
                    firstChild[i] = secondParent[i];
                    fromParentToChildDoNotCopy.add(secondParent[i]);
                }
            }

            i++;
        }

        int firstChildLength = firstChild.length - 1;
        int indexBeforeBreakPoint = lengthBreakPoint - 1;
        while (indexBeforeBreakPoint > 0) {
            if (!(fromParentToChildDoNotCopy.contains(secondParent[indexBeforeBreakPoint]))) {
                int freeGen = secondParent[indexBeforeBreakPoint];
                firstChild[firstChildLength--] = freeGen;
            }
            indexBeforeBreakPoint--;
        }

        return firstChild;
    }
}

interface MutationStrategy {
    int[][] startMutation(int[][] parents);
}

class SwapMutation implements MutationStrategy{
    private final Random random = new Random();
    private int percentMutation;

    public SwapMutation(int percentMutation) {
        this.percentMutation = percentMutation;
    }

    @Override
    public int[][] startMutation(int[][] parents) {
        int[] firstChild = parents[0], secondChild = parents[1];

        boolean isMutationFirstChild = mutation();
        if (isMutationFirstChild) {
            randomTwoGens(firstChild);
        }


        boolean isMutationSecondChild = mutation();
        if (isMutationSecondChild) {
            randomTwoGens(secondChild);
        }

        return new int[][]{
                firstChild,
                secondChild
        };
    }

    private int[] randomTwoGens(int[] child) {

        int firstRandomPointGen = random.nextInt(child.length);
        int secondRandomPointGen = random.nextInt(child.length);
        while (firstRandomPointGen == secondRandomPointGen) { //Тут была ошибка. Я сравнивал не 2 указателя, а указатель и элемент по индексу
            secondRandomPointGen = random.nextInt(child.length);
        }

        int tempPointGen = child[firstRandomPointGen];
        child[firstRandomPointGen] = child[secondRandomPointGen];
        child[secondRandomPointGen] = tempPointGen;

        return child;

    }

    private boolean mutation() {
        Random random = new Random();

        int randomNumber = random.nextInt(100);
        return randomNumber < percentMutation;

    }
}

interface FitnessStrategy {
    int fitnessMethod(int[] roadGraph);
}

class GraphPathLengthFitness implements FitnessStrategy{

    private static final Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();

    static {
        graph.put(0, new HashMap<>());
        graph.get(0).put(1, 1);
        graph.get(0).put(2, 3);
        graph.get(0).put(3, 4);
        graph.get(0).put(4, 5);

        graph.put(1, new HashMap<>());
        graph.get(1).put(2, 1);
        graph.get(1).put(3, 4);
        graph.get(1).put(4, 8);

        graph.put(2, new HashMap<>());
        graph.get(2).put(3, 5);
        graph.get(2).put(4, 1);

        graph.put(3, new HashMap<>());
        graph.get(3).put(4, 2);

        graph.put(4, new HashMap<>());
    }

    @Override
    public int fitnessMethod(int[] roadGraph) {
        int sum = 0;
        for (int i = 0; i < roadGraph.length; i++) {
            if (i == roadGraph.length - 1) {
                if (graph.get(0) != null && graph.get(0).get(roadGraph[i]) != null) {
                    sum += graph.get(0).get(roadGraph[i]);
                } else if (graph.get(roadGraph[i]) != null && graph.get(roadGraph[i]).get(0) != null) {
                    sum += graph.get(roadGraph[i]).get(0);
                }
                continue;
            }

            if (graph.get(roadGraph[i]) != null && graph.get(roadGraph[i]).get(roadGraph[i + 1]) != null) {
                sum += graph.get(roadGraph[i]).get(roadGraph[i + 1]);
            } else {
                if (graph.get(roadGraph[i + 1]) != null && graph.get(roadGraph[i + 1]).get(roadGraph[i]) != null) {
                    sum += graph.get(roadGraph[i + 1]).get(roadGraph[i]);
                }
            }
        }
        return sum;
    }
}

class PairChildWithWeight implements Comparable<PairChildWithWeight> {
    private int[] gens;
    private int weight;

    public PairChildWithWeight(int[] gens, int weight) {
        this.gens = gens;
        this.weight = weight;
    }

    public int[] getGens() {
        return gens;
    }

    public void setGens(int[] gens) {
        this.gens = gens;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "PairChildWithWeight{" +
                "gens=" + Arrays.toString(gens) +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(PairChildWithWeight o) {
        return Integer.compare(this.weight, o.weight);
    }
}

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

public class Main {
    public static void main(String[] args) {
        SelectParentStrategy selectParentStrategy = new SelectRandomParentStrategy();
        CrossingParentsStrategy crossover = new SimpleCrossover();
        MutationStrategy mutationStrategy = new SwapMutation(25);
        FitnessStrategy fitnessStrategy = new GraphPathLengthFitness();

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(selectParentStrategy, crossover, mutationStrategy, fitnessStrategy);
        geneticAlgorithm.run();
    }
}