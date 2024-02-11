package tools.scanner.list;

import tools.collections.pos.Lp;
import tools.scanner.Scan;
import tools.tuple.Pos;

public class ScanLp {
	public static Lp readLC() { return readLC(Scan.readInt()); }
	public static Lp readLC(int size) { 
		Lp lp = new Lp();
		for (int i = 0; i < size; i++) lp.add(new Pos(Scan.readLine()));
		return lp;
	}
	public static Lp readCL() { return readCL(Scan.readInt()); }
	public static Lp readCL(int size) { 
		Lp lp = new Lp();
		for (int i = 0; i < size; i++) lp.add(new Pos(Scan.readLine()).reverse());
		return lp;
	}
	public static Lp readLCRaw() {
		Lp lp = new Lp();
		try {
			String line;
			while (!(line = Scan.readLine()).isEmpty()) lp.add(new Pos(line));
		} catch (Throwable t) {}
		return lp;
	}
	public static Lp readCLRaw() {
		Lp lp = new Lp();
		try {
			String line;
			while (!(line = Scan.readLine()).isEmpty()) lp.add(new Pos(line).reverse());
		} catch (Throwable t) {}
		return lp;
	}
}
