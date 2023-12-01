package tools.scanner.list;

import tools.collections.int64.Ll;
import tools.scanner.Scan;

public class ScanLl {
	public static Ll readLine() { return new Ll(Scan.readLine()); }
	public static Ll readChars() {
		Ll l = new Ll();
		String s = Scan.readString();
		for (char c : s.toCharArray()) l.add((long) c);
		return l;
	}
	public static Ll read() { return read(Scan.readInt()); }
	public static Ll read(int size) { 
		Ll l = new Ll();
		for (int i = 0; i < size; i++) l.add(Scan.readLong());
		return l;
	}
	public static Ll readUntilEmptyLine() {
		Ll l = new Ll();
		try {
			String s;
			while (!(s = Scan.readLine()).isEmpty()) l.add(Long.parseLong(s));
		} catch (Throwable t) {}
		return l;
	}
	public static Ll readRaw() {
		Ll l = new Ll();
		try {
			String s;
			while (true) if (!(s = Scan.readLine()).isEmpty()) l.add(Long.parseLong(s));
		} catch (Throwable t) {}
		return l;
	}
}
