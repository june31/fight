package aoc2022.day22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tables.Table;

public class Day22_2 {
	
	private static int[][] map;
	private static List<Integer> dists = new ArrayList<>();
	private static List<Character> turns = new ArrayList<>();
	private static int l = 0;
	private static int c = 0;
	private static int dl = 0;
	private static int dc = 1;
	private static int dir = 0;
	private static int size;

	public static void main(String[] args) throws IOException {
		List<int[]> mapList = new ArrayList<>();
		int max = 0;
		String filename = "input2.txt";
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String rLine;
			while (!(rLine = reader.readLine()).isBlank()) if (rLine.length() > max) max = rLine.length(); 
		}
		
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String rLine;
			int pix = 0;
			while ((rLine = reader.readLine()) != null) {
				if (rLine.isBlank()) break;
				int[] row = new int[max];
				for (int i = 0; i < rLine.length(); i++) {
					row[i] = rLine.charAt(i);
					if (row[i] != ' ') pix++;
				}
				for (int i = rLine.length(); i < max; i++) row[i] = ' ';
				mapList.add(row);
			}
			size = (int) Math.sqrt(pix/6);
			map = Table.convert(mapList);
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
		int l2 = l;
		int c2 = c;
		do {
			l2 = Math.floorMod(l2 + dl, map.length);
			c2 = Math.floorMod(c2 + dc, map[0].length);
		} while (map[l2][c2] == ' ');
		if (map[l2][c2] == '#') return;
		l = l2;
		c = c2;
	}

	private static void turnLeft() {
		if (dir == 0) {
			dir = 3;
			dl = -1;
			dc = 0;
		} else if (dir == 1) {
			dir = 0;
			dl = 0;
			dc = 1;
		} else if (dir == 2) {
			dir = 1;
			dl = 1;
			dc = 0;
		} else {
			dir = 2;
			dl = 0;
			dc = -1;
		}
	}
	
	private static void turnRight() {
		if (dir == 0) {
			dir = 1;
			dl = 1;
			dc = 0;
		} else if (dir == 1) {
			dir = 2;
			dl = 0;
			dc = -1;
		} else if (dir == 2) {
			dir = 3;
			dl = -1;
			dc = 0;
		} else {
			dir = 0;
			dl = 0;
			dc = 1;
		}
	}
}
