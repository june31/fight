package tools.bfs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

import tools.bfs.util.BFSMonoBase;

public final class BFSMono extends BFSMonoBase {

	public BFSMono(int n) {
		super(n);
	}
	
	@Override
	public int diffuse(int s, BooleanSupplier end) {
		endCondition = end;
		List<Integer> current = new ArrayList<>();
		List<Integer> next = new ArrayList<>();
		current.add(s);
		if (!clean) for (int i = 0; i < backtrack.length; i++) backtrack[i] = 0;
		clean = false;
		turn = 0;
		found = true;
		scanned = 0;
		id = s;
		if (endCondition.getAsBoolean()) return 0;
		scanned = 1;
		backtrack[s] = -1;
		turn = 1;
		
		while (true) {
			for (int i = 0; i < current.size(); i++) {
				id = current.get(i);
				List<IntSupplier> curMoves = moves.get(id);
				for (IntSupplier ism: curMoves) {
					int id2 = ism.getAsInt();
					int back = backtrack[id2];
					if (back != 0) continue;
					backtrack[id2] = USED_BIT | id; 
					scanned++;
					if (endCondition.getAsBoolean()) return turn;
					next.add(id2);
				}
			}
			if (next.size() == 0) {
				found = false;
				break;
			}

			List<Integer> tmp = current;
			current = next;
			next = tmp;
			next.clear();
			turn++;
		}

		return turn;
	}
}
