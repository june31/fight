package isograd;

import java.util.Stack;

import tools.collections.int32.L;
import tools.collections.map.Mili;
import tools.collections.node.Ln;
import tools.scanner.Scan;
import tools.structures.graph.node.Node;
import tools.tables.Table;

// Isograd - Tech inclusive CA Collectif 2023
public class Iso_AntagonistZones {
	public static void main(String[] args) {
		int C = Scan.readInt();
		int L = Scan.readInt();
		int[][] map = new int[L][C];
		int nb = Scan.readInt();
		int ai = nb + 1;
		Mili antas = new Mili();
		for (int i = 1; i <= nb; i++) {
			Node.build(i);
			String s = Scan.readLine().replace('-', ' ');
			boolean zone = s.charAt(0) == 'I';
			L a = new L(s);
			if (zone) {
				for (int c = a.get(0); c <= a.get(1); c++) {
					for (int l = a.get(2); l <= a.get(3); l++) {
						if (map[l][c] != 0) Node.buildDual(i, map[l][c]);
						else map[l][c] = i;
					}
				}
			} else {
				int p1 = map[a.get(1)][a.get(0)];
				if (p1 == 0) {
					p1 = i;
					Node.build(p1);
					map[a.get(1)][a.get(0)] = p1;
				}
				int p2 = map[a.get(3)][a.get(2)];
				if (p2 == 0) {
					p2 = ai++;
					Node.build(p2);
					map[a.get(3)][a.get(2)] = p2;
				}
				antas.add(p1, p2);
				antas.add(p2, p1);
			}
		}

		int[] vals = new int[ai];
		vals[0] = 'A';
		Stack<Integer> stack = new Stack<>();
		for (int i = 1; i < ai; i++) stack.push(i);
		
		while (!stack.isEmpty()) {
			int i = stack.pop();
			L reverse = new L();
			if (vals[i] != 'A' && vals[i] != 'B') {
				Ln nodes = Node.get(i).propagate();
				boolean hasA = false;
				boolean hasB = false;
				for (Node n : nodes) {
					reverse.addAll(antas.getOrEmpty(n.id));
					if (vals[n.id] == 'a') hasA = true;
					else if (vals[n.id] == 'b') hasB = true;
				}
				if (hasA && hasB) {
					System.out.println("IMPOSSIBLE");
					return;
				}
				int v = hasB ? 'B' : 'A';
				for (Node n : nodes) vals[n.id] = v;
				int revB = v ^ 35; // A -> b, B -> a
				for (int r : reverse) {
					if (vals[r] == 0) {
						vals[r] = revB;
						stack.push(r);
					} else if ((vals[r] | 32) != revB) {
						System.out.println("IMPOSSIBLE");
						return;
					}
				}
			}
		}

		for (int l = 0; l < L; l++) for (int c = 0; c < C; c++) map[l][c] = vals[map[l][c]];
		Table.printMap(map);
	}
}
