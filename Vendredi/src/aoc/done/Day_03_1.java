package aoc.done;

import tools.bfs.BFS2DExt;
import tools.bfs.util.BFS2DHelper;
import tools.collections.int32.L;
import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

public class Day_03_1 {
	
	public static void main(String[] args) {
		String[] lines = Scan.readRawLines();
		for (int i = 0; i < lines.length; i++) {
			lines[i] = lines[i].replace('-', '%');
		}
		int ln = lines.length;
		int[][] map = new int[ln][lines[0].length()];
		for (int i = 0; i < ln; i++) {
			byte[] bytes = lines[i].getBytes();
			for (int j = 0; j < bytes.length; j++) map[i][j] = bytes[j];
		}
		int sumAll = 0;
		for (int i = 0; i < ln; i++) sumAll += new L(lines[i]).sum(); 
			
		Pos[] ps = Table.findAll(map, v -> v != '.' && (v < '0' || v > '9'));
		for (Pos p: ps) {
			BFS2DExt bfs1 = new BFS2DExt(map);
			bfs1.moves = BFS2DHelper.dir8(bfs1);
			bfs1.diffuse(p,
					() -> {
						if (bfs1.turn == 2) return false;
						if (bfs1.v2 >= '0' && bfs1.v2 <= '9') map[bfs1.l2][bfs1.c2] = 'X';
						return true;
					},
					() -> bfs1.turn > 1);
		}
		
		int bs = 0;
		for (int i = 0; i < ln; i++) {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < lines[i].length(); j++) sb.append((char) map[i][j]);
			for (String s: sb.toString().split("[^-\\dX]+")) {
				System.out.println(s);
				try { bs += Integer.parseInt(s); } catch (NumberFormatException e) {}
			}
		}
		
		System.out.println(sumAll - bs);
	}
}
