package combinatoire;

import java.util.List;

import tools.enumeration.combinations.Arrangements;
import tools.scanner.Scan;

public class Select5OutOf9_ {
	
	public static void main(String[] args) {
		Scan.readString();
		byte[] s = Scan.readString().getBytes();
		for (int i = 0; i < 5; i++) s[i] -= 'a';
		int z = Scan.readInt();
		var nums = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
		int min = 99999;
		for (var l : new Arrangements<>(nums, 5)) {
			int a = l.get(0) * 10000 + l.get(1) * 1000 + l.get(2) * 100 + l.get(3) * 10 + l.get(4);
			int b = l.get(s[0]) * 10000 + l.get(s[1]) * 1000 + l.get(s[2]) * 100 + l.get(s[3]) * 10 + l.get(s[4]);
			if (a + b == z && min > a) min = a; 
		}
		System.out.println(min);
	}
}
