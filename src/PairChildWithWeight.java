import java.util.Arrays;

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