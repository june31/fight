package tools.scanner.list;

import tools.collections.int32.L;
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
		L vals = ScanL.readRaw();
		for (int i = 0; i < vals.size(); i += 2) l.add(new II(vals.get(i), vals.get(i + 1)));
		return l;
	}

	public static Lii readLine() {
		Lii l = new Lii();
		L vals = ScanL.readLine();
		for (int i = 0; i < vals.size(); i += 2) l.add(new II(vals.get(i), vals.get(i + 1)));
		return l;
	}
}
