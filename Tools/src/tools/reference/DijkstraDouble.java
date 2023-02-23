package tools.reference;
public class DijkstraDouble {
	private final int n;
	private final int[][] g;
	
	public DijkstraDouble(int[][] graph) {
		g = graph;
		n = graph.length;
	}

	public double[] calc(int pos) {
		double dist[] = new double[n];
		for (int i = 0; i < n; i++) dist[i] = Double.POSITIVE_INFINITY;
		dist[pos] = 0;
		boolean[] set = new boolean[n];
		for (int i = 0; i < n - 1; i++) {
			double min = Double.POSITIVE_INFINITY;
			int u = -1;
			for (int v = 0; v < n; v++) {
				if (!set[v] && dist[v] < min) {
					min = dist[v];
					u = v;
				}
			}
			set[u] = true;
			for (int v = 0; v < n; v++)
				if (!set[v] && g[u][v] != 0 && dist[u] + g[u][v] < dist[v])
					dist[v] = dist[u] + g[u][v];
		}
		return dist;
	}
}
