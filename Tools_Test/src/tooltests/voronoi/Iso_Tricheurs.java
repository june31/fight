package tooltests.voronoi;

import tools.collections.int32.L;
import tools.collections.node.Ln;
import tools.scanner.Scan;
import tools.scanner.list.ScanLn;
import tools.structures.graph.node.Node;
import tools.tables.Table;
import tools.voronoi.VoronoiGraph;

// Challenge MDF 2023, grande finale exo 2
// https://www.isograd-testingservices.com//FR/solutions-challenges-de-code?cts_id=104
public class Iso_Tricheurs {
	public static void main(String[] args) {
		Node.range1(Scan.readInt());
		int M = Scan.readInt();
		Ln players = ScanLn.readNodes();
		players.add(Node.get(1));
		for (int i = 0; i < M; i++) Node.buildDual(Scan.readString(), Scan.readString());
		
		var vor = new VoronoiGraph();
		vor.diffuse(players, true); // Cheaters do not block themselves, so priority = true and player = last 
		L res = new L();
		for (var e: vor.nodeOwners.entrySet()) if (e.getValue() == players.size() - 1) res.add(e.getKey().id);
		Table.println(res.sortedUp());
	}
}
