package aoc2022.day24;

import tools.math.Num;
import tools.scanner.Scan;
import tools.tables.Table;

public class BlizzardBasin_2 {

	private static final int RIGHT = 1;
	private static final int DOWN = 2;
	private static final int LEFT = 4;
	private static final int UP = 8;
	private static final int WALL = 16;
	private static final int REACHABLE = 32;
	
	private static int[][] map;
	private static int[][][] forecast;
	private static int nx;
	private static int ny;
	private static int period;
	private static int min;
	private static int n;
	
	public static void main(String[] args) {
		Scan.open("input2.txt");
		map = Table.toMap(Scan.readRawStrings());
		ny = map.length - 2;
		nx = map[0].length - 2;
		map[0][1] = '%';
		map[ny + 1][nx] = '%';
		period = Num.lcm(nx, ny);
		int limit = 100 + 10 * nx + 10 * ny;
		System.out.println(ny + " lines, " + nx + " cols, period:" + period);
		
		forecast = new int[period][ny + 2][nx + 2];
		for (int i = 0; i < ny + 2; i++) {
			for (int j = 0; j < nx + 2; j++) {
				forecast[0][i][j] = convert(map[i][j]);
			}
		}
		for (int layer = 1; layer < period; layer++) {
			for (int i = 0; i < ny + 2; i++) {
				for (int j = 0; j < nx + 2; j++) {
					int f = forecast[layer-1][i][j];
					if (f == WALL) forecast[layer][i][j] = WALL;
					if ((f & RIGHT) != 0) forecast[layer][i][px(j)] |= RIGHT;
					if ((f & DOWN) != 0) forecast[layer][py(i)][j] |= DOWN;
					if ((f & LEFT) != 0) forecast[layer][i][mx(j)] |= LEFT;
					if ((f & UP) != 0) forecast[layer][my(i)][j] |= UP;
				}
			}
		}


		// Trip 1
		min = limit;
		for (int start = 0; start < period; start++) {
			n = (start + 1) % period;
			if (forecast[n][1][1] != 0) continue;
			clean(n);
			forecast[n][1][1] = REACHABLE;
			int steps = start + 1;
			int delta = reach(nx, ny);
			steps += delta;
			steps++; // exit
			if (min > steps) min = steps;
		}
		int trip1 = min;
		
		// Trip 2
		min = limit;
		for (int start = 0; start < period; start++) {
			n = (start + trip1 + 1) % period;
			if (forecast[n][ny][nx] != 0) continue;
			clean(n);
			forecast[n][ny][nx] = REACHABLE;
			int steps = start + trip1 + 1;
			int delta = reach(1, 1);
			steps += delta;
			steps++; // exit
			if (min > steps) min = steps;
		}
		int trip12 = min;
		
		// Trip 3
		min = limit;
		for (int start = 0; start < period; start++) {
			n = (start + trip12 + 1) % period;
			if (forecast[n][1][1] != 0) continue;
			clean(n);
			forecast[n][1][1] = REACHABLE;
			int steps = start + trip12 + 1;
			int delta = reach(nx, ny);
			steps += delta;
			steps++; // exit
			if (min > steps) min = steps;
		}
		System.out.println(min);
	}
	
	private static int reach(int tx, int ty) {
		int steps = 0;
		while (forecast[n][ty][tx] != REACHABLE) {
			steps++;
			int n2 = (n + 1) % period;
			clean(n2);
			for (int i = 1; i < ny + 1; i++) {
				for (int j = 1; j < nx + 1; j++) {
					if (forecast[n][i][j] == REACHABLE) {
						if (forecast[n2][i][j] == 0) forecast[n2][i][j] = REACHABLE;
						if (forecast[n2][i][j+1] == 0) forecast[n2][i][j+1] = REACHABLE;
						if (forecast[n2][i+1][j] == 0) forecast[n2][i+1][j] = REACHABLE;
						if (forecast[n2][i][j-1] == 0) forecast[n2][i][j-1] = REACHABLE;
						if (forecast[n2][i-1][j] == 0) forecast[n2][i-1][j] = REACHABLE;
					}
				}
			}
			//showForecast(n);
			n = n2;
			if (steps == min) return steps;
		}
		return steps;
	}

	private static void clean(int n) {
		for (int i = 1; i < ny + 1; i++) {
			for (int j = 1; j < nx + 1; j++) {
				forecast[n][i][j] &= ~REACHABLE;
			}
		}
	}

	static void showForecast(int layer) {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= ny; i++) {
			for (int j = 1; j <= nx; j++) {
				char c = getChar(forecast[layer][i][j]);
				sb.append(c);
			}
			sb.append('\n');
		}
		System.out.println(sb);
	}

	private static char getChar(int i) {
		int n = Integer.bitCount(i & 15);
		if (n > 1) return (char) ('0' + n);
		if (i == 0) return '.';
		if (i == RIGHT) return '>';
		if (i == DOWN) return 'v';
		if (i == LEFT) return '<';
		if (i == UP) return '^';
		if (i == REACHABLE) return 'R';
		
		System.err.println("Invalid value:" + i);
		return '?';
	}

	private static int px(int x) { return 1 + Math.floorMod(x, nx); }
	private static int py(int y) { return 1 + Math.floorMod(y, ny); }
	private static int mx(int x) { return 1 + Math.floorMod(x-2, nx); }
	private static int my(int y) { return 1 + Math.floorMod(y-2, ny); }

	private static int convert(int c) {
		if (c == '.') return 0;
		if (c == '>') return RIGHT;
		if (c == 'v') return DOWN;
		if (c == '<') return LEFT;
		if (c == '^') return UP;
		return WALL;
	}
}
