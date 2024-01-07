package tools.scanner.list;

import tools.collections.float64.Ld;
import tools.scanner.Scan;

public class ScanLd {
	public static Ld readLine() { return new Ld(Scan.readLine()); }
	public static Ld read() { return read(Scan.readInt()); }
	public static Ld read(int size) { 
		Ld l = new Ld();
		for (int i = 0; i < size; i++) l.add(Scan.readDouble());
		return l;
	}
	public static Ld readRaw() {
		Ld ld = new Ld();
		try {
			String l;
			while (!(l = Scan.readLine()).isEmpty()) ld.addAll(new Ld(l));
		} catch (Throwable t) {}
		return ld;
	}
}
