package tools.scanner.list;

import tools.collections.multi.Lsp;
import tools.scanner.Scan;
import tools.tuple.SP;
import tools.tuple.Pos;

public class ScanLsp {
	public static Lsp read() { return read(Scan.readInt()); }

	public static Lsp read(int size) {
		Lsp l = new Lsp();
		for (int i = 0; i < size; i++) l.add(new SP(Scan.readString(),
				new Pos(Integer.parseInt(Scan.readString()), Integer.parseInt(Scan.readString()))));
		return l;
	}

	public static Lsp readRaw() {
		Lsp l = new Lsp();
		try {
			String[] vals;
			while ((vals = Scan.readLine().strip().split("\\s+")).length != 0) {
				for (int i = 0; i < vals.length; i += 2) l.add(new SP(vals[i], new Pos(vals[i + 1])));
			}
		} catch (Throwable t) {}
		return l;
	}

	public static Lsp readLine() {
		Lsp l = new Lsp();
		String[] vals = Scan.readLine().strip().split("\\s+");
		for (int i = 0; i < vals.length; i += 2)
			l.add(new SP(vals[i], new Pos(vals[i + 1])));
		return l;
	}
}
