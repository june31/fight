package tools.scanner.list;

import tools.collections.segment.LSegment;
import tools.scanner.Scan;
import tools.tuple.Segment;

public class ScanLSegment {
	public static LSegment read() { return read(Scan.readInt()); }
	public static LSegment read(int size) { 
		LSegment l = new LSegment();
		for (int i = 0; i < size; i++) l.add(new Segment(Scan.readInt(), Scan.readInt()));
		return l;
	}
	public static LSegment readRaw() {
		LSegment l = new LSegment();
		try {
			int[] vals;
			while ((vals = Scan.readIntLine()).length != 0) l.add(new Segment(vals[0], vals[1]));
		} catch (Throwable t) {}
		return l;
	}
}
