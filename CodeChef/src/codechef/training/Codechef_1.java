package codechef.training;

import java.util.ArrayList;
import java.util.List;

import tools.enumeration.permutations.MixCyclicPermutations;
import tools.enumeration.permutations.MixPermutations;
import tools.math.Num;
import tools.output.Out;
import tools.scanner.Scan;

class Codechef_1 {

	static int n;

	public static void main2(String[] args) {
		n = 6;
		var it = new MixCyclicPermutations<>(List.of(1, 2, 3, 4, 5, 6));
		for (var l : it) {
			Out.println(l);
			int[] t = calc(l.stream().mapToInt(Integer::intValue).toArray());
			for (int i = 0; i < n - 1; i++) Out.buf(t[i] + " ");
			Out.bufln(t[n - 1]);

			Out.flush();
		}
	}

	public static void main(String[] args) {
		int T = Scan.readInt();
		for (int turn = 0; turn < T; turn++) {
			n = Scan.readInt();
			int[] rt = Scan.readIntArray(n);
			int[] t = calc(rt);
			for (int i = 0; i < n - 1; i++) Out.buf(t[i] + " ");
			Out.bufln(t[n - 1]);
		}
		Out.flush();
	}

	public static int[] calc(int[] rt) {
		var choices = new ArrayList<int[]>();

		// A. 1 does not move.
		int[] ta = roll(rt); 
		finalSwap(ta);
		finalSwap(ta);
		choices.add(ta);

		// B. 1 swaps before 2.
		int[] tb = new int[n];
		System.arraycopy(rt, 0, tb, 0, n);
		int pos = Math.floorMod(get(tb, 2) - 1, n);
		swap(tb, pos, 1);
		tb = roll(tb);
		finalSwap(tb);
		choices.add(tb);

		// C. 1/2 swap before 3.
		int[] tc = new int[n];
		System.arraycopy(rt, 0, tc, 0, n);
		pos = Math.floorMod(get(tc, 3) - 2, n);
		swap(tc, pos, 1);
		pos = Math.floorMod(get(tc, 3) - 1, n);
		swap(tc, pos, 2);
		tc = roll(tc);
		choices.add(tc);

		// D. 1/x swap before 3.
		int[] td = new int[n];
		System.arraycopy(rt, 0, td, 0, n);
		pos = Math.floorMod(get(td, 3) - 2, n);
		swap(td, pos, 1);
		td = roll(td);
		finalSwap(td);
		choices.add(td);

		// E. 1-x, 2-3
		int[] te = new int[n];
		System.arraycopy(rt, 0, te, 0, n);
		pos = get(te, 3);
		swap(te, pos, 2);
		pos = Math.floorMod(get(te, 2) - 1, n);
		swap(te, pos, 1);
		te = roll(te);
		choices.add(te);

		// F. 1 swaps with 2.
		int[] tf = new int[n];
		System.arraycopy(rt, 0, tf, 0, n);
		pos = get(tf, 1);
		swap(tf, pos, 2);
		tf = roll(tf);
		finalSwap(tf);
		choices.add(tf);

		// G. 1 swaps with 3.
		int[] tg = new int[n];
		System.arraycopy(rt, 0, tg, 0, n);
		pos = get(tg, 1);
		swap(tg, pos, 3);
		tg = roll(tg);
		finalSwap(tg);
		choices.add(tg);
		
		choices.sort((t1, t2) -> {
			for (int i = 0; i < n; i++) {
				int c = t1[i] - t2[i];
				if (c != 0) return c;
			}
			return 0;
		});
		return choices.get(0);
	}

	private static void swap(int[] t, int pos, int i) {
		int pos2 = get(t, i);
		int tmp = t[pos2];
		t[pos2] = t[pos]; 
		t[pos] = tmp;
	}

	private static int get(int[] t, int x) {
		for (int i = 0; i < n; i++) {
			if (t[i] == x) return i;
		}
		return 0;
	}

	private static void finalSwap(int[] t) {
		int i = 0;
		while (t[i] == ++i) if (i == n) return;
		int id = get(t, i);
		t[id] = t[i-1];
		t[i-1] = i;
	}

	private static int[] roll(int[] t) {
		int[] t2 = new int[n];
		int i1 = 0;
		while (t[i1] != 1) i1++;
		int id = 0;
		for (int i = i1; i < n; i++) t2[id++] = t[i];
		for (int i = 0; i < i1; i++) t2[id++] = t[i];
		return t2;
	}
}
