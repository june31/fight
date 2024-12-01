package tools.scanner.list;

import tools.collections.multi.Lsi;
import tools.scanner.Scan;
import tools.tuple.SI;

public class ScanLsi {
	public static Lsi read() { return read(Scan.readInt()); }
	public static Lsi read(int size) { 
		Lsi l = new Lsi();
		for (int i = 0; i < size; i++) l.add(new SI(Scan.readString(), Scan.readInt()));
		return l;
	}
	public static Lsi readRaw() {
		Lsi l = new Lsi();
		try {
			String[] vals;
			while ((vals = Scan.readLine().strip().split("\\s+")).length != 0) {
				for (int i = 0; i < vals.length; i += 2) l.add(new SI(vals[i + 1], Integer.parseInt(vals[i + 1])));
			}
		} catch (Throwable t) {}
		return l;
	}

	public static Lsi readLine() {
		Lsi l = new Lsi();
		String[] vals = Scan.readLine().strip().split("\\s+");
		for (int i = 0; i < vals.length; i += 2)
			l.add(new SI(vals[i], Integer.parseInt(vals[i + 1])));
		return l;
	}
}
