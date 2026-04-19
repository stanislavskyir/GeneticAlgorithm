import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class SimpleCrossover implements CrossingParentsStrategy{
    private final Random random = new Random();
    @Override
    public int[] returnCrossingParents(int[] parent1, int[] parent2) {
        int breakPoint = 2;
        int[] child = new int[parent1.length];

        Set<Integer> set = new HashSet<>();

        int i = 0;
        int lenParent1 = parent1.length;

        int currentPoint = breakPoint;
        while (i < lenParent1) {

            if (i < breakPoint) {
                child[i] = parent1[i];
                set.add(parent1[i]);
            }

            if (i >= breakPoint) {
                if (!set.contains(parent2[i])) {
                    child[currentPoint] = parent2[i];
                    set.add(parent2[i]);
                    currentPoint++;
                }
            }

            i++;
        }

        int currentIndex = breakPoint;
        while (currentIndex < parent1.length){
            if (!(set.contains(parent1[currentIndex]))) {
                int freeGen = parent1[currentIndex];
                child[currentPoint++] = freeGen;
                set.add(freeGen);
            }
            currentIndex++;
        }

        return child;
    }
}