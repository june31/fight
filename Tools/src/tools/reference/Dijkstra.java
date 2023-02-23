package tools.reference;
public class Dijkstra {
	private final int n;
	private final int[][] g;
	
	public Dijkstra(int[][] graph) {
		g = graph;
		n = graph.length;
	}

	public int[] calc(int pos) {
		int dist[] = new int[n];
		for (int i = 0; i < n; i++) dist[i] = Integer.MAX_VALUE;
		dist[pos] = 0;
		boolean[] set = new boolean[n];
		for (int i = 0; i < n - 1; i++) {
			int min = Integer.MAX_VALUE;
			int u = -1;
			for (int v = 0; v < n; v++) {
				if (!set[v] && dist[v] < min) {
					min = dist[v];
					u = v;
				}
			}
			set[u] = true;
			for (int v = 0; v < n; v++)
				if (!set[v] && g[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + g[u][v] < dist[v])
					dist[v] = dist[u] + g[u][v];
		}
		return dist;
	}
}
