package aoc24;

import tools.bfs.BFS;
import tools.collections.pos.Lp;
import tools.scanner.list.ScanLp;
import tools.strings.S;

public class Day18 {
	public static void main(String[] args) {
		Lp l = ScanLp.readRawCL();
		var map = new int[71][71];
		BFS b = new BFS(map).setWall('#').setEnd(70, 70);
		for (int i = 0; i == 0 || b.found; i++) {
			if (i == 1024) S.o(b.diffuse(0, 0).turn);
			map[l.get(i).l][l.get(i).c] = '#';
			if (!b.diffuse(0, 0).found) S.o(l.get(i).c + "," + l.get(i).l);
		}
	}
}
