package currentCG;

import tools.enumeration.combinations.Combinations;
import tools.scanner.Scan;
import tools.scanner.list.ScanLs;
import tools.strings.S;
import tools.structures.graph.node.Node;

public class CGS_Alt2 {
	public static void main(String[] args) {
		String target = Scan.readLine();
		for (String movie: ScanLs.readLines()) {
			movie = movie.substring(movie.indexOf(':') + 2);
			for (var pair: new Combinations<String>(movie.split(", "), 2)) {
				Node.buildDual(pair.get(0), pair.get(1));
			}
		}
		S.o(Node.dist(Node.get(target), Node.get("Kevin Bacon")));
	}
}
