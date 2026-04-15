import java.util.Random;

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