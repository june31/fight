package tools.scanner.list;

import tools.collections.int32.L;
import tools.collections.segment.LRange;
import tools.scanner.Scan;
import tools.tuple.Range;

public class ScanLRange {
	public static LRange read() { return read(Scan.readInt()); }
	public static LRange read(int size) { 
		LRange l = new LRange();
		for (int i = 0; i < size; i++) l.add(new Range(Scan.readLong(), Scan.readLong()));
		return l;
	}
	public static LRange readRaw() {
		LRange l = new LRange();
		try {
			long[] vals;
			while ((vals = Scan.readLongLine()).length != 0) l.add(new Range(vals[0], vals[1]));
		} catch (Throwable t) {}
		return l;
	}
	
	public static LRange readTimeIntervals(int size) {
		LRange lr = new LRange();
		for (int i = 0; i < size; i++) {
			L l = ScanL.readLine();
			lr.add(l.get(0) * 60 + l.get(1), l.get(2) * 60 + l.get(3));
		}
		return lr;
	}
}
