package training;

import java.util.List;

import tools.enumeration.permutations.MixPermutations;
import tools.scanner.Scan;

public class IsoContest_1 {

	public static void main(String[] args) {
		int a = Scan.readInt();
		int b = Scan.readInt();
		int c = Scan.readInt();
		int d = Scan.readInt();
		int e = Scan.readInt();
		int f = Scan.readInt();
		
		var r = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
		
		for (var l : new MixPermutations<>(r)) {
			if (a == l.get(0) + l.get(1) + l.get(2)
			&& b == l.get(3) + l.get(4) + l.get(5)
			&& c == l.get(6) + l.get(7) + l.get(8)
			&& d == l.get(0) + l.get(3) + l.get(6)
			&& e == l.get(1) + l.get(4) + l.get(7)
			&& f == l.get(2) + l.get(5) + l.get(8)) {
 				System.out.println("" + l.get(0) + l.get(1) + l.get(2) + "\n"
						+ l.get(3) + l.get(4) + l.get(5) + "\n"
						+ l.get(6) + l.get(7) + l.get(8));
				break;
			}
		}
	}
}
