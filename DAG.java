import java.util.ArrayList;
import java.util.Stack;

public class DAG {
    
    class Pair {
        private int v, weight;
        
        Pair(int v, int weight) {
            this.v = v;
            this.weight = weight;
        }

        public int getV() {
            return v;
        }

        public int getWeight() {
            return weight;
        }
    }

    private void dfsHelper(int v, boolean[] visited, ArrayList<ArrayList<Pair>> adjList, Stack<Integer> stack) {
        visited[v] = true;
        for (Pair neighbour : adjList.get(v)) {
            if (!visited[neighbour.getV()]) dfsHelper(neighbour.getV(), visited, adjList, stack);
        }
        stack.push(v);
    }

    int[] topologicalSort(int V, ArrayList<ArrayList<Pair>> adjList) {
        Stack<Integer> stack = new Stack<Integer>();
        boolean[] visited = new boolean[V];
        int[] topoSort = new int[V];

        for (int v = 0; v < V; v++) {
            if (!visited[v]) dfsHelper(v, visited, adjList, stack);
        }

        for (int v = 0; v < V; v++) topoSort[v] = stack.pop();
        return topoSort;
    }

    void shortestPath(int src, int V, int[] vertices, ArrayList<ArrayList<Pair>> adjList) {
        int[] dist = new int[V];
        int maxVal = 100;
        for (int i = 0; i < V; i++) dist[i] = maxVal;
        dist[src] = 0;

        for (int v : vertices) {
            // node has been reached previously
            if (dist[v] != maxVal) {
                for (Pair neighbour : adjList.get(v)) {
                    int u = neighbour.getV();
                    if (dist[v] + neighbour.getWeight() < dist[u]) {
                        dist[u] = dist[v] + neighbour.getWeight();
                    }
                }
            } 
        }

        for (int v = 0; v < V; v++) System.out.print(dist[v]+", ");
    }

    public static void main(String[] args) {
        DAG dag = new DAG();
        int V = 6;
        ArrayList<ArrayList<Pair>> adjList = new ArrayList<>();
        for (int v = 0; v < V; v++) adjList.add(new ArrayList<Pair>());

        // add weighted edges
        adjList.get(0).add(dag.new Pair(1, 2));
        adjList.get(0).add(dag.new Pair(4, 1));
        adjList.get(1).add(dag.new Pair(2, 3));
        adjList.get(2).add(dag.new Pair(3, 6));
        adjList.get(4).add(dag.new Pair(2, 2));
        adjList.get(4).add(dag.new Pair(5, 4));
        adjList.get(5).add(dag.new Pair(3, 1));

        int[] topoSort = dag.topologicalSort(V, adjList);
        dag.shortestPath(0, V, topoSort, adjList);
    }
}
