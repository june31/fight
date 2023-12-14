package aoc.done;

import java.util.HashSet;
import java.util.Set;

import tools.scanner.Scan;
import tools.tables.Table;

public class Day_14_2 {
	
	private static int[][] map = Scan.readMap0();
	private static int L = map.length;
	private static int C = map[0].length;
	private static Set<String> known = new HashSet<>();

	public static void main(String[] args) {

		int a = 0;
		while (known.add(Table.toString(map))) {
			north();
			west();
			south();
			east();
			a++;
		}
		System.out.println(a);

		known.clear();
		int b = 0;
		while (known.add(Table.toString(map))) {
			north();
			west();
			south();
			east();
			b++;
		}
		System.out.println(a);
		
		for (int c = 0; c < (1_000_000_000-a) % b; c++) {
			north();
			west();
			south();
			east();
		}

		int s = 0;
		int id = 0;
		for (int l = L-1; l >= 0; l--) {
			id++;
			for (int c = 0; c < C; c++) if (map[l][c] == 'O') s += id;
		}

		System.out.println(s);
	}

	private static void north() {
		for (int l = 0; l < L; l++) {
			for (int c = 0; c < C; c++) {
				if (map[l][c] == 'O') {
					int x = l - 1;
					while (x >= 0 && map[x][c] == '.') {
						map[x][c] = 'O';
						map[x+1][c] = '.';
						x--;
					}
				}
			}
		}
	}
	
	private static void south() {
		for (int l = L-1; l >= 0; l--) {
			for (int c = 0; c < C; c++) {
				if (map[l][c] == 'O') {
					int x = l + 1;
					while (x < L && map[x][c] == '.') {
						map[x][c] = 'O';
						map[x-1][c] = '.';
						x++;
					}
				}
			}
		}
	}

	private static void west() {
		for (int c = 0; c < C; c++) {
			for (int l = 0; l < L; l++) {
				if (map[l][c] == 'O') {
					int x = c - 1;
					while (x >= 0 && map[l][x] == '.') {
						map[l][x] = 'O';
						map[l][x+1] = '.';
						x--;
					}
				}
			}
		}
	}
	
	private static void east() {
		for (int c = C-1; c >= 0; c--) {
			for (int l = 0; l < L; l++) {
				if (map[l][c] == 'O') {
					int x = c + 1;
					while (x < C && map[l][x] == '.') {
						map[l][x] = 'O';
						map[l][x-1] = '.';
						x++;
					}
				}
			}
		}
	}

}
