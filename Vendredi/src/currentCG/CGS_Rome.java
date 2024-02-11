package currentCG;

import tools.collections.node.Ln;
import tools.scanner.list.ScanLn;
import tools.structures.graph.node.Node;

public class CGS_Rome {
	
	public static void main(String[] args) {
		ScanLn.readNodesDual();
		System.out.println(toRome(Node.get(1), new Ln()));
	}
	
	private static int toRome(Node town, Ln path) {
		if (town.name.equals("100")) return 1;
		path.add(town);
		int sum = 0;
		for (Node n: town.links) if (!path.contains(n)) sum += toRome(n, new Ln(path));
		return sum;
	}
}
