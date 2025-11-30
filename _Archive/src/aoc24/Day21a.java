package aoc24;

import java.util.List;

import tools.bfs.BFS;
import tools.collections.int32.L;
import tools.collections.map.Msli;
import tools.collections.pos.Lp;
import tools.scanner.list.ScanLs;
import tools.strings.S;
import tools.strings.SB;

public class Day21a {
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
	private static Msli mmv = new Msli();
	
	//private static  boolean q = false;
	
	
	public static void main(String[] args) {
		BFS bp = new BFS(pin);
		bp.setPossibleMoves(b -> List.of(
				() -> { b.c2--; },
				() -> { b.l2++; },
				() -> { b.c2++; },
				() -> { b.l2--; }));

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
						if (ld.size() == 4 && ld.first() == ld.get(2)) {
							S.o(ld.joinC());
						}
						ld = switch (ld.joinC()) {
						case "vv>v" -> new L(">vvv".toCharArray());
						case "v>v" -> new L(">vv".toCharArray());
						case ">v>" -> new L(">>v".toCharArray());
						case "^<^^" -> new L("^^^<".toCharArray());
						case "^<^" -> new L("^^<".toCharArray());
						case "<^<" -> new L("^<<".toCharArray());
						case "<^<^" -> new L("^^<<".toCharArray());
						case "v>v>" -> new L("<<vv".toCharArray());
						default -> ld;
						};
						//ld = q ? ld.sortedUp() : ld.sortedDown();;
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
				() -> { b.l2--; }));
		for (int l = 0; l < 2; l++) {
			for (int c = 0; c < 3; c++) {
				if (move[l][c] == '#') continue;
				for (int y = 0; y < 2; y++) {
					for (int x = 0; x < 3; x++) {
						if (move[y][x] == '#') continue;
						bm.setEnd(y, x).diffuse(l, c);
						//S.o(l, c, y, x);
						Lp lp = bm.shortestPath();
						L ld = new L();
						for (int i = 0; i < lp.size()-1; i++) {
							if (lp.get(i+1).c > lp.get(i).c) ld.add('>');
							if (lp.get(i+1).c < lp.get(i).c) ld.add('<');
							if (lp.get(i+1).l > lp.get(i).l) ld.add('v');
							if (lp.get(i+1).l < lp.get(i).l) ld.add('^');
						}
						//if (!ld.isEmpty()) ld = ld.first() == ld.last() ? ld.sortedDown() : ld.sortedUp();
						//ld = q ? ld.sortedUp() : ld.sortedDown();;
						if (ld.size() >= 3 && ld.first() == ld.last()) {
							//S.o(ld.joinC());
						}
						if (ld.joinC().equals("<v<")) ld = new L("v<<".toCharArray());
						ld.add('A');
						mmv.put("" + ((char) move[l][c]) + ((char) move[y][x]), ld);
					}
				}
			}
		}
		
		var ls = ScanLs.readRaw();
		for (var s: ls) {
			sum += r(s);
		}
		//S.o(mpin);
		S.o();
		S.o(sum);
	}
	private static long r(String s) {
		SB sa = new SB();
		SB sb = new SB();
		SB sc = new SB();
		int sum = 0;
		long num = S.l(s.substring(0, 3));
		char x = 'A';
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			sa.append(c);
			L l1 = mpin.get("" + x + c);
			char y = 'A';
			for (int j: l1) {
				char d = (char) j;
				sb.append(d);
				L l2 = mmv.get("" + y + d);
				char z = 'A';
				for (int k: l2) {
					char e = (char) k;
					sc.append(e);
					L l3 = mmv.get("" + z + e);
					for (int a:l3) {
						S.p((char) a);
					}
					sum += l3.size();
					z = e;
				}
				y = d;
			}
			x = c;
		}
		S.o();
		S.o(sc);
		S.o(sb);
		S.o(sa);
		S.o(sum, '*', num);
		S.o();
		return num * sum;
	}
}
