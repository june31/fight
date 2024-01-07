package tools.scanner.list;

import tools.collections.int64.Ll;
import tools.scanner.Scan;

public class ScanLl {
	public static Ll readLine() { return new Ll(Scan.readLine()); }
	public static Ll read() { return read(Scan.readInt()); }
	public static Ll read(int size) { 
		Ll l = new Ll();
		for (int i = 0; i < size; i++) l.add(Scan.readLong());
		return l;
	}
	public static Ll readRaw() {
		Ll ll = new Ll();
		try {
			String l;
			while (!(l = Scan.readLine()).isEmpty()) ll.addAll(new Ll(l));
		} catch (Throwable t) {}
		return ll;
	}
}
