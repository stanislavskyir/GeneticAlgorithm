import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        //SelectParentStrategy selectParentStrategy = new SelectRandomParentStrategy();

        CrossingParentsStrategy crossover = new SimpleCrossover();
        MutationStrategy mutationStrategy = new SwapMutation(25);
        FitnessStrategy fitnessStrategy = new GraphPathLengthFitness();
        SelectParentStrategy selectTourParentStrategy = new SelectTournamentParentStrategy(fitnessStrategy);

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(selectTourParentStrategy, crossover, mutationStrategy, fitnessStrategy);
        geneticAlgorithm.run();

//       int[][] startPopulation = {
//                {0, 1, 2, 3, 4},
//                {0, 1, 4, 2, 3},
//
//                {0, 2, 1, 3, 4},
//                {0, 2, 1, 4, 3},
//                {0, 2, 3, 1, 4},
//                {0, 2, 4, 1, 3},
//
//                {0, 3, 1, 2, 4},
//                {0, 3, 2, 4, 1},
//                {0, 3, 4, 1, 2},
//
//                {0, 4, 1, 2, 3},
//                {0, 4, 2, 1, 3},
//                {0, 4, 3, 1, 2},
//                {0, 4, 3, 2, 1}
//        };
//        SelectParentStrategy selectTourParentStrategy = new SelectTournamentParentStrategy(fitnessStrategy);
//        selectTourParentStrategy.selectParent(startPopulation);
    }

}