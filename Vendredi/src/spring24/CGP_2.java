package spring24;

import java.util.Scanner;

import tools.tables.Table;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class CGP_2 {

	public static void main(String args[]) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		if (in.hasNextLine()) {
			in.nextLine();
		}

		int[][] map = new int[n][n];
		Table.fill(map, '.');
		// game loop
		while (true) {
			String[] command = in.nextLine().split(" ");
			int id = Integer.parseInt(command[1]);
			if (command[0].equals("C")) 
				for (int i = 0; i < n; i++) map[i][id] = '#';
			else
				for (int i = 0; i < n; i++) map[id][i] = '.';
			Table.printMap(map);
		}
	}
}


