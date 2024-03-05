package current;

import tools.collections.string.Ls;
import tools.mapper.MapLn;
import tools.scanner.Scan;
import tools.strings.S;
import tools.structures.graph.node.Node;

// IsoGrad - Shaker 2023 - Exercise 4
// https://www.isograd-testingservices.com/FR/solutions-challenges-de-code?cts_id=121
public class ISO_Shaker2023_Jeu_de_mots2 {
	public static void main(String[] args) {
		for (String word: Scan.readLines())
			for (int i = 0; i < word.length(); i++)
				Node.buildSingle(word.substring(0, i), word.substring(0, i + 1));
		
		Ls winners = MapLn.toLs(Node.get("").links.filtered(n -> play(n, true)));
		if (winners.isEmpty()) S.o("impossible");
		else winners.sortedUp().printLn("\n");
	}

	private static boolean play(Node n, boolean win) {
		if (win) for (Node c: n.links) win &= play(c, false);
		else for (Node c: n.links) win |= play(c, true);
		return win;
	}
}
