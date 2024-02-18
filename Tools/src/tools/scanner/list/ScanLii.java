package tools.scanner.list;

import tools.collections.multi.Lii;
import tools.scanner.Scan;
import tools.tuple.II;

public class ScanLii {
	public static Lii read() { return read(Scan.readInt()); }
	public static Lii read(int size) { 
		Lii l = new Lii();
		for (int i = 0; i < size; i++) l.add(new II(Scan.readInt(), Scan.readInt()));
		return l;
	}
	public static Lii readRaw() {
		Lii l = new Lii();
		try {
			int[] vals;
			while ((vals = Scan.readIntLine()).length != 0) l.add(new II(vals[0], vals[1]));
		} catch (Throwable t) {}
		return l;
	}
}
