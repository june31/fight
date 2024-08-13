package isograd;

import java.util.HashMap;

import tools.collections.int32.Si;
import tools.collections.node.Ln;
import tools.mapper.MapLn;
import tools.scanner.list.ScanLn;
import tools.strings.S;
import tools.structures.graph.node.Node;

// PAS TESTÉ : site web en panne (BattleDev 2019/5)
// https://www.isograd-testingservices.com/FR/solutions-challenges-de-code?cts_id=60
public class Iso_VieilleCarte {
	public static void main(String[] args) {
		Ln l1 = ScanLn.readNodesDual();
		Ln l2 = ScanLn.readNodesDual();
		
		var m1 = new HashMap<Si, Node>(); 
		var m2 = new HashMap<Node, Si>();
		for (int i = 0; i < l1.size(); i++) {
			Node.forEach(n -> n.x = 0);
			Node n1 = l1.get(i);
			Node n2 = l2.get(i);
			n1.x = 1;
			n2.x = 1;
			for (int j = 0; j < 7; j++) {
				Node.forEach(n -> n.y = n.x);
				Node.forEach(n -> n.links.forEach(m -> m.y += n.x));
				Node.forEach(n -> n.x = n.y);
			}
			m1.put(new Si(MapLn.toXL(l1)), n1);
			m2.put(n2, new Si(MapLn.toXL(l2)));
		}
		m2.forEach((k, v) -> S.o(k.name, m1.get(v).name));
	}
}
