package tools.scanner.list;

import tools.collections.map.Msi;
import tools.scanner.Scan;
import tools.strings.S;

public class ScanMsi {
	public static Msi read() { return read(Scan.readInt()); }
	
	public static Msi read(int size) { 
		Msi m = new Msi();
		for (int i = 0; i < size; i++) m.put(Scan.readString(), Scan.readInt());
		return m;
	}
	
	public static Msi readInverse() { return readInverse(Scan.readInt()); }
	
	public static Msi readInverse(int size) { 
		Msi m = new Msi();
		for (int i = 0; i < size; i++) {
			int x = Scan.readInt();
			m.put(Scan.readString(), x);
		}
		return m;
	}
	
	public static Msi readRaw() {
		Msi m = new Msi();
		try {
			String[] vals;
			while ((vals = S.splitLast(Scan.readLine(), "\\s+")).length != 0) {
				m.put(vals[0], Integer.parseInt(vals[1]));
			}
		} catch (Throwable t) {}
		return m;
	}
	
	public static Msi readInverseRaw() {
		Msi m = new Msi();
		try {
			String[] vals;
			while ((vals = Scan.readLine().split("\\s+", 2)).length != 0) {
				m.put(vals[1], Integer.parseInt(vals[0]));
			}
		} catch (Throwable t) {}
		return m;
	}
}
