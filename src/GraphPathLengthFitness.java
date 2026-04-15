import java.util.HashMap;
import java.util.Map;

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