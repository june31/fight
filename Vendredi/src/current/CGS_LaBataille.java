package current;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import tools.scanner.Scan;
import tools.tables.T;

public class CGS_LaBataille {
	public static void main(String[] args) {
		int[] ih1 = Arrays.stream(Scan.readStringArray()).mapToInt(s -> val(s)).toArray();
		int[] ih2 = Arrays.stream(Scan.readStringArray()).mapToInt(s -> val(s)).toArray();
		
		List<Integer> h1 = new LinkedList<>(T.ints(ih1));
		List<Integer> h2 = new LinkedList<>(T.ints(ih2));
		
		int manches = 0;
		boolean pat = false;
		Game: while (h1.size() > 0 && h2.size() > 0) {
			manches++;
			int c1 = h1.remove(0);
			int c2 = h2.remove(0);
			if (c1 > c2) {
				h1.add(c1);
				h1.add(c2);
			} else if (c2 > c1) {
				h2.add(c1);
				h2.add(c2);
			} else {
				List<Integer> t1 = new ArrayList<>();
				t1.add(c1);
				List<Integer> t2 = new ArrayList<>();
				t2.add(c2);
				while (c1 == c2) {
					if (h1.size() < 4 || h2.size() < 4) {
						pat = true;
						break Game;
					}
					t1.add(h1.remove(0));
					t1.add(h1.remove(0));
					t1.add(h1.remove(0));
					c1 = h1.remove(0);
					t1.add(c1);
					t2.add(h2.remove(0));
					t2.add(h2.remove(0));
					t2.add(h2.remove(0));
					c2 = h2.remove(0);
					t2.add(c2);
					if (c1 > c2) {
						h1.addAll(t1);
						h1.addAll(t2);
					} else if (c2 > c1) {
						h2.addAll(t1);
						h2.addAll(t2);
					}
				}
			}
			
		}
		if (pat) System.out.println("PAT");
		else if (h1.size() == 0) System.out.println("2 " + manches);
		else System.out.println("1 " + manches);
	}
	
	private static int val(String card) {
		if (card.length() == 3) return 10;
		char c = card.charAt(0);
		if (c <= '9') return c - '0';
		if (c == 'J') return 11;
		if (c == 'Q') return 12;
		if (c == 'K') return 13;
		return 14;
	}
}
