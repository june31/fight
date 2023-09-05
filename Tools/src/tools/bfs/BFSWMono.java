package tools.bfs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

import tools.bfs.util.BFSMonoBase;
import tools.tuple.IL;

public final class BFSWMono extends BFSMonoBase {

	public int[] weights;
	public long weightSum;
	
	public BFSWMono(int n, int[] weights) {
		super(n);
		this.weights = weights;
	}
	
	@Override
	public int diffuse(int s, BooleanSupplier end) {
		endCondition = end;
		List<IL> todo = new ArrayList<>();
		todo.add(new IL(s, 0l));
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
		
		while (!todo.isEmpty()) {
			IL il = todo.remove(todo.size() - 1);
			int id = il.index;
			long w = il.value;
			List<IntSupplier> curMoves = moves.get(id);
			for (IntSupplier ism: curMoves) {
				int id2 = ism.getAsInt();
				int back = backtrack[id2];
				if (back != 0) continue;
				backtrack[id2] = USED_BIT | id; 
				scanned++;
				long w2 = w + weights[id2];
				if (endCondition.getAsBoolean()) {
					weightSum = w2;
					return turn;
				}
				todo.add(new IL(id2, w2));
				todo.sort((il1, il2) -> Long.compare(il2.value, il1.value));
			}
		}
		return turn;
	}
}
