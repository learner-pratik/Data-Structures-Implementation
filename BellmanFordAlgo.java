import java.util.ArrayList;

public class BellmanFordAlgo {
    
    class Edge {
        int u, v, w;
        
        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    void bellmanFordShortestPath(int n, ArrayList<Edge> edgeList) {
        int[] dist = new int[n];
        for (int i = 0; i < n; i++) dist[i] = 1000000007;
        dist[0] = 0;

        for (int i = 0; i < n-1; i++) {
            for (Edge edge : edgeList) {
                if (dist[edge.u]+edge.w < dist[edge.v]) {
                    dist[edge.v] = dist[edge.u]+edge.w;
                }
            }
        }

        // check for negative weight cycle
        for (Edge edge : edgeList) {
            if (dist[edge.u]+edge.w < dist[edge.v]) {
                System.out.println("Negative weight cycle found");
                break;
            }
        }

        for (int i = 0; i < n ; i++) {
            System.out.print(dist[i]+", ");
        }
    }

    public static void main(String[] args) {
        BellmanFordAlgo sPath = new BellmanFordAlgo();
        int V = 6;
        ArrayList<Edge> list = new ArrayList<Edge>();

        list.add(sPath.new Edge(3, 2, 6));
        list.add(sPath.new Edge(5, 3, 1));
        list.add(sPath.new Edge(0, 1, 5));
        list.add(sPath.new Edge(1, 5, -3));
        list.add(sPath.new Edge(1, 2, -2));
        list.add(sPath.new Edge(3, 4, -2));
        list.add(sPath.new Edge(2, 4, 3));

        sPath.bellmanFordShortestPath(V, list);
    }
}
