package currentCG;

import tools.dichotomy.Search;
import tools.function.DoubleToDoubleFunction;
import tools.math.Num;
import tools.scanner.Scan;
import tools.tables.Table;

public class CGS_Blunder3 {

	static final long MAX = 10_000_000_000_000_000l;
	static final String[] names = {
			"O(1)", "O(log n)", "O(n)", "O(n log n)", "O(n^2)", "O(n^2 log n)", "O(n^3)", "O(2^n)"
	};
	static final DoubleToDoubleFunction[] funcs = {
			x -> 1,
			x -> Math.log(x),
			x -> x,
			x -> x * Math.log(x),
			x -> x * x,
			x -> x * x * Math.log(x),
			x -> x * x * x,
			x -> x > 50 ? MAX : Math.pow(2, x) 
	};

	public static void main(String[] args) {
		int n = Scan.readInt();
		long[] xs = new long[n];
		long[] ys = new long[n];
		Scan.readLongArrays(n, xs, ys);
		Table.applyLong(ys, y -> 1_000_000 * y); // improve resolution

		int id = Num.minLong(names.length, i ->
			Search.minLong(x -> {
				double sum = 0;
				for (int j = 0; j < n; j++) {
					double abs = Math.abs(x * funcs[i].applyAsDouble(xs[j]) - ys[j]);
					if (abs > MAX) abs = MAX;
					sum += abs;
				}
				return (long) sum;
			}).value
		).index;

		System.out.println(names[id]);
	}
}

