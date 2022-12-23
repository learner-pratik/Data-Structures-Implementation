import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class KruskalsAlgo {

    class Node {
        int u, v, weight;
        Node(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }
    }

    int findParent(int u, int[] parent) {
        if (u == parent[u]) return u;
        // using path compression
        return parent[u] = findParent(parent[u], parent);
    } 

    void union(int u, int v, int[] par, int[] rank) {
        u = findParent(u, par);
        v = findParent(v, par);
        if (rank[u] > rank[v]) par[v] = u;
        else if (rank[u] < rank[v]) par[u] = v;
        else {
            // same rank
            par[u] = v;
            rank[v]++;
        }
    }

    void kruskalsMST(int N, ArrayList<Node> adjList) {
        // sort edges by increasing weight
        Collections.sort(adjList, new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                if (node1.weight < node2.weight) return -1;
                if (node1.weight > node2.weight) return 1;
                return 0;
            }
        });

        // disjoint set data structures
        int[] parent = new int[N+1];
        int[] rank = new int[N+1];
        // make sets
        for (int i = 1; i <= N; i++) parent[i] = i;

        int mstCost = 0;
        ArrayList<Node> mst = new ArrayList<Node>();
        // iterate over all edges
        for (Node node : adjList) {
            // vertices belong to different sets
            if (findParent(node.u, parent) != findParent(node.v, parent)) {
                mstCost += node.weight;
                mst.add(node);
                // combine the sets
                union(node.u, node.v, parent, rank);
            }
        }

        System.out.println("MST Cost -> " + mstCost);
        for (Node node : mst) {
            System.out.println("u: "+node.u+", v: "+node.v+", weight: "+node.weight);
        }
    }

    public static void main(String[] args) {
        KruskalsAlgo kAlgo = new KruskalsAlgo();
        int V = 6;
        ArrayList<Node> adjList = new ArrayList<Node>();

        // adding graph edges
        adjList.add(kAlgo.new Node(1, 2, 2));
        adjList.add(kAlgo.new Node(1, 4, 1));
        adjList.add(kAlgo.new Node(1, 5, 4));
        adjList.add(kAlgo.new Node(2, 3, 3));
        adjList.add(kAlgo.new Node(2, 4, 3));
        adjList.add(kAlgo.new Node(2, 6, 7));
        adjList.add(kAlgo.new Node(3, 4, 5));
        adjList.add(kAlgo.new Node(3, 6, 8));
        adjList.add(kAlgo.new Node(4, 5, 9));

        kAlgo.kruskalsMST(V, adjList);
    }
}