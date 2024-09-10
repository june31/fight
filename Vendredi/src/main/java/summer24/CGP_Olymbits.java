package summer24;

import tools.scanner.Scan;

public class CGP_Olymbits {
	public static void main(String args[]) {
		Scan.setDebugMode(true);
		Common.myIndex = Scan.readInt();
		Scan.readInt(); // 4 games

		String[] gpu = new String[4];
		int[] r0 = new int[4];
		int[] r1 = new int[4];
		int[] r2 = new int[4];
		int[] r3 = new int[4];
		int[] r4 = new int[4];
		int[] r5 = new int[4];
		int[] r6 = new int[4];

		while (true) {
			Common.turn++;
			
			// Current player scores
			Common.scores[Common.myIndex] = Scan.readIntLine();
			Common.scores[(Common.myIndex + 2) % 3] = Scan.readIntLine();
			Common.scores[(Common.myIndex + 1) % 3] = Scan.readIntLine();
			
			for (int i = 0; i < 4; i++) {
				gpu[i] = Scan.readString();
				r0[i] = Scan.readInt();
				r1[i] = Scan.readInt();
				r2[i] = Scan.readInt();
				r3[i] = Scan.readInt();
				r4[i] = Scan.readInt();
				r5[i] = Scan.readInt();
				r6[i] = Scan.readInt();
				//S.e(gpu[i], r0[i], r1[i], r2[i], r3[i], r4[i], r5[i], r6[i]);
			}
			
			Hurdle.init(gpu[0], r0[0], r1[0], r2[0], r3[0], r4[0], r5[0]);
			Bow.init(gpu[1], r0[1], r1[1], r2[1], r3[1], r4[1], r5[1]);
			Roller.init(gpu[2], r0[2], r1[2], r2[2], r3[2], r4[2], r5[2], r6[2]);
			Dive.init(gpu[3], r0[3], r1[3], r2[3], r3[3], r4[3], r5[3]);
			
			int move = Algo.play();
			
			System.out.println(move == Common.L ? "LEFT" : move == Common.U ? "UP" : move == Common.R ? "RIGHT" : "DOWN");
			
			if (Scan.isEclipse()) break;
		}
	}
}
