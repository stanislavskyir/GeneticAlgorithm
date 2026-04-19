import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        CrossingParentsStrategy crossover = new SimpleCrossover();
        MutationStrategy mutationStrategy = new SwapMutation(25);
        FitnessStrategy fitnessStrategy = new GraphPathLengthFitness();
        SelectParentStrategy selectTourParentStrategy = new SelectTournamentParentStrategy(fitnessStrategy);

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(selectTourParentStrategy, crossover, mutationStrategy, fitnessStrategy);
        //geneticAlgorithm.run();


        //int[] firstParent = {0, 1, 2, 3, 4, 5, 6}, secondParent = {3, 1, 0, 6, 5, 4, 2};
        //int[] firstParent = {0, 1, 2, 3, 4, 5, 6}, secondParent = {6, 5, 4, 3, 2, 1, 0};
        //int[] firstParent = {0, 1, 2, 4, 3, 5, 6}, secondParent = {6, 5, 4, 3, 2, 1, 0};
        //int[] firstParent = {0,1,2,3,4,5,6}, secondParent = {2,0,4,6,1,3,5};
        //int[] firstParent = {0,1,2,3,4,5,6}, secondParent = {1,3,5,0,2,4,6};

//        int[] firstParent = {0,1,2,3,4,5,6};
//        int[] secondParent = {5,0,1,2,3,4,6};
//        int[] firstParent = {0,1,2,3,4,5,6};
//        int[] secondParent = {4,2,0,1,6,3,5};
        //int[] firstParent = {0, 1, 2, 3, 4, 5, 6}, secondParent = {0, 1, 2, 3, 4, 5, 6};
        int[] firstParent = {0,1,2,3,4}, secondParent = {2,3,0,4,1};
        int[] firstChild = crossover.returnCrossingParents(firstParent, secondParent);
        int[] secondChild = crossover.returnCrossingParents(secondParent, firstParent);

        System.out.println("1st parent: " + Arrays.toString(firstParent));
        System.out.println("2nd parent: " + Arrays.toString(secondParent));

        System.out.println("1st child: " + Arrays.toString(firstChild));
        System.out.println("2nd child: " + Arrays.toString(secondChild));

    }

}