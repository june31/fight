package tools.scanner.list;

import tools.collections.multi.Lps;
import tools.scanner.Scan;
import tools.tuple.PS;
import tools.tuple.Pos;

public class ScanLps {
	public static Lps read() { return read(Scan.readInt()); }

	public static Lps read(int size) {
		Lps l = new Lps();
		for (int i = 0; i < size; i++) l.add(new PS(
				new Pos(Integer.parseInt(Scan.readString()), Integer.parseInt(Scan.readString())),
				Scan.readString()));
		return l;
	}

	public static Lps readRaw() {
		Lps l = new Lps();
		try {
			String[] vals;
			while ((vals = Scan.readLine().strip().split("\\s+")).length != 0) {
				for (int i = 0; i < vals.length; i += 2) l.add(new PS(new Pos(vals[i]), vals[i + 1]));
			}
		} catch (Throwable t) {}
		return l;
	}

	public static Lps readLine() {
		Lps l = new Lps();
		String[] vals = Scan.readLine().strip().split("\\s+");
		for (int i = 0; i < vals.length; i += 2)
			l.add(new PS(new Pos(vals[i]), vals[i + 1]));
		return l;
	}
}
