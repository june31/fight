package currentCG;

import java.util.Comparator;

import tools.collections.map.Msi;
import tools.collections.string.Ls;
import tools.math.Dist;
import tools.scanner.Scan;
import tools.scanner.list.ScanLs;
import tools.scanner.list.ScanLsp;
import tools.strings.S;
import tools.tuple.Pos;
import tools.tuple.SI;
import tools.tuple.SP;

public class CGS_Alt {
	public static void main(String[] args) {
		double s = Scan.readInt() / 2.0;
		Ls players = ScanLs.readLines();
		Msi scores = new Msi();
		for (SP draw: ScanLsp.read()) scores.inc(draw.s, score(s, draw.p));
		scores.toLsi().sorted(Comparator.comparingInt((SI si) -> -si.i).thenComparingInt(si -> players.indexOf(si.s))
		).forEach((si) -> S.o(si.s + " " + si.i));
	}

	private static int score(double s, Pos p) {
		if (Dist.manh(0, 0, p.l, p.c) <= s) return 15;
		if (Dist.calc(0, 0, p.l, p.c) <= s) return 10;
		if (Dist.chebyshev(0, 0, p.l, p.c) <= s) return 5;
		return 0;
	}
}
