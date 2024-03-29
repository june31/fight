package aoc2022.day17;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tetris_1 {
	static final int[][][] shapes = new int[][][] {
		{
			{ 1, 1, 1, 1 }
		},
		{
			{ 0, 1, 0 },
			{ 1, 1, 1 },
			{ 0, 1, 0 }
		},
		{
			{ 1, 1, 1 }, // It is reversed!
			{ 0, 0, 1 },
			{ 0, 0, 1 }
		},
		{
			{ 1 },
			{ 1 },
			{ 1 },
			{ 1 }
		},
		{
			{ 1, 1 },
			{ 1, 1 }
		},
	};

	static final int[] width = new int[] { 4, 3, 3, 1, 2 }; 
	static final int[] heigth = new int[] { 1, 3, 3, 4, 2 }; 

	static final int[] chimney = new int[8192 * 8];
	static { for (int i=0; i<7; i++) chimney[i] = 2; };


	public static void main(String[] args) throws IOException {
		byte[] dirs;
		try (BufferedReader reader = new BufferedReader(new FileReader("input2.txt"))) {
			String line = reader.readLine();
			dirs = line.getBytes();
		}
		int dirN = dirs.length;
		int shapeIndex = 0;

		int minH = 1;
		int x;
		int wind = 0;

		for (int turn = 0; turn < 2022; turn++) {
			x = 2;
			int y = minH + 3;
			while (true) { 
				if (dirs[wind % dirN] != '<' && dirs[wind % dirN] != '>') System.out.println("z"); 
				int dx = dirs[wind % dirN] == '>' ? 1 : -1;
				wind++;
				if (x + dx == -1) dx = 0;
				if (x + dx + width[shapeIndex] > 7) dx = 0;
				if (dx != 0 && !windBlocked(shapeIndex, x, y, dx)) x += dx;
				if (blocked(shapeIndex, x, y)) break;
				y--;
			}
			print(shapeIndex, x, y);
			H: for (;; minH++) {
				for (int c = 0; c < 7; c++) if (chimney[minH * 8 + c] != 0) continue H;
				break;
			}
			shapeIndex++;
			shapeIndex %= 5;
			//drawBoard(minH);
		}
		System.out.println(minH - 1);
	}

	private static boolean blocked(int shapeIndex, int x, int y) {
		for (int l = 0; l < heigth[shapeIndex]; l++) {
			for (int c = 0; c < width[shapeIndex]; c++) {
				if (chimney[(l+y-1) * 8 + (c+x)] != 0 && shapes[shapeIndex][l][c] != 0) return true;
			}
		}
		return false;
	}
	
	private static boolean windBlocked(int shapeIndex, int x, int y, int dx) {
		for (int l = 0; l < heigth[shapeIndex]; l++) {
			for (int c = 0; c < width[shapeIndex]; c++) {
				if (chimney[(l+y) * 8 + (c+x+dx)] != 0 && shapes[shapeIndex][l][c] != 0) return true;
			}
		}
		return false;
	}

	private static void print(int shapeIndex, int x, int y) {
		for (int l = 0; l < heigth[shapeIndex]; l++) {
			for (int c = 0; c < width[shapeIndex]; c++) {
				chimney[(l+y) * 8 + (c+x)] |= shapes[shapeIndex][l][c];
			}
		}
	}

	static void drawBoard(int start) {
		StringBuilder sb = new StringBuilder();
		for (int l = start - 1; l >= 0; l--) {
			//sb.append('|');
			for (int c = 0; c < 7; c++) {
				int v = chimney[l * 8 + c];
				if (v == 0) sb.append('.');
				else if (v == 1) sb.append('#');
				else if (v == 2) sb.append('#');
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}
}
