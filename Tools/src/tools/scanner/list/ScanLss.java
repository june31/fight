package tools.scanner.list;

import tools.collections.multi.Lss;
import tools.scanner.Scan;
import tools.tuple.SS;

public class ScanLss {
	public static Lss read() { return read(Scan.readInt()); }
	public static Lss read(int size) { 
		Lss l = new Lss();
		for (int i = 0; i < size; i++) l.add(new SS(Scan.readString(), Scan.readString()));
		return l;
	}
	public static Lss readRaw() {
		Lss l = new Lss();
		try {
			String[] vals;
			while ((vals = Scan.readLine().strip().split("\\s+")).length != 0) {
				for (int i = 0; i < vals.length; i += 2) l.add(new SS(vals[i], vals[i + 1]));
			}
		} catch (Throwable t) {}
		return l;
	}

	public static Lss readLine() {
		Lss l = new Lss();
		String[] vals = Scan.readLine().strip().split("\\s+");
		for (int i = 0; i < vals.length; i += 2)
			l.add(new SS(vals[i], vals[i + 1]));
		return l;
	}
}
