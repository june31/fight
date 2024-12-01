package tools.scanner.list;

import tools.collections.string.Ls;
import tools.scanner.Scan;

public class ScanLs {
	public static Ls readRaw() {
		Ls ls = new Ls();
		try {
			String l;
			while (!(l = Scan.readLine()).isEmpty()) ls.add(l);
		} catch (Throwable t) {}
		return ls;
	}
	
	public static Ls readRawStrings() {
		Ls ls = new Ls();
		try {
			String l;
			while (!(l = Scan.readLine()).isEmpty()) for (String s: l.split("\\s+")) ls.add(s);
		} catch (Throwable t) {}
		return ls;
	}

	public static Ls readLines() {
		Ls ls = new Ls();
		int n = Scan.readInt();
		for (int i = 0; i < n; i++) ls.add(Scan.readLine());
		return ls;
	}

	public static Ls readLines(int n) {
		Ls ls = new Ls();
		for (int i = 0; i < n; i++) ls.add(Scan.readLine());
		return ls;
	}

	public static Ls readStrings() {
		Ls ls = new Ls();
		int n = Scan.readInt();
		for (int i = 0; i < n; i++) ls.add(Scan.readString());
		return ls;
	}

	public static Ls readStrings(int n) {
		Ls ls = new Ls();
		for (int i = 0; i < n; i++) ls.add(Scan.readString());
		return ls;
	}

	public static Ls readLine() { return readLine(" "); }
	public static Ls readLine(String regex) {
		return new Ls(Scan.readLine().trim(), regex);
	}
	
	public static Ls readCharsInString() { return new Ls(Scan.readString(), ""); }
	public static Ls readCharsInLine() { return readLine(""); }	
}
