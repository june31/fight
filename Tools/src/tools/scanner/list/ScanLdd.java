package tools.scanner.list;

import tools.collections.int32.L;
import tools.collections.multi.Ldd;
import tools.scanner.Scan;
import tools.tuple.DD;

public class ScanLdd {
	public static Ldd read() { return read(Scan.readInt()); }
	public static Ldd read(int size) { 
		Ldd l = new Ldd();
		for (int i = 0; i < size; i++) l.add(new DD(Scan.readDouble(), Scan.readDouble()));
		return l;
	}
	public static Ldd readRaw() {
		Ldd l = new Ldd();
		try {
			double[] vals;
			while ((vals = Scan.readDoubleLine()).length != 0) l.add(new DD(vals[0], vals[1]));
		} catch (Throwable t) {}
		return l;
	}
	
	public static Ldd readTimeIntervals(int size) {
		Ldd lr = new Ldd();
		for (int i = 0; i < size; i++) {
			L l = ScanL.readLine();
			lr.add(l.get(0) * 60 + l.get(1), l.get(2) * 60 + l.get(3));
		}
		return lr;
	}
}
