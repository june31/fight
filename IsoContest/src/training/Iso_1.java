package training;

import tools.scanner.Scan;

public class Iso_1 {

	public static void main(String[] args) {
		String name = Scan.readString();
		int h = Integer.parseInt(name.split(":")[0]);
		int m = Integer.parseInt(name.split(":")[1]);
		System.out.println(h >= 0 && h <= 23 && m >= 0 && m <= 59 ? "YES" : "NO");
	}
}
