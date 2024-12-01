package tools.scanner.list;

import java.util.Arrays;
import java.util.stream.Collectors;

import tools.collections.int32.L;
import tools.scanner.Scan;

public class ScanL {
	public static L readLine() { return new L(Scan.readLine()); }
	public static L readBinaryLine() { return Arrays.stream(Scan.readLine().strip().split("\\s+")).map(s -> Integer.parseInt(s, 2)).collect(Collectors.toCollection(L::new)); }
	public static L readHexLine() { return Arrays.stream(Scan.readLine().strip().split("\\s+")).map(s -> Integer.parseInt(s, 16)).collect(Collectors.toCollection(L::new)); }
	public static L readChars() { return new L(Scan.readLine().toCharArray()); }
	public static L readCharsInString() { return new L(Scan.readString().toCharArray()); }
	public static L read() { return read(Scan.readInt()); }
	public static L read(int size) { 
		L l = new L();
		for (int i = 0; i < size; i++) l.add(Scan.readInt());
		return l;
	}
	public static L readBinary(int size) { 
		L l = new L();
		for (int i = 0; i < size; i++) l.add(Scan.readBinary());
		return l;
	}
	public static L readHex(int size) { 
		L l = new L();
		for (int i = 0; i < size; i++) l.add(Scan.readHex());
		return l;
	}
	public static L readRaw() {
		L li = new L();
		try {
			String l;
			while (!(l = Scan.readLine()).isEmpty()) li.addAll(new L(l));
		} catch (Throwable t) {}
		return li;
	}
}
