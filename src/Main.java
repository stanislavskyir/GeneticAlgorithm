

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