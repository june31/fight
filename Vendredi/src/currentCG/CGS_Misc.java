package currentCG;

import java.util.ArrayList;
import java.util.List;

import tools.scanner.Scan;
import tools.structures.graph.node.Node;

public class CGS_Misc {
    public static void main(String args[]) {
    	String[] t = Scan.readLineArray();
		List<Character> values = new ArrayList<>();
		Node root = new Node();
		values.add('?');
		for (String s : t) {
			Node n = root;
			Ch: for (char c : s.toCharArray()) {
				for (Node c2 : n.links) {
					if (values.get(c2.id) == c) {
						n = c2;
						continue Ch;
					}
				}
				Node n2 = new Node();
				n.links.add(n2);
				n = n2;
				values.add(c);
			}
		}
		System.out.println(values.size() - 1);
    }
}
