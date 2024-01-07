package tools.scanner.list;

import java.util.ArrayList;
import java.util.List;

import tools.collections.bool.Lb;
import tools.scanner.Scan;

public class ScanLb {
	public static Lb readLine() { return new Lb(Scan.readLine()); }
	public static List<Lb> readRawList() {
		List<Lb> llb = new ArrayList<>();
		try {
			String l;
			while (!(l = Scan.readLine()).isEmpty()) llb.add(new Lb(l));
		} catch (Throwable t) {}
		return llb;
	}
	public static List<Lb> readList() {
		List<Lb> llb = new ArrayList<>();
		int n = Scan.readInt();
		for (int i = 0; i < n; i++) llb.add(new Lb(Scan.readLine()));
		return llb;
	}
}
