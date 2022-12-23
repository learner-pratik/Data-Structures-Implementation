import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {

    ArrayList<Integer> bfs(int V, ArrayList<ArrayList<Integer>> adjList) {
        ArrayList<Integer> bfsTraversal = new ArrayList<Integer>();
        boolean[] visited = new boolean[V+1];

        for (int v = 1; v <= V; v++) {
            if (!visited[v]) {
                Queue<Integer> queue = new LinkedList<Integer>();
                queue.add(v);
                visited[v] = true;

                while (!queue.isEmpty()) {
                    Integer vertex = queue.poll();
                    bfsTraversal.add(vertex);
                    for (Integer neighbour : adjList.get(vertex)) {
                        if (!visited[neighbour]) {
                            queue.add(neighbour);
                            visited[neighbour] = true;
                        }
                    }
                }
            }
        }

        return bfsTraversal;
    }

    void dfsHelper(int vertex, boolean[] visited, ArrayList<ArrayList<Integer>> adjList, ArrayList<Integer> dfsTraversal) {
        dfsTraversal.add(vertex);
        visited[vertex] = true;
        for (Integer neighbour : adjList.get(vertex)) {
            if (!visited[neighbour]) dfsHelper(neighbour, visited, adjList, dfsTraversal);
        }
    }

    ArrayList<Integer> dfs(int V, ArrayList<ArrayList<Integer>> adjList) {
        ArrayList<Integer> dfsTraversal = new ArrayList<>();
        boolean[] visited = new boolean[V+1];

        for (int v = 1; v <= V; v++) {
            if (!visited[v]) dfsHelper(v, visited, adjList, dfsTraversal);
        }

        return dfsTraversal;
    }

    public static void main(String[] args) {
        Graph graph = new Graph();

        int n = 7;
        ArrayList<ArrayList<Integer>> adjacencyList = new ArrayList<>();
        for (int i = 0; i <= n; i++) adjacencyList.add(new ArrayList<Integer>());
        // adding edges
        adjacencyList.get(1).add(2);
        adjacencyList.get(2).add(1);

        adjacencyList.get(2).add(3);
        adjacencyList.get(3).add(2);

        adjacencyList.get(2).add(7);
        adjacencyList.get(7).add(2);

        adjacencyList.get(3).add(5);
        adjacencyList.get(5).add(3);

        adjacencyList.get(5).add(7);
        adjacencyList.get(7).add(5);

        adjacencyList.get(4).add(6);
        adjacencyList.get(6).add(4);

        ArrayList<Integer> bfsList = graph.bfs(n, adjacencyList);
        System.out.println(bfsList.toString());

        ArrayList<Integer> dfsList = graph.dfs(n, adjacencyList);
        System.out.println(dfsList.toString());
    }
}
