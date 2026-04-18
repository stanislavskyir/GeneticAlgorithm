import java.util.Random;

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
