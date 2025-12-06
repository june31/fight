package aoc.done;

import tools.collections.character.Lc;
import tools.collections.int64.Ll;
import tools.collections.multi.int64.LLl;
import tools.scanner.Scan;
import tools.strings.S;

public class Day06_1 {
	public static void main(String[] args) {
		long z = 0;
		var in = Scan.readRaw();
		int L = in.length - 1;
		int C = in[0].split(" +").length;
		Lc ops = new Lc(in[L], " +");
		LLl vals = new LLl();
		for (int i = 0; i < L; i++) vals.add(new Ll(in[i]));
		for (int i = 0; i < C; i++) {
			char op = ops.get(i);
			long v = vals.get(0).get(i);
			for (int j = 1; j < L; j++) {
				long w = vals.get(j).get(i);
				if (op == '+') v += w;
				else if (op == '*') v *= w;
			}
			z += v;
		}
		S.o(z);
	}
}
