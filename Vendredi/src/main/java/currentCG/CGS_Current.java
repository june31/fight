package currentCG;

import tools.bfs.BFS;
import tools.collections.pos.Sp;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;
import tools.tuple.Pos;

public class CGS_Current {
	public static void main(String[] args) {
		int max = 0;
		int island = 1;
		var map = Scan.readMap1();
		BFS bfs = new BFS(map);
		int i = 0;
		for (int l = 0; l < map.length; l++) {
			for (int c = 0; c < map[0].length; c++) {
				if (map[l][c] != '#') continue;
				Sp sp = new Sp();
				i++;
				bfs.setWall('~');
				bfs.diffuse(l, c);
				for (Pos p: bfs.visited) {
					if (Table.get(map, p.up()) == '~') sp.add(p.up());
					if (Table.get(map, p.down()) == '~') sp.add(p.down());
					if (Table.get(map, p.left()) == '~') sp.add(p.left());
					if (Table.get(map, p.right()) == '~') sp.add(p.right());;
				}
				for (Pos p: bfs.visited) map[p.l][p.c] = '~';
				if (sp.size() > max) {
					island = i;
					max = sp.size();
				}
			}
		}
		S.o(island, max);
	}
}