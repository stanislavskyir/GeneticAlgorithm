import java.util.HashSet;
import java.util.Set;

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