package currentCG;

import tools.bfs.BFS2DExt;
import tools.bfs.util.BFS2DHelper;
import tools.collections.map.Mso;
import tools.collections.multi.Lsi;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;
import tools.tuple.Pos;
import tools.tuple.SI;

public class CGS_Current {
	private static Pos end, start;
	
	public static void main(String[] args) throws Exception {
		var map = Scan.readMapCL();
		Table.forEach(map, (l, c, v) -> {
			Pos p = new Pos(l, c);
			if (v == '<') if (c == 0) end = p; else start = p;
			if (v == '>') if (c == 0) start = p; else end = p;
			if (v == '^') if (l == 0) end = p; else start = p;
			if (v == 'v') if (c == 0) start = p; else end = p;
		});
		Lsi res = new Lsi();
		Mso<BFS2DExt> bfsMap = new Mso<>();

		// Warrior
		String nameW = "WARRIOR";
		var bfsW = new BFS2DExt(map).wall('#').end(end);
		bfsW.diffuse(start);
		res.addSI(nameW, bfsW.found ? (bfsW.turn+1) * 2 : Integer.MAX_VALUE);
		bfsMap.put(nameW, bfsW);

		// Dwarf
		String nameD = "DWARF";
		var walled = Table.wall(map, '#');
		var bfsD = new BFS2DExt(map).end(end);
		bfsD.move(() -> bfsD.v2 != '#' || walled[bfsD.l2*2 - bfsD.l1 + 1][bfsD.c2*2 - bfsD.c1 + 1] != '#');
		bfsD.diffuse(start);
		res.addSI(nameD, bfsD.found ? (bfsD.turn+1) * 3 : Integer.MAX_VALUE);
		bfsMap.put(nameD, bfsD);
		
		// Elf
		String nameE = "ELF";
		var bfsE = new BFS2DExt(map).wall('#').end(end);
		bfsE.setMoves(BFS2DHelper.dir8());
		bfsE.diffuse(start);
		res.addSI(nameE, bfsE.found ? (bfsE.turn+1) * 4 : Integer.MAX_VALUE);
		bfsMap.put(nameE, bfsE);
		
		// Mage
		String nameM = "MAGE";
		var bfsM = new BFS2DExt(map).end(end);
		bfsM.setMoves(BFS2DHelper.anyToWall('#'));
		bfsM.diffuse(start);
		res.addSI(nameM, bfsM.found ? (bfsM.turn+1) * 5 : Integer.MAX_VALUE);
		bfsMap.put(nameM, bfsM);
		
		// Summary
		SI winner = res.min();
		S.o(winner.s, winner.i);
		var lp = bfsMap.get(winner.s).shortestPath();
		for (int i = 1; i < lp.size() - 1; i++) {
			Pos p1 = lp.get(i);
			Pos p2 = lp.get(i + 1);
			if (p2.c > p1.c && p2.l == p1.l) map[p1.l][p1.c] = '>';
			else if (p2.c < p1.c && p2.l == p1.l) map[p1.l][p1.c] = '<';
			else if (p2.l > p1.l && p2.c == p1.c) map[p1.l][p1.c] = 'v';
			else if (p2.l < p1.l && p2.c == p1.c) map[p1.l][p1.c] = '^';
			else map[p1.l][p1.c] = 'o';
		}
		Table.printMap(map);
	}
}
