package tooltests.collections.multiple;

import tools.collections.multi.Lsi;
import tools.tuple.SI;

public class LSITest {
	
	public static void main(String[] args) {
		Lsi l = new Lsi();
		l.add(new SI("c", 1));
		l.add(new SI("b", 2));
		l.add(new SI("c", 3));
		l.add(new SI("d", 4));
		l.add(new SI("e", 5));
		System.out.println(l.integers());
		System.out.println(l.strings());
		System.out.println(l.filtered(si -> si.i > 2));
		System.out.println(l.sortedAlpha());
		System.out.println(l.sortedUp());
		System.out.println(l.added());
	}
}
