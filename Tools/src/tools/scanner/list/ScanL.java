package tools.scanner.list;

import tools.collections.int32.L;
import tools.scanner.Scan;

public class ScanL {
	public static L readLine() { return new L(Scan.readLine()); }
	public static L readChars() {
		L l = new L();
		String s = Scan.readString();
		for (char c : s.toCharArray()) l.add((int) c);
		return l;
	}
	public static L read() { return read(Scan.readInt()); }
	public static L read(int size) { 
		L l = new L();
		for (int i = 0; i < size; i++) l.add(Scan.readInt());
		return l;
	}
	public static L readUntilEmptyLine() {
		L l = new L();
		try {
			String s;
			while (!(s = Scan.readLine()).isEmpty()) l.add(Integer.parseInt(s));
		} catch (Throwable t) {}
		return l;
	}
	public static L readRaw() {
		L l = new L();
		try {
			String s;
			while (true) if (!(s = Scan.readLine()).isEmpty()) l.add(Integer.parseInt(s));
		} catch (Throwable t) {}
		return l;
	}
}
