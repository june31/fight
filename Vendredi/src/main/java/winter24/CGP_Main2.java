package winter24;

import java.util.ArrayList;

import tools.bfs.BFS;
import tools.collections.pos.Lp;
import tools.collections.string.Ls;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tuple.Pos;

class CGP_Main2 {

	public static void main(String args[]) {
		//Scan.setDebugMode(true);
		int nc = Scan.readInt();
		int nl = Scan.readInt();
		int turn = 0;
		Pos refA = null;

		// game loop
		while (true) {
			turn++;
			int[][] map = new int[nl][nc];
			int entityCount = Scan.readInt();
			Organ R = null;
			var myOrgans = new ArrayList<Organ>();
			var oppOrgans = new ArrayList<Organ>();
			for (int i = 0; i < entityCount; i++) {
				int c = Scan.readInt();
				int l = Scan.readInt();
				Pos p = new Pos(l,c);
				// WALL, ROOT, BASIC, TENTACLE, HARVESTER, SPORER, A, B, C, D
				String ent = Scan.readString();
				int owner = Scan.readInt(); // 1 if your organ, 0 if enemy organ, -1 if neither
				Lp la = new Lp();
				Lp lb = new Lp();
				Lp lc = new Lp();
				Lp ld = new Lp();
				int item = switch (ent + owner) {
				case "WALL-1" -> '#';
				case "ROOT0" -> 'r';
				case "ROOT1" -> 'R';
				case "BASIC0" -> 'b';
				case "BASIC1" -> 'B';
				case "TENTACLE0" -> 't';
				case "TENTACLE1" -> 'T';
				case "HARVESTER0" -> 'h';
				case "HARVESTER1" -> 'H';
				case "SPORER0" -> 's';
				case "SPORER1" -> 'S';
				case "A-1" -> { la.add(p); yield 'A'; }
				case "B-1" -> { lb.add(p); yield 'B'; }
				case "C-1" -> { lc.add(p); yield 'C'; }
				case "D-1" -> { ld.add(p); yield 'D'; }
				default -> '?';
				};
				map[l][c] = item;
				int organId = Scan.readInt(); // id of this entity if it's an organ, 0 otherwise
				String organDir = Scan.readString(); // N,E,S,W or X if not an organ
				int organParentId = Scan.readInt();
				int organRootId = Scan.readInt();
				if (owner == 1) {
					Organ organ = new Organ(organId, l, c);
					if (item == 'R') R = organ;
					myOrgans.add(organ);
				}
			}
			int myA = Scan.readInt();
			int myB = Scan.readInt();
			int myC = Scan.readInt();
			int myD = Scan.readInt(); // your protein stock
			int oppA = Scan.readInt();
			int oppB = Scan.readInt();
			int oppC = Scan.readInt();
			int oppD = Scan.readInt(); // opponent's protein stock
			int requiredActionsCount = Scan.readInt(); // your number of organisms, output an action for each one in any order
			S.e(requiredActionsCount);

			int maxId = 0;
			Organ o = null;
			for (Organ q: myOrgans) {
				if (q.id() > maxId) {
					maxId = q.id();
					o = q;
				}
			}
			for (int i = 0; i < requiredActionsCount; i++) {
				if (turn == 0) S.o("GROW " + o.id() + " " + (o.c() + 1) + " " + o.l() + " BASIC");
				else if (turn == 10) S.o("GROW " + o.id() + " " + (o.c() + 1) + " 2 TENTACLE E");
				else if (turn == 11) S.o("GROW " + o.id() + " " + o.c() + " 3 TENTACLE E");
				else if (turn == 12) S.o("GROW " + o.id() + " " + o.c() + " 4 TENTACLE E");
				else if (turn == 13) S.o("GROW " + o.id() + " " + o.c() + " 5 TENTACLE E");
				else S.o("GROW " + o.id() + " " + (o.c() + 1) + " 5 TENTACLE E");
			}
		}
	}

	private static char getDir(int l1, int c1, int l2, int c2) {
		if (l1 == l2)
			return c1 < c2 ? 'E' : 'W';
		return l1 < l2 ? 'S' : 'N';
	}
}