package tools.scanner.list;

import tools.collections.multi.Liii;
import tools.scanner.Scan;
import tools.tuple.III;

public class ScanLiii {
	public static Liii read() { return read(Scan.readInt()); }
	public static Liii read(int size) { 
		Liii l = new Liii();
		for (int i = 0; i < size; i++) l.add(new III(Scan.readInt(), Scan.readInt(), Scan.readInt()));
		return l;
	}
	public static Liii readRaw() {
		Liii l = new Liii();
		try {
			int[] vals;
			while ((vals = Scan.readIntLine()).length != 0) l.add(new III(vals[0], vals[1], vals[2]));
		} catch (Throwable t) {}
		return l;
	}
}
