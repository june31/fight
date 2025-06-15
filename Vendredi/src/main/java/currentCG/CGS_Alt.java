package currentCG;

import java.util.Arrays;

import tools.collections.multi.Lsi;
import tools.collections.pos.Lp;
import tools.collections.string.LHSs;
import tools.collections.string.Ls;
import tools.collections.string.Ss;
import tools.scanner.Scan;
import tools.scanner.list.ScanLs;
import tools.strings.S;
import tools.strings.SB;
import tools.tables.Table;
import tools.tuple.Pos;
import tools.tuple.SI;

public class CGS_Alt {
	public static void main(String[] args) {
		int[][] map = Scan.readMapSpaced(4);
		Ls l = ScanLs.readLines();
		Lsi lsi = new Lsi();
		SB sb1 = new SB("===Each Player's Score===\n");
		SB sb2 = new SB("===Each Scoring Player's Scoring Words===\n");
		Ss all = new Ss();
		Ss dups = new Ss();
		for (String line : l) {
			for (String m: new Ss(line.split(" writes: ")[1].split(" "))) {
				if (all.contains(m)) dups.add(m);
				else all.add(m);
			}
		}
		for (String line : l) {
			var tk = line.split(" writes: ");
			int score = 0;
			SB sb3 = new SB(); 
			sb3.a(tk[0] + "\n");
			for (String m : new LHSs(tk[1].split(" "))) {
				if (dups.contains(m)) continue; // Skip duplicates
				if (rec(map, m.toCharArray(), -1, -1)) {
					int s = switch (m.length()) {
					case 1 -> 0;
					case 2 -> 0;
					case 3 -> 1;
					case 4 -> 1;
					case 5 -> 2;
					case 6 -> 3;
					case 7 -> 5;
					default -> 11;
					};
					score += s;
					sb3.append(s + " " + m + "\n");
				}
			}
			if (score > 0) sb2.append(sb3);
			sb1.append(tk[0] + " " + score + "\n");
			lsi.add(new SI(tk[0], score));
		}
		S.p(lsi.max().s + " is the winner!\n\n" + sb1 + "\n" + sb2);
	}

	private static boolean rec(int[][] map, char[] cs, int l, int c) {
		if (cs.length == 0) return true;
		Lp lp = new Lp();
		for (Pos pos: Table.findAll(map, cs[0])) { 
			if (l != -1) {
				if (Math.abs(pos.l - l) > 1 || Math.abs(pos.c - c) > 1) continue;
			}
			lp.add(pos);
		}
		for (Pos p: lp) {
			var m2 = Table.copy(map);
			m2[p.l][p.c] = '?';
			var cs2 = Arrays.copyOfRange(cs, 1, cs.length);
			if (rec(m2, cs2, p.l, p.c)) return true;
		}
		return false;
	}
}
