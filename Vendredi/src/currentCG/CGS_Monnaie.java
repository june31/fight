package currentCG;

import java.util.ArrayList;
import java.util.List;

import tools.chrono.Chrono;
import tools.collections.int32.L;
import tools.scanner.Scan;
import tools.scanner.list.ScanL;
import tools.strings.S;

public class CGS_Monnaie {
	public static void main(String[] args) {
		Chrono.start();
		L notes = ScanL.readLine().sortedDown();
		int target = Scan.readInt();
		boolean[] used = new boolean[target];
		if (target == 0) {
            S.o("0");
            return;
        }
		List<L> current = new ArrayList<L>();
		current.add(new L());
		List<L> next;
		while (!current.isEmpty()) {
			next = new ArrayList<L>();
			for (L l: current) {
				for (int note: notes) {
					if (!l.isEmpty() && note > l.last()) continue; 
                	L newL = new L(l);
                	newL.add(note);
					var sum = newL.sum();
				    if (sum == target) {
                    	S.o(newL.join());
                		Chrono.stop();
                    	return;
                    }
				    if (sum < target) {
				    	next.add(newL);
					    used[sum] = true;
				    }
				}
			}
			current = next;
		}
		S.o("IMPOSSIBLE");
	}
}
