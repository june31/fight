package currentCG;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import tools.directions.Dir;
import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

public class CGS_Blunder1 {
	static boolean beer = false;
	
	public static void main(String[] args) {
		Map<Dir, String> txt = new HashMap<>();
		txt.put(Dir.R, "EAST\n");
		txt.put(Dir.L, "WEST\n");
		txt.put(Dir.U, "NORTH\n");
		txt.put(Dir.D, "SOUTH\n");
		Dir[][] prios = {
				{ Dir.D, Dir.R, Dir.U, Dir.L },
				{ Dir.L, Dir.U, Dir.R, Dir.D }
		};
		
		int[][] map = Scan.readMap2();
		Table.debugMapLC(map);
		Pos p = Table.find(map, '@');
		Pos[] tp = Table.findAll(map, 'T');
		Set<Hist> hist = new HashSet<>();
		Dir d = Dir.D;
		StringBuilder sb = new StringBuilder();
		int pid = 0;
		loop: while (true) {
			Hist h = new Hist(p, d, beer);
			if (hist.contains(h)) {
				System.out.println("LOOP");
				return;
			}
			hist.add(h);
			int v = d.peek(map, p);
			if (canMove(v)) {
				p = d.next(p);
				sb.append(txt.get(d));
				switch (v) {
				case ' ': break;
				case 'T': p = p.equals(tp[0]) ? new Pos(tp[1]) : new Pos(tp[0]); break;
				case 'X': hist.clear(); map[p.l][p.c] = ' '; break;
				case 'I': pid ^= 1; break;
				case 'B': beer ^= true; break;
				case 'E': d = Dir.R; break;
				case 'W': d = Dir.L; break;
				case 'N': d = Dir.U; break;
				case 'S': d = Dir.D; break;
				case '$': System.out.println(sb); return;
				}
			} else {
				for (int i = 0; i < 4; i++) {
					if (canMove(prios[pid][i].peek(map, p))) {
						d = prios[pid][i];
						continue loop;
					}
				}
				System.out.println("LOOP");
				return;					
			}
		}
	}

	private static boolean canMove(int v) {
		return (v != 'X' || beer) && v != '#' && v != -1;
	}
}

class Hist {
	Pos p;
	Dir d;
	boolean b;
	Hist(Pos p, Dir d, boolean b) { this.p = p; this.d = d; this.b = b; }
	@Override
	public int hashCode() {
		return Objects.hash(d, p, b);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hist other = (Hist) obj;
		return d == other.d && Objects.equals(p, other.p) && b == other.b;
	}
	
}
