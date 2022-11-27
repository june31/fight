package codechef.training.sparsetable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import tools.output.Out;
import tools.scanner.Scan;
import tools.structures.sparsetable.SparseTableInt;

public class Main$MSTICK {

	public static void main(String[] args) throws java.lang.Exception {
		int N = Scan.readInt();
		int tab[] = Scan.readIntArray(N);
		NumberFormat formatter = new DecimalFormat("#0.0", DecimalFormatSymbols.getInstance(Locale.US));     
		SparseTableInt spiMin = new SparseTableInt(tab, Integer.MAX_VALUE, Math::min);
		SparseTableInt spiMax = new SparseTableInt(tab, Integer.MIN_VALUE, Math::max);

		Scan.loop(() -> {
			int L = Scan.readInt();
			int R = Scan.readInt();
			int toCenter = spiMin.get(L, R);
			int a = toCenter + spiMax.get(0, L-1);
			double b = toCenter + (spiMax.get(L, R) - toCenter) / 2.0;
			int c = toCenter + spiMax.get(R+1, N-1);
			Out.bufln(formatter.format(Math.max(Math.max(a, b), c)));
		});
		
		Out.flush();
	}
}
