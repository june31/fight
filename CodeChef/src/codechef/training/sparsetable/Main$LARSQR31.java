package codechef.training.sparsetable;

import tools.dichotomy.Search;
import tools.scanner.Scan;
import tools.structures.sparsetable.SparseTableInt;

public class Main$LARSQR31 {

	public static void main(String[] args) {
		int n = Scan.readInt();
		int[] left = new int[n];
		int[] right = new int[n];
		for (int i = 0; i < n; i++) {
			left[i] = Scan.readInt();
			right[i] = Scan.readInt();
		}

		var maxLeft = new SparseTableInt(left, Math::max);
		var minRight = new SparseTableInt(right, Math::min);
		
		System.out.println(Search.maxTrue(n, size -> {
			for (int i = 0; i < n + 1 - size; i++) {
				int l = maxLeft.get(i, i + size - 1);
				int r = minRight.get(i, i + size - 1);
				if (r - l + 1 >= size) return true;
			}
			return false;
		}));
	}
}
