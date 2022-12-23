import java.util.ArrayList;
import java.util.Stack;

public class TopoSort {

    void dfsTopoSort(int v, boolean[] visited, ArrayList<ArrayList<Integer>> adjList, Stack<Integer> stack) {
        visited[v] = true;
        for (Integer neighbour : adjList.get(v)) {
            if (!visited[neighbour]) dfsTopoSort(neighbour, visited, adjList, stack);
        }
        stack.push(v);
        return;
    }

    void topologicalSort(int V, ArrayList<ArrayList<Integer>> adjList) {
        boolean[] visited = new boolean[V];
        Stack<Integer> stack = new Stack<>();

        for (int v = 0; v < V; v++) {
            if (!visited[v]) dfsTopoSort(v, visited, adjList, stack);
        }

        while (!stack.isEmpty()) System.out.print(stack.pop()+", ");
        return;
    }

    public static void main(String[] args) {
        TopoSort tSort = new TopoSort();
        int V = 6;
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();
        for (int v = 0; v < V; v++) adjList.add(new ArrayList<Integer>());

        // add graph edges
        adjList.get(2).add(3);
        adjList.get(3).add(1);
        adjList.get(4).add(0);
        adjList.get(4).add(1);
        adjList.get(5).add(0);
        adjList.get(5).add(2);

        tSort.topologicalSort(V, adjList);
    }
}
