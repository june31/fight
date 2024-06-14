package summer24;

import tools.collections.int32.L;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tuple.II;

public class CGP_Olymbits {
	public static void main(String args[]) {
		int id = Scan.readInt();
		int nbGames = Scan.readInt();
		S.e(id, nbGames);

		while (true) {
			int[][] scores = new int[3][];
			for (int i = 0; i < 3; i++) {
				scores[i] = Scan.readIntLine();
				S.e("Scores: " + scores[i][0] + " " + scores[i][1] + " " + scores[i][2] + " " + scores[i][3]);
			}
			String[] gpu = new String[nbGames];
			int[] r0 = new int[nbGames];
			int[] r1 = new int[nbGames];
			int[] r2 = new int[nbGames];
			int[] r3 = new int[nbGames];
			int[] r4 = new int[nbGames];
			int[] r5 = new int[nbGames];
			int[] r6 = new int[nbGames];

			boolean[] reset = new boolean[4];
			int right = 0;
			int up1 = 0;
			int up2 = 0;
			int left = 0;
			int active = 0;
			for (int i = 0; i < 4; i++) {
				gpu[i] = Scan.readString() + "....";
				r0[i] = Scan.readInt();
				r1[i] = Scan.readInt();
				r2[i] = Scan.readInt();
				r3[i] = Scan.readInt();
				r4[i] = Scan.readInt();
				r5[i] = Scan.readInt();
				r6[i] = Scan.readInt();
				if (gpu[i].charAt(0) == 'G') {
					scores[id][i] = 100;
					reset[i] = true;
					continue;
				}
				active++;
				int[] r = id == 0 ? r0 : id == 1 ? r1 : r2;		
				String s = gpu[i].substring(r[i]);
				S.e(s + " " + s.indexOf('#'));
				if (s.startsWith("....")) {
					right++;
					S.e(i + " RIGHT");
				} else if (s.startsWith("...")) {
					up1++;
					S.e(i + " UP1");
				} else if (s.startsWith("..")) {
					left++;
					S.e(i + " LEFT");
				} else if (s.startsWith(".")) {
					up2++;
					S.e(i + " UP2");
				} else {
					S.e(i + " OUPS");
					active--;
				}
			}

			if (active == right) {
				S.o("RIGHT");
			} else if (active == right + up1 + up2) {
				S.o("UP");
			} else if (active == right + up1 + left && left > up1) {
				S.o("LEFT");
			} else {
				S.o(left > up2 && left > up1 ? "LEFT" : "UP");
			}
		}
	}
}
