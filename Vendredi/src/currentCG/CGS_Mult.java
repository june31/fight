package currentCG;

import java.util.ArrayList;
import java.util.List;

import tools.scanner.Scan;
import tools.tables.Table;

public class CGS_Mult {
	public static void main(String[] args) {
		int[] s = Table.toIntArray(Scan.readLine().split(" "));
		int l = s.length;
		long n = Scan.readLong();
		
		long count = 0;
		int sign = 1;
		System.out.println(s.length);
		
		List<Integer> ll = new ArrayList<>();
		L: for (int i: s) {
			for (int j: s) if (i != j && i % j == 0) continue L;
			ll.add(i);
		}
		
		System.out.println(ll.size());
		/*for (int i = 1; i <= l; i++) {
			for (int[] c : new IntCombinations(s, i)) {
				//count += sign * (n/Num.lcm());
			}
		}*/
	}
}
