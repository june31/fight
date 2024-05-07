package training;

import tools.collections.map.Mii;
import tools.scanner.list.ScanL;
import tools.strings.S;

public class Iso_1 {
	public static void main(String[] args) {
		S.o(new Mii(ScanL.readLine()).max().get(0).getValue() <= 2);
	}
}
