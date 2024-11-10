package currentCG;

import tools.F;
import tools.collections.int32.L;
import tools.collections.multi.LAi;
import tools.collections.string.Ls;
import tools.mapper.MapLs;
import tools.scanner.Scan;
import tools.scanner.list.ScanLs;
import tools.strings.S;

public class CGS_Current {
	private static int colors;
	private static int columns;
	private static int lines;
	private static LAi lPositions;
	private static L lCorrect;
	private static L lMisplaced;

	public static void main(String[] args) throws Exception {
		colors = Scan.readInt();
		columns = Scan.readInt();
		lines = Scan.readInt();
		Ls rawText = ScanLs.readLines(lines);
		lPositions = LAi.fromLsDigits(rawText.column(0));
		lCorrect = MapLs.toL(rawText.column(1));
		lMisplaced = MapLs.toL(rawText.column(2));
		recurseOnExact(new L(), lCorrect);
	}

	private static void recurseOnExact(L history, L lCorrect) {
		int depth = history.size();
		if (depth == columns) {
			if (validate(history)) S.o(history.join(""));
			return;
		}
		COLOR: for (int color = 0; color < colors; color++) {
			L lNewCorrect = lCorrect.copy();
			for (int line = 0; line < lines; line++) {
				int c = lPositions.get(line)[depth];
				if (c == color) {
					int correct = lCorrect.get(line);
					if (correct == 0) continue COLOR;
					lNewCorrect.set(line, correct - 1);
				}
				else if (lCorrect.get(line) >= columns - depth) continue COLOR;
			}
			L newHistory = history.copy();
			newHistory.add(color);
			recurseOnExact(newHistory, lNewCorrect);
		}
	}

	private static boolean validate(L history) {
		for (int line = 0; line < lines; line++) {
			L solution = new L(history);
			int[] pos = lPositions.get(line).clone();
			for (int i = 0; i < columns; i++)
				if (pos[i] == solution.get(i)) {
					pos[i] = colors;
					solution.set(i, colors + 1);
				}
			int[] stats = F.stats0(colors + 2, pos);
			int misplaced = lMisplaced.get(line);
			for (int s: solution)
				if (stats[s] > 0) {
					misplaced--;
					stats[s]--;
				}
			if (misplaced != 0) return false;
		}
		return true;
	}
}
