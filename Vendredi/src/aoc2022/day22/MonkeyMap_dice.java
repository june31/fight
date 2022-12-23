package aoc2022.day22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MonkeyMap_dice {
	
	private static int[][] map;
	private static int[][] MAP;
	private static List<Integer> dists = new ArrayList<>();
	private static List<Character> turns = new ArrayList<>();
	private static int l = 0;
	private static int c = 0;
	private static int l2;
	private static int c2;
	private static int L = 0;
	private static int C = 0;
	private static int dl = 0;
	private static int dc = 1;
	private static int dl2;
	private static int dc2;
	private static int dir = 0;
	private static int dir2;
	private static int N;

	public static void main(String[] args) throws IOException {
		String filename = "input2.txt";
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			int pix = 0;
			String rLine;
			while (!(rLine = reader.readLine()).isBlank()) {
				for (int i = 0; i < rLine.length(); i++) if (rLine.charAt(i) != ' ') pix++;
			}
			N = (int) Math.sqrt(pix/6);
		}

		map = new int[N*4][N*4];
		MAP = new int[4][4];
		
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String rLine;
			int line = 0;
			while ((rLine = reader.readLine()) != null) {
				if (rLine.isBlank()) break;
				for (int i = 0; i < rLine.length(); i++) {
					map[line][i] = rLine.charAt(i);
					if (rLine.charAt(i) != ' ') MAP[line / N][i / N] = 1; 
				}
				for (int i = rLine.length(); i < 4*N; i++) map[line][i] = ' ';
				line++;
			}
			for (int i = line; i < 4*N; i++) for (int j = 0; j < 4*N; j++) map[i][j] = ' ';
			
			rLine = reader.readLine();
			for (String d : rLine.split("[A-Z]") ) dists.add(Integer.parseInt(d));
			for (String d : rLine.split("[0-9]+") ) if (!d.isBlank()) turns.add(d.charAt(0));
			turns.add('X');
			
			while (map[l][c] != '.') c++;
			
			for (int i = 0; i < dists.size(); i++) {
				int d = dists.get(i);
				for (int j = 0; j < d; j++) move();
				int t = turns.get(i);
				if (t == 'R') turnRight();
				else if (t == 'L') turnLeft();
				else System.out.println(1000 * (l+1) + 4 * (c+1) + dir);
			}
			
		}
	}

	private static void move() {
		if (dir == 0 && (dl != 0 || dc != 1)) fail("Wrong dir 0!");
		if (dir == 1 && (dl != 1 || dc != 0)) fail("Wrong dir 1!");
		if (dir == 2 && (dl != 0 || dc != -1)) fail("Wrong dir 2!");
		if (dir == 3 && (dl != -1 || dc != 0)) fail("Wrong dir 3!");
		l2 = Math.floorMod(l + dl, map.length);
		c2 = Math.floorMod(c + dc, map[0].length);
		dl2 = dl;
		dc2 = dc;
		dir2 = dir;
		L = l / N;
		C = c / N;
		if (map[l2][c2] == ' ') {
			char z = f(L, C).charAt(0);
			
			if (z == 'A' && dir == 3) teleport('F', 1);
			else if (z == 'A' && dir == 2) teleport('D', 2);

			else if (z == 'B' && dir == 3) teleport('F', 0);
			else if (z == 'B' && dir == 0) teleport('E', 2);
			else if (z == 'B' && dir == 1) teleport('C', 1);

			else if (z == 'C' && dir == 0) teleport('B', 3);
			else if (z == 'C' && dir == 2) teleport('D', 3);

			else if (z == 'D' && dir == 2) teleport('A', 2);
			else if (z == 'D' && dir == 3) teleport('C', 1);

			else if (z == 'E' && dir == 0) teleport('B', 2);
			else if (z == 'E' && dir == 1) teleport('F', 1);

			else if (z == 'F' && dir == 2) teleport('A', 3);
			else if (z == 'F' && dir == 0) teleport('E', 3);
			else if (z == 'F' && dir == 1) teleport('B', 0);
			else fail("Oups! [" + L + ", " + C + "] dir:" + dir + ".\nTarget coord: [" + l2 + ", " + c2 + "].");
		}
		if (map[l2][c2] == '#') return;
		l = l2;
		c = c2;
		dl = dl2;
		dc = dc2;
		dir = dir2;
	}

	private static void teleport(char w, int rot) {
		System.out.println("Teleport from " + f(L, C) + " to " + w + " with rot " + rot + ".");
		System.out.println("Old coord: [" + (l%N) + ", " + (c%N) + "] dir:" + dir);
		l2 %= N;
		c2 %= N;
		dir2 = (dir + rot) & 3;
		for (int i = 0; i < rot; i++) {
			int tmp = dl2;
			dl2 = dc2;
			dc2 = -tmp;
			
			tmp = l2;
			l2 = c2;
			c2 = N-tmp-1;
		}
		System.out.println("New coord: [" + l2 + ", " + c2 + "] dir:" + dir2);
		l2 += getL(w)*N;
		c2 += getC(w)*N;
		if (map[l2][c2] == '#') System.out.println("Failed teleport.");
		if (map[l2][c2] == ' ') fail("Teleport to void.");
	}

	private static int getL(char w) {
		if (w == 'A') return 0;
		if (w == 'B') return 0;
		if (w == 'C') return 1;
		if (w == 'D') return 2;
		if (w == 'E') return 2;
		if (w == 'F') return 3;
		fail("getL failed.");
		return 0;	
	}

	private static int getC(char w) {
		if (w == 'A') return 1;
		if (w == 'B') return 2;
		if (w == 'C') return 1;
		if (w == 'D') return 0;
		if (w == 'E') return 1;
		if (w == 'F') return 0;
		fail("getC failed.");
		return 0;	
	}

	private static void fail(String string) {
		System.out.println("\nError: " +string);
		System.exit(1);
	}

	private static void turnLeft() {
		dir = (dir - 1) & 3;
		int tmp = dl;
		dl = -dc;
		dc = tmp;
	}

	private static void turnRight() {
		dir = (dir + 1) & 3;
		int tmp = dl;
		dl = dc;
		dc = -tmp;
	}
	
	private static String f(int l, int c) {
		if (l == 0 && c == 1) return "A";
		if (l == 0 && c == 2) return "B";
		if (l == 1 && c == 1) return "C";
		if (l == 2 && c == 0) return "D";
		if (l == 2 && c == 1) return "E";
		if (l == 3 && c == 0) return "F";
		fail("f failed.");
		return "?";	
	}
}
