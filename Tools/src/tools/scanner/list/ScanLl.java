package tools.scanner.list;

import java.util.Arrays;
import java.util.stream.Collectors;

import tools.collections.int32.L;
import tools.collections.int64.Ll;
import tools.scanner.Scan;

public class ScanLl {
	public static Ll readLine() { return new Ll(Scan.readLine()); }
	public static Ll readBinaryLine() { return Arrays.stream(Scan.readLine().split("\\s+")).map(s -> Long.parseLong(s, 2)).collect(Collectors.toCollection(Ll::new)); }
	public static Ll readHexLine() { return Arrays.stream(Scan.readLine().split("\\s+")).map(s -> Long.parseLong(s, 16)).collect(Collectors.toCollection(Ll::new)); }
	public static Ll read() { return read(Scan.readInt()); }
	public static Ll read(int size) { 
		Ll l = new Ll();
		for (int i = 0; i < size; i++) l.add(Scan.readLong());
		return l;
	}
	public static Ll readBinary(int size) { 
		Ll l = new Ll();
		for (int i = 0; i < size; i++) l.add(Scan.readBinaryLong());
		return l;
	}
	public static Ll readHex(int size) { 
		Ll l = new Ll();
		for (int i = 0; i < size; i++) l.add(Scan.readHexLong());
		return l;
	}
	public static Ll readRaw() {
		Ll ll = new Ll();
		try {
			String l;
			while (!(l = Scan.readLine()).isEmpty()) ll.addAll(new Ll(l));
		} catch (Throwable t) {}
		return ll;
	}
}
