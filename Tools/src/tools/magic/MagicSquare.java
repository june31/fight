package tools.magic;

public class MagicSquare {
/*	public final int nL;
	public final int nC;
	public final int max;
	public final int meanL;
	public final int meanC;
	public final List<int[][]> maps = new ArrayList<>();
	public final List<Filter> filters = new ArrayList<>();
	public final List<BooleanSupplier> conditions = new ArrayList<>();
	
	public int l;
	public int c; 
	public int v;
	public int depth;
	
	private boolean changed = false; 

	public MagicSquare(int nl, int nc, int max) {
		nL = nl;
		nC = nc;
		this.max = max;
		meanL = max * (max + 1) / 2 / nl;
		meanC = max * (max + 1) / 2 / nc;
		maps.add(new int[nl][nc]);
	}
	
	public MagicSquare(int n, int max) {
		this(n, n, max);
	}
	
	public MagicSquare(int n) {
		this(n, n, n * n);
	}
	
	public void addFilters(Filter... fs) {
		if (changed) throw new IllegalAccessError("MagicSquare filters shall be set before the set() command is used.");
		for (Filter f: fs) filters.add(f);
	}
	
	public boolean set(int l, int c, int v) {
		this.l = l;
		this.c = c;
		this.v = v;
		changed = true;
		maps.get(0)[l][c] = v;
		return filterAll() && checkAll();
	}
	
	private boolean filterAll() {
		long h = (1l << max) - 1;
		for (Filter f: filters) h &= f.filters.get(depth)[l][c];
		return (h & (1l << (v-1))) != 0;
	}

	private long getAllowed() {
		long h = (1l << max) - 1;
		for (Filter f: filters) h &= f.filters.get(depth)[l][c];
		return h;
	}

	private boolean checkAll() {
		for (BooleanSupplier bs: conditions) if (!bs.getAsBoolean()) return false;
		return true;
	}

	public int[][] find() {
		depth = 0;
		int result = -2;
		IIL[][] tab = new IIL[nL][nC];
		SortedSet<IIL> todo = new TreeSet<>((iil1, iil2) -> Long.bitCount(iil1.value) - Long.bitCount(iil2.value)); 
		for (int i = 0; i < nL; i++) {
			for (int j = 0; j < nC; j++) {
				IIL iil = new IIL(i, j, getAllowed(0));
				tab[i][j] = iil;
				if (sets.get(0)[i][j]) todo.add(iil);
			}
		}
		while (depth >= 0) {
			if (todo.isEmpty()) return maps.get(depth);
			IIL iil = todo.first();
			todo.remove(iil);
			l = iil.line;
			c = iil.col;
			v = Long.numberOfTrailingZeros(iil.value) + 1;
			if (checkAll()) {
				if (++depth == maps.size()) {
					maps.add(Table.copy(maps.get(maps.size() - 1)));
					sets.add(Table.copy(sets.get(sets.size() - 1)));
				} else {
					for (int i = 0; i < n; i++)
					maps.set(depth, Table.copy(maps.get(maps.size() - 1)));
					sets.add(Table.copy(sets.get(sets.size() - 1)));
				}
			}
			
			
			
			
			
			
			result = board.process(result);
			if (result >= 0) {
				if (++depth == boards.size()) boards.add(boardSupplier.get());
				board.copyTo(boards.get(depth));
			} else if (result == SoloBoard.FAIL) depth--;
			else return board;
		}
		return null;
	}
	
	public abstract class Filter {
		public final List<long[][]> filters;
		Filter() {
			filters = new ArrayList<>();
			long[][] filter = new long[nL][nC];
			filters.add(filter);
			Table.fill(filter, (1l << max) - 1);
		}
		public abstract void update();
	}
	
	public class AllDistinctFilter extends Filter {
		public void update() { long[][] filter = filters.get(depth); for (int i = 0; i < nL; i++) for (int j = 0; j < nC; j++) filter[i][j] &= ~(1l << (v-1)); }
	}

	public class HDistinctFilter extends Filter {
		public void update() { long[][] filter = filters.get(depth); for (int i = 0; i < nC; i++) filter[l][i] &= ~(1l << (v-1)); }
	}
	
	public class VDistinctFilter extends Filter {
		public void update() { long[][] filter = filters.get(depth); for (int i = 0; i < nL; i++) filter[i][c] &= ~(1l << (v-1)); }
	}

	public class SquareDistinctFilter extends Filter {
		public void update() {
			long[][] filter = filters.get(depth);
			int size = (int) Math.sqrt(max);
			int sl = l / size;
			int sc = c / size;
			for (int i = sl; i < sl + size; i++) for (int j = sc; j < sc + size; j++) filter[i][j] &= ~(1l << (v-1));
		}
	}
	
	public BooleanSupplier hSumCondition() {
		return () -> {
			int sum = v;
			for (int i = 0; i < nC; i++) {
				if (i == c) continue;
				if (!sets.get(depth)[l][i]) return true;
				sum += maps.get(depth)[l][i];
			}
			return sum == meanC;
		};
	}
	
	public BooleanSupplier vSumCondition() {
		return () -> {
			int sum = v;
			for (int i = 0; i < nL; i++) {
				if (i == l) continue;
				if (!sets.get(depth)[i][c]) return true;
				sum += maps.get(depth)[i][c];
			}
			return sum == meanL;
		};
	}

	public BooleanSupplier decDiagSumCondition() {
		return () -> {
			if (l != c) return true;
			int sum = v;
			for (int i = 0; i < n; i++) {
				if (i == c) continue;
				if (!sets.get(depth)[i][i]) return true;
				sum += maps.get(depth)[i][i];
			}
			return sum == mean;
		};
	}
	
	public BooleanSupplier incDiagSumCondition() {
		return () -> {
			if (l != n - c - 1) return true;
			int sum = v;
			for (int i = 0; i < n; i++) {
				if (i == l) continue;
				if (!sets.get(depth)[i][n - i - 1]) return true;
				sum += maps.get(depth)[i][n - i - 1];
			}
			return sum == mean;
		};
	}*/
}

