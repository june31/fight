package winter24;

import java.util.ArrayList;

import tools.bfs.BFS;
import tools.collections.pos.Lp;
import tools.collections.string.Ls;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tuple.Pos;

class CGP_Main {

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
			if (refA != null) map[refA.l][refA.c] = '#';
			BFS bfs = new BFS(map);
			bfs.setWall('#', 'R', 'r', 'B', 'b');
			//Table.debug(map);
			int min = Integer.MAX_VALUE;
			int max = 0;
			Organ src = null;
			Pos dst = null;
			Pos posA = null;
			bfs.setEnd('A');
			bfs.setTestStart(false);
			for (Organ o: myOrgans) {
				bfs.diffuse(o.l(), o.c());
				if (refA == null) {
					if (bfs.found && min > bfs.turn) {
						min = bfs.turn;
						src = o;
						posA = new Pos(bfs.l2, bfs.c2);
						dst = bfs.shortestPath().get(1);
					}
				} else {
					if (max < bfs.turn) {
						max = bfs.turn;
						src = o;
						posA = new Pos(bfs.l2, bfs.c2);
						if (map[posA.l][posA.c+1] == 0) dst = new Pos(posA.l, posA.c+1);
						else if (map[posA.l][posA.c - 1] == 0)
							dst = new Pos(posA.l, posA.c - 1);
						else if (map[posA.l + 1][posA.c] == 0)
							dst = new Pos(posA.l + 1, posA.c);
						else dst = new Pos(posA.l - 1, posA.c);
					}
				}
			}
			Ls actions = new Ls();
			if (src != null) {
				if (refA == null && min == 2) {
					refA = posA;
					actions.add("GROW " + src.id() + " " + dst.c + " " + dst.l + " HARVESTER " + getDir(dst.l, dst.c, posA.l, posA.c));
				}
				actions.add("GROW " + src.id() + " " + dst.c + " " + dst.l + " BASIC");
			}
			for (int i = 0; i < requiredActionsCount; i++) {
				if (i < actions.size()) S.o(actions.get(i));
				else S.o("WAIT");
			}
		}
	}

	private static char getDir(int l1, int c1, int l2, int c2) {
		if (l1 == l2)
			return c1 < c2 ? 'E' : 'W';
		return l1 < l2 ? 'S' : 'N';
	}
}