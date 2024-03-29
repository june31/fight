package tools.scanner.list;

import tools.collections.string.Ls;
import tools.scanner.Scan;

public class ScanLs {
	public static Ls readRawLines() {
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
			while (!(l = Scan.readLine()).isEmpty()) for (String s: l.split(" ")) ls.add(s);
		} catch (Throwable t) {}
		return ls;
	}

	public static Ls readLines() {
		Ls ls = new Ls();
		int n = Scan.readInt();
		for (int i = 0; i < n; i++) ls.add(Scan.readLine());
		return ls;
	}

	public static Ls readWords() { return readWords(" "); }
	public static Ls readWords(String regex) {
		Ls ls = new Ls();
		for (String s: Scan.readLine().trim().split(regex)) ls.add(s);
		return ls;
	}
}
