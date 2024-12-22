package aoc24.done;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import tools.bfs.BFS;
import tools.collections.int32.L;
import tools.collections.map.Msli;
import tools.collections.map.Mss;
import tools.collections.pos.Lp;
import tools.scanner.list.ScanLs;
import tools.strings.S;
import tools.strings.SB;

public class Day21b {
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
	private static Msli mpin = new Msli();
	private static Mss mmv = new Mss();

	private static Map<String, Long> memo = new TreeMap<>();


	public static void main(String[] args) {
		BFS bp = new BFS(pin);
		bp.setPossibleMoves(b -> List.of(
				() -> { b.c2--; },
				() -> { b.l2++; },
				() -> { b.l2--; },
				() -> { b.c2++; }
		));

		for (int l = 0; l < 4; l++) {
			for (int c = 0; c < 3; c++) {
				if (pin[l][c] == '#') continue;
				for (int y = 0; y < 4; y++) {
					for (int x = 0; x < 3; x++) {
						if (pin[y][x] == '#') continue;
						bp.setEnd(y, x).diffuse(l, c);
						//S.o(l, c, y, x);
						Lp lp = bp.shortestPath();
						L ld = new L();
						for (int i = 0; i < lp.size()-1; i++) {
							if (lp.get(i+1).c > lp.get(i).c) ld.add('>');
							if (lp.get(i+1).c < lp.get(i).c) ld.add('<');
							if (lp.get(i+1).l > lp.get(i).l) ld.add('v');
							if (lp.get(i+1).l < lp.get(i).l) ld.add('^');
						}
						ld = switch (ld.joinC()) {
						case "vv>v" -> new L(">vvv".toCharArray());
						case "v>v" -> new L(">vv".toCharArray());
						case ">v>" -> new L(">>v".toCharArray());
						case "^<^^" -> new L("^^^<".toCharArray());
						case "^<^" -> new L("^^<".toCharArray());
						case "<^<" -> new L("^<<".toCharArray());
						case "<^<^" -> new L("^^<<".toCharArray());
						case "v>v>" -> new L(">>vv".toCharArray());
						case "vv>v>" -> new L(">>vvv".toCharArray());
						case "<^<^^" -> new L("^^^<<".toCharArray());
						default -> ld;
						};
						ld.add('A');
						mpin.put("" + ((char) pin[l][c]) + ((char) pin[y][x]), ld);
					}
				}
			}
		}
		BFS bm = new BFS(move);
		bm.setPossibleMoves(b -> List.of(
				() -> { b.c2--; },
				() -> { b.l2++; },
				() -> { b.c2++; },
				() -> { b.l2--; }
		));
		for (int l = 0; l < 2; l++) {
			for (int c = 0; c < 3; c++) {
				if (move[l][c] == '#') continue;
				for (int y = 0; y < 2; y++) {
					for (int x = 0; x < 3; x++) {
						if (move[y][x] == '#') continue;
						bm.setEnd(y, x).diffuse(l, c);
						Lp lp = bm.shortestPath();
						SB sb = new SB();
						for (int i = 0; i < lp.size()-1; i++) {
							if (lp.get(i+1).c > lp.get(i).c) sb.a('>');
							if (lp.get(i+1).c < lp.get(i).c) sb.a('<');
							if (lp.get(i+1).l > lp.get(i).l) sb.a('v');
							if (lp.get(i+1).l < lp.get(i).l) sb.a('^');
						}
						if (sb.toString().equals("<v<")) sb = new SB("v<<");
						sb.a('A');
						mmv.put("" + ((char) move[l][c]) + ((char) move[y][x]), sb.toString());
					}
				}
			}
		}

		var ls = ScanLs.readRaw();
		for (var s: ls) {
			sum += r(s);
		}
		S.o();
		S.o(sum);
		S.o();
		for (int i = 1; i <= -D; i++) {  
			S.o("Depth = " + i);
			for (var e: memo.entrySet()) {
				if (e.getKey().endsWith("|" + i)) {
					var k = e.getKey();
					S.o(k.substring(0, k.indexOf('|')) + "  " + e.getValue());
				}
			}
			S.o();
		}
	}
	private static long r(String s) {
		long sum = 0;
		long num = S.l(s.substring(0, 3));
		char x = 'A';
		SB sb = new SB();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			L l1 = mpin.get("" + x + c);
			for (int a: l1) sb.c(a);
			x = c;
		}
		
		sum += rec(sb.toString(), D);
		S.o();
		S.o(sum, num);
		return num * sum;
	}
	private static long rec(String s, int depth) {
		//if (depth == 0) S.p(s);
		if (depth == 0) return s.length();
		Long m = memo.get(s+"|"+depth);
		if (m != null) {
			return m;
		}
		long sum = 0;
		var toks = s.replace("A","-A").split("A");
		for (String t: toks) {
			t = t.replace("-", "A");
			char x = 'A';
			for (char c: t.toCharArray()) {
				String r = mmv.get("" + x + c);
				x = c;
				long res = rec(r, depth - 1);
				memo.put(r+"|"+(depth-1), res);
				if (res < 0) S.o("huh");
				sum += res;
			}
		}
		return sum;
	}
	
	static int D = 25;
}