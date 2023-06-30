package tooltests.voronoi;

import tools.scanner.Scan;
import tools.structures.graph.Graph;
import tools.tables.Table;
import tools.voronoi.VoronoiGraph;

// Challenge MDF 2023, grande finale exo 2
// https://www.isograd-testingservices.com//FR/solutions-challenges-de-code?cts_id=104
public class Iso_Tricheurs {
	public static void main(String[] args) {
		int N = Scan.readInt();
		int M = Scan.readInt();
		int T = Scan.readInt();
		int[] cheaters = Table.dec(Scan.readIntArray(T));

		Graph g = new Graph();
		for (int i = 0; i < N; i++) g.addNode();
		for (int i = 0; i < M; i++) g.dualLink(g.getNode(Scan.readInt() - 1), g.getNode(Scan.readInt() - 1));

		VoronoiGraph vor = new VoronoiGraph(g);
		vor.diffuse(Table.concat(cheaters, 0), true);
		Table.println(Table.inc(Table.findAll(vor.nodeOwners, T))); 
	}
}
