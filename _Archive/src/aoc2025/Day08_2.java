package aoc2025;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import tools.chrono.Chrono;
import tools.collections.pos.Lp3;
import tools.collections.string.Ls;
import tools.math.Dist3;
import tools.scanner.list.ScanLs;
import tools.strings.S;
import tools.tuple.Pos3;

public class Day08_2 {
	public static void main(String[] args) {
		Chrono.start();
		Ls in = ScanLs.readRaw();
		Lp3	l = new Lp3();
		for (String s: in) {
			var ss = s.split(",");
			Pos3 p = new Pos3(S.i(ss[0]), S.i(ss[1]), S.i(ss[2]));
			l.add(p);
		}

		var lsp = new ArrayList<Set<Pos3>>();
		var used = new HashSet<String>();
		
		int z = 0;
		while (true) {
			if (++z % 1000 == 0) S.o("Iteration " + z);
			long min = Long.MAX_VALUE;
			Pos3 mp = null;
			Pos3 mq = null;

			for (int i = 0; i < l.size() - 1; i++) {
				Pos3 p = l.get(i);
				for (int j = i+1; j < l.size(); j++) {
					Pos3 q = l.get(j);
					if (used.contains(p+""+q)) continue;
					long d = Dist3.squared(p, q);
					if (d < min) {
						min = d;
						mp = p;
						mq = q;
					}
				}
			}
			
			used.add(mp+""+mq);

			Set<Pos3> fsp = null;
			for (var sp: lsp) {
				if (fsp == null && (sp.contains(mp) || sp.contains(mq))) {
					sp.add(mp);
					sp.add(mq);
					fsp = sp;
				} else if (fsp != null && (sp.contains(mp) || sp.contains(mq))) {
					fsp.addAll(sp);
					sp.clear();
					break;
				}
			}
			if (fsp == null) {
				var ns = new HashSet<Pos3>();
				ns.add(mp);
				ns.add(mq);
				lsp.add(ns);
			}
			else if (fsp.size() == l.size()) {
				S.o((long) mp.x * (long) mq.x);
				Chrono.stop();
				return;
			}
		}
	}
}
