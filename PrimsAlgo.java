import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class PrimsAlgo {
    
    class Node {
        int v, weight;
        Node(int v, int weight) {
            this.v = v;
            this.weight = weight;
        }
    }

    void primsMST(int N, ArrayList<ArrayList<Node>> adjList) {
        // defining data structures
        int[] key = new int[N];
        int[] par = new int[N];
        boolean[] mst = new boolean[N];

        for (int i = 0; i < N; i++) par[i] = -1;
        for (int i = 0; i < N; i++) key[i] = 10005;
        key[0] = 0;

        // creating min-heap to get min weighted node
        PriorityQueue<Node> pQueue = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node node0, Node node1) {
                if (node0.weight < node1.weight) return -1;
                if (node0.weight > node1.weight) return 1;
                return 0;
            }
        });

        int mstCost = 0;
        pQueue.add(new Node(0, 0));
        for (int e = 0; e < N-1; e++) {
            Node sNode = pQueue.poll();
            int u = sNode.v;
            mstCost += sNode.weight;
            mst[u] = true;

            // iterating over all incident vertices
            for (Node node : adjList.get(u)) {
                if (node.weight < key[node.v]) {
                    key[node.v] = node.weight;
                    par[node.v] = u;
                    pQueue.add(new Node(node.v, node.weight));
                }
            }
        }
        mstCost += pQueue.poll().weight;

        System.out.println("MST Cost -> " + mstCost);
        for (int p = 1; p < N; p++) {
            System.out.println("u: "+par[p]+", v: "+p+", weight: "+key[p]);
        }
    }

    public static void main(String[] args) {
        PrimsAlgo pAlgo = new PrimsAlgo();
        int V = 5;
        ArrayList<ArrayList<Node>> adjList = new ArrayList<>();
        for (int v = 0; v < V; v++) adjList.add(new ArrayList<Node>());

        // building graph
        adjList.get(0).add(pAlgo.new Node(1, 2));
        adjList.get(0).add(pAlgo.new Node(3, 6));
        adjList.get(1).add(pAlgo.new Node(2, 3));
        adjList.get(1).add(pAlgo.new Node(3, 8));
        adjList.get(1).add(pAlgo.new Node(4, 5));
        adjList.get(2).add(pAlgo.new Node(4, 7));

        pAlgo.primsMST(V, adjList);
    }
}
