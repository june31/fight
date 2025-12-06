package aoc.done;

import tools.collections.int32.L;
import tools.collections.int64.Ll;
import tools.collections.multi.int64.LLl;
import tools.scanner.Scan;
import tools.strings.S;

public class Day06_2 {
	public static void main(String[] args) {
		long z = 0;
		var in = Scan.readMapRaw();
		int L = in.length - 1;
		L ops = new L();
		for (int c: in[L]) if (c > ' ') ops.add(c);
		int C = ops.size();
		
		LLl vals = new LLl();
		Ll vs = new Ll();
		for (int i = 0; i < in[0].length; i++) {
			long l = 0;
			boolean found = false;
			for (int j = 0; j < L; j++) {
				int c = in[j][i];
				if (c > ' ') {
					l = l * 10 + (c - '0');
					found = true;
				}
			}
			if (found) {
				vs.add(l);
				l = 0;
			} else {
				vals.add(vs);
				vs = new Ll();
			}
		}
		vals.add(vs);
		
		for (int i = 0; i < C; i++) {
			int op = ops.get(i);
			long v = vals.get(i).get(0);
			for (int j = 1; j < vals.get(i).size(); j++) {
				long w = vals.get(i).get(j);
				if (op == '+') v += w;
				else if (op == '*') v *= w;
			}
			z += v;
		}
		S.o(z);
	}
}
