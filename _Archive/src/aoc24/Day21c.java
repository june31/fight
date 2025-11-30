package aoc24;

import java.util.Map;
import java.util.TreeMap;

import tools.collections.map.Mip;
import tools.enumeration.combinations.BoolCombinations;
import tools.scanner.list.ScanLs;
import tools.strings.S;
import tools.strings.SB;
import tools.tuple.Pos;

public class Day21c {
	private static long sum = 0;
	private static int pin[][] = {
			{ '7', '8', '9' },
			{ '4', '5', '6' },
			{ '1', '2', '3' },
			{ '#', '0', 'A' }
	};
	private static int move[][] = {
			{ '#', '^', 'A' },
			{ '<', 'v', '>' },
	};
	private static Mip mpin = new Mip(pin);
	private static Mip mmv = new Mip(move);

	private static Map<String, Long> memo = new TreeMap<>();

	public static void main(String[] args) {
		var ls = ScanLs.readRaw();
		for (var s: ls) sum += calc(s);
		S.o();
		S.o(sum);
	}

	private static long calc(String s) {
		long sum = 0;
		long num = S.l(s.substring(0, 3));
		char x = 'A';
		for (char c: s.toCharArray()) {
			Pos p1 = mpin.get((int) x);
			Pos p2 = mpin.get((int) c);
			char v = p1.l > p2.l ? '^' : 'v';
			char h = p1.c > p2.c ? '<' : '>';
			int nv = Math.abs(p1.l - p2.l);
			int nh = Math.abs(p1.c - p2.c);
			long min = Long.MAX_VALUE;
			Loop: for (var lb: new BoolCombinations(nv, nh)) {
				SB sb = new SB();
				Pos p = p1;
				for (boolean b: lb) {
					char d = b ? v : h;
					sb.append(d);
					if (d == '^') p = p.up();
					if (d == 'v') p = p.down();
					if (d == '<') p = p.left();
					if (d == '>') p = p.right();
					if (pin[p.l][p.c] == '#') continue Loop;
				}
				long res = rec(sb + "A", D);
				if (res < min) min = res;
			}
			sum += min;
			x = c;
		}
		S.o(sum, '*', num);
		return sum * num;
	}
	
	private static long rec(String s, int depth) {
		if (depth == 0) return s.length();
		Long m = memo.get(s + '|' + depth);
		if (m != null) return m;
		long sum = 0;
		var toks = s.replace("A","-A").split("A");
		for (String t: toks) {
			t = t.replace("-", "A");
			char x = 'A';
			long inter = 0;
			for (char c: t.toCharArray()) {
				Pos p1 = mmv.get((int) x);
				Pos p2 = mmv.get((int) c);
				char v = p1.l > p2.l ? '^' : 'v';
				char h = p1.c > p2.c ? '<' : '>';
				int nv = Math.abs(p1.l - p2.l);
				int nh = Math.abs(p1.c - p2.c);
				long min = Long.MAX_VALUE;
				Loop: for (var lb: new BoolCombinations(nv, nh)) {
					SB sb = new SB();
					Pos p = p1;
					for (boolean b: lb) {
						char d = b ? v : h;
						sb.append(d);
						if (d == '^') p = p.up();
						if (d == 'v') p = p.down();
						if (d == '<') p = p.left();
						if (d == '>') p = p.right();
						if (move[p.l][p.c] == '#') continue Loop;
					}
					long res = rec(sb + "A", depth - 1);
					if (res < min) min = res;
				}
				inter += min;
				x = c;
			}
			memo.put(t + '|' + depth, inter);
			sum += inter;
		}
		return sum;
	}
	
	static int D = 25;
}