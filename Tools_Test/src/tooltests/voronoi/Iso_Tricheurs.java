package tooltests.voronoi;

import tools.collections.int32.L;
import tools.collections.node.Ln;
import tools.scanner.Scan;
import tools.structures.graph.node.Node;
import tools.tables.Table;
import tools.voronoi.VoronoiGraph;

// Challenge MDF 2023, grande finale exo 2
// https://www.isograd-testingservices.com//FR/solutions-challenges-de-code?cts_id=104
public class Iso_Tricheurs {
	public static void main(String[] args) {
		int N = Scan.readInt();
		int M = Scan.readInt();
		int T = Scan.readInt();
		var nodes = Ln.range1(N);
		var cheaters = Scan.readL(T).stream().map(i -> Node.fromName(i)).toList();
		var players = Ln.of(Node.fromName(1));
		players.addAll(cheaters);
		for (int i = 0; i < M; i++)
			nodes.addLinkDual(Node.fromName(Scan.readString()), Node.fromName(Scan.readString()));

		var vor = new VoronoiGraph();
		vor.diffuse(players, false);
		L res = new L();
		for (var e: vor.nodeOwners.entrySet()) if (e.getValue() == 0) res.add(e.getKey().x);
		Table.println(res.sortedUp());
	}
}
