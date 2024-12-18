package aoc24.done;

import tools.bfs.BFS;
import tools.scanner.list.ScanLp;
import tools.strings.S;
import tools.tuple.Pos;

public class Day18 {
	public static void main(String[] args) {
		var l = ScanLp.readRawCL();
		var map = new int[71][71];
		for (int i = 0; i < 1024; i++) map[l.get(i).l][l.get(i).c] = '#';
		BFS b = new BFS(map).setWall('#').setEnd(70, 70);
		S.o(b.diffuse(0, 0).turn);
		Pos p = null;
		for (int i = 1024; b.found; i++) {
			p = l.get(i);
			map[p.l][p.c] = '#';
			b.diffuse(0, 0);
		}
		S.o(p.c + "," + p.l);
	}
}
