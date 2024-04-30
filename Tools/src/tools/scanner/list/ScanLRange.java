package tools.scanner.list;

import tools.collections.int32.L;
import tools.collections.segment.LInterval;
import tools.scanner.Scan;
import tools.tuple.Interval;

public class ScanLRange {
	public static LInterval read() { return read(Scan.readInt()); }
	public static LInterval read(int size) { 
		LInterval l = new LInterval();
		for (int i = 0; i < size; i++) l.add(new Interval(Scan.readLong(), Scan.readLong()));
		return l;
	}
	public static LInterval readRaw() {
		LInterval l = new LInterval();
		try {
			long[] vals;
			while ((vals = Scan.readLongLine()).length != 0) l.add(new Interval(vals[0], vals[1]));
		} catch (Throwable t) {}
		return l;
	}
	
	public static LInterval readTimeIntervals(int size) {
		LInterval lr = new LInterval();
		for (int i = 0; i < size; i++) {
			L l = ScanL.readLine();
			lr.add(l.get(0) * 60 + l.get(1), l.get(2) * 60 + l.get(3));
		}
		return lr;
	}
}
