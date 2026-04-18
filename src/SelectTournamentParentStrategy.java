import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class SelectTournamentParentStrategy implements SelectParentStrategy{
    private final Random random = new Random();

    private final FitnessStrategy fitnessStrategy;

    public SelectTournamentParentStrategy(FitnessStrategy fitnessStrategy) {
        this.fitnessStrategy = fitnessStrategy;
    }

    /*
    Да, верно.

    Три случайных кандидата → считаешь fitness → выбираешь лучшего → он становится родителем.

    Это и есть базовый турнирный отбор.
     */

    private int[][] selectRandomNParents(int[][] startPopulation){
        int countParents = 3;
        int[][] cloneStartPopulation = startPopulation.clone();

        int i = 0;
        int[][] futureParents = new int[countParents][cloneStartPopulation[0].length];

        Set<Integer> isIndexInArray = new HashSet<>();

        while (i < countParents){
            int j = 0;
            int randomIndex = random.nextInt(cloneStartPopulation.length);
            while (isIndexInArray.contains(randomIndex)){
                randomIndex = random.nextInt(cloneStartPopulation.length);
            }
            isIndexInArray.add(randomIndex);
            while (j < cloneStartPopulation[randomIndex].length){
                futureParents[i][j] = cloneStartPopulation[randomIndex][j];
                j++;
            }

            i++;
        }


        //System.out.println("FutureParents: " + Arrays.deepToString(futureParents));
        return futureParents;
    }


    private int[] returnTournamentParent(int[][] startPopulation){
        int[][] parents = selectRandomNParents(startPopulation);
        int index = 0;
        int maxSum = fitnessStrategy.fitnessMethod(parents[index]);

//        System.out.println("StartMaxSumArray: " + Arrays.toString(parents[index]));
//        System.out.println("StartMaxSum: " + maxSum);
//        System.out.println("StartMaxIndex: " + index);

        for (int i = 1; i < parents.length; i++) {
            int sum = fitnessStrategy.fitnessMethod(parents[i]);
            if(sum < maxSum){
                maxSum = sum;
                index = i;
            }

//            System.out.println("WhileArray: " + Arrays.toString(parents[i]));
//            System.out.println("WhileSum: " + sum);
//            System.out.println("WhileIndex: " +index);
        }

//        System.out.println("WinArray: " + Arrays.toString(parents[index]));
//        System.out.println("WinSum: " + maxSum);
//        System.out.println("WinIndex: " + index);
        return parents[index];

    }

    @Override
    public int[][] selectParent(int[][] startPopulation) {
        return new int[][]{
                returnTournamentParent(startPopulation),
                returnTournamentParent(startPopulation)
        };
    }
}