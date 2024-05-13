package tools.scanner.list;

import tools.collections.int32.L;
import tools.collections.multi.Lll;
import tools.scanner.Scan;
import tools.tuple.LL;

public class ScanLll {
	public static Lll read() { return read(Scan.readInt()); }
	public static Lll read(int size) { 
		Lll l = new Lll();
		for (int i = 0; i < size; i++) l.add(new LL(Scan.readLong(), Scan.readLong()));
		return l;
	}
	public static Lll readRaw() {
		Lll l = new Lll();
		try {
			long[] vals;
			while ((vals = Scan.readLongLine()).length != 0) l.add(new LL(vals[0], vals[1]));
		} catch (Throwable t) {}
		return l;
	}
	
	public static Lll readTimeIntervals(int size) {
		Lll lr = new Lll();
		for (int i = 0; i < size; i++) {
			L l = ScanL.readLine();
			lr.add(l.get(0) * 60 + l.get(1), l.get(2) * 60 + l.get(3));
		}
		return lr;
	}
}
