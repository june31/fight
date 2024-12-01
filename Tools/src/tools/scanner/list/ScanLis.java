package tools.scanner.list;

import tools.collections.multi.Lis;
import tools.scanner.Scan;
import tools.tuple.IS;

public class ScanLis {
	public static Lis read() { return read(Scan.readInt()); }
	public static Lis read(int size) { 
		Lis l = new Lis();
		for (int i = 0; i < size; i++) l.add(new IS(Scan.readInt(), Scan.readString()));
		return l;
	}
	public static Lis readRaw() {
		Lis l = new Lis();
		try {
			String[] vals;
			while ((vals = Scan.readLine().split("\\s+")).length != 0) {
				for (int i = 0; i < vals.length; i += 2) l.add(new IS(Integer.parseInt(vals[i]), vals[i + 1]));
			}
		} catch (Throwable t) {}
		return l;
	}

	public static Lis readLine() {
		Lis l = new Lis();
		String[] vals = Scan.readLine().split("\\s+");
		for (int i = 0; i < vals.length; i += 2)
			l.add(new IS(Integer.parseInt(vals[i]), vals[i + 1]));
		return l;
	}
}
