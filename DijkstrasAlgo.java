import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

class DijkstrasAlgo {

    class Node {
        int vertex, weight;
    
        Node(int v, int w) {
            this.vertex = v;
            this.weight = w;
        }
    }

    void dijkstrasShortestPath(int src, ArrayList<ArrayList<Node>> adjList, int N) {
        PriorityQueue<Node> pQueue = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                if (node1.weight < node2.weight) return -1;
                if (node1.weight > node2.weight) return 1;
                return 0;
            }
        });
        
        int[] dist = new int[N+1];
        for (int i = 1; i <= N; i++) dist[i] = Integer.MAX_VALUE;
        dist[src] = 0;
        
        pQueue.add(new Node(src, 0));

        while(!pQueue.isEmpty()) {
            Node node = pQueue.poll();
            for (Node neighbour : adjList.get(node.vertex)) {
                int v = neighbour.vertex;
                if (node.weight + neighbour.weight < dist[v]) {
                    dist[v] = node.weight + neighbour.weight;
                    pQueue.add(new Node(v, dist[v]));
                }
            }
        }

        for (int i = 1; i <= N; i++) {
            System.out.print(dist[i]+", ");
        }

    }

    public static void main(String[] args) {
        DijkstrasAlgo sPath = new DijkstrasAlgo();

        int V = 5;
        ArrayList<ArrayList<Node>> adjList = new ArrayList<>();
        for (int i = 0; i <= V; i++) adjList.add(new ArrayList<Node>());

        adjList.get(1).add(sPath.new Node(2, 2));
        adjList.get(2).add(sPath.new Node(1, 2));

        adjList.get(2).add(sPath.new Node(5, 5));
        adjList.get(5).add(sPath.new Node(2, 5));

        adjList.get(2).add(sPath.new Node(3, 4));
        adjList.get(3).add(sPath.new Node(2, 4));

        adjList.get(1).add(sPath.new Node(4, 1));
        adjList.get(4).add(sPath.new Node(1, 1));

        adjList.get(4).add(sPath.new Node(3, 3));
        adjList.get(3).add(sPath.new Node(4, 3));

        adjList.get(3).add(sPath.new Node(5, 1));
        adjList.get(5).add(sPath.new Node(3, 1));

        sPath.dijkstrasShortestPath(1, adjList, V);
    }
}
