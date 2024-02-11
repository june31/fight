package tooltests.structures.sparsetable;

import tools.math.Num;
import tools.scanner.Scan;
import tools.structures.sparsetable.SparseTableInt;

// https://www.codingame.com/ide/puzzle/stock-exchange-losses
public class CGS_StockExchange {
	public static void main(String[] args) {
		int[] t = Scan.readInts();
		SparseTableInt st = new SparseTableInt(t, Math::min);
		System.out.println(Num.min(t, i -> st.get(i, t.length - 1) - t[i]).value);
	}
}
