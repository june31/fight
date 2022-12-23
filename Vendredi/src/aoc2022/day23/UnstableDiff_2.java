package aoc2022.day23;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tables.Table;

public class UnstableDiff_2 {
	
	private static int[][] map;
	
	private static final int[][] scanX = { { -1, 0, 1 }, { -1, 0, 1 }, { -1, -1, -1 }, { 1, 1, 1 } }; 
	private static final int[][] scanY = { { -1, -1, -1 }, { 1, 1, 1 }, { -1, 0, 1 }, { -1, 0, 1 } }; 
	private static final int[] targetX = { 0, 0, -1, 1 }; 
	private static final int[] targetY = { -1, 1, 0, 0 }; 
	private static final int[] surroundX = { 0, 1, 1, 1, 0, -1, -1, -1 }; 
	private static final int[] surroundY = { -1, -1, 0, 1, 1, 1, 0, -1 }; 

	private static final int[] elfX = new int[10000]; 
	private static final int[] elfY = new int[10000];
	private static final int[] consiX = new int[10000]; 
	private static final int[] consiY = new int[10000];
	private static final int[] cleanX = new int[10000]; 
	private static final int[] cleanY = new int[10000];
	private static int elfN;
	
	private static int minX = Integer.MAX_VALUE;
	private static int minY = Integer.MAX_VALUE;
	private static int maxX = 0;
	private static int maxY = 0;

	private static final int PAD = 1000;

	public static void main(String[] args) throws IOException {
		List<int[]> rows = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {
			String line;
			int idLine = 0;
			while ((line = reader.readLine()) != null) {
				int[] lInfo = Table.toIntArray(line.getBytes());
				for (int i = 0; i < lInfo.length; i++) {
					if (lInfo[i] == '#') {
						elfX[elfN] = i + PAD;
						elfY[elfN] = idLine + PAD;
						elfN++;
					}
				}
				rows.add(lInfo);
				idLine++;
			}
		}
		map = Table.wall(Table.convert(rows), PAD, '.');
		
		int turn = 0;
		boolean moved = true;
		while (moved) {
			moved = false;
			for (int e = 0; e < elfN; e++) {
				int x = elfX[e];
				int y = elfY[e];
				consiX[e] = x;
				consiY[e] = y;
				boolean surrounded = false;
				for (int s = 0; s < 8; s++) {
					if (map[y + surroundY[s]][x + surroundX[s]] == '#') {
						surrounded = true;
						break;
					}
				}
				if (surrounded) {
					CONSI: for (int i = 0; i < 4; i++) {
						int id = (turn + i) & 3;
						if (map[y + targetY[id]][x + targetX[id]] != '.') continue;
						int[] dirX = scanX[id];
						int[] dirY = scanY[id];
						for (int j = 0; j < 3; j++) {
							if (map[y + dirY[j]][x + dirX[j]] == '#') continue CONSI;
						}
						consiX[e] = x + targetX[id];
						consiY[e] = y + targetY[id];
						break;
					}
				}
			}
			
			for (int e = 0; e < elfN; e++) {
				cleanX[e] = consiX[e];
				cleanY[e] = consiY[e];
			}
			for (int e = 0; e < elfN; e++) {
				int v = map[consiY[e]][consiX[e]];
				if (v <= 0) {
					consiX[e] = elfX[e];
					consiY[e] = elfY[e];
					consiX[-v] = elfX[-v];
					consiY[-v] = elfY[-v];
				} else {
					map[consiY[e]][consiX[e]] = -e;
				}
			}
			for (int e = 0; e < elfN; e++) {
				map[elfY[e]][elfX[e]] = '.';
				map[cleanY[e]][cleanX[e]] = '.';
				map[consiY[e]][consiX[e]] = '#';
				if (elfX[e] != consiX[e] || elfY[e] != consiY[e]) moved = true;
				elfX[e] = consiX[e];
				elfY[e] = consiY[e];
				if (consiX[e] < minX) minX = consiX[e];
				if (consiY[e] < minY) minY = consiY[e];
				if (consiX[e] > maxX) maxX = consiX[e];
				if (consiY[e] > maxY) maxY = consiY[e];
			}
			turn++;
			//System.out.println(turn + 1);
			//showMap();
		}
		
		//System.out.println("elfs:" + elfN + " minX:" + minX + " maxX:" + maxX + " minY:" + minY + " maxY:" + maxY);
		//int mul = (map.length - 2) * (map[0].length - 2);
		//System.out.println("map:" + (map.length - 2) + "*" + (map[0].length - 2) + "=" + mul);
		//System.out.println("map-elfs=" + (mul - elfN));
		//System.out.println((maxX - minX + 1) * (maxY - minY + 1) - elfN);
		System.out.println(turn);
	}
	
	static void showMap() {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < map.length - 1; i++) {
			for (int j = 1; j < map[0].length - 1; j++) {
				sb.append((char) map[i][j]);
			}
			sb.append('\n');
		}
		System.out.println(sb + "\n");
	}
}
