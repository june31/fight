package tools.enumeration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tools.enumeration.combinations.MixCyclicArrangements;
import tools.enumeration.combinations.MixSymmetricArrangements;

public class _Debug_Enumeration {
	public static void main(String[] args) {
		var mix1 = new MixSymmetricArrangements<String>(List.of("A", "B", "C", "D", "E", "F", "G", "H"), 3);
		var mix2 = new MixCyclicArrangements<String>(List.of("A", "B", "C", "D", "E", "F", "G", "H"), 3);
		Set<String> used = new HashSet<>();
		int i = 0;
		for (var l : mix1) {
			System.out.println(l);
			used.add(l.toString());
			i++;
		}
		System.out.println(i);
		i = 0;
		for (var l : mix2) {
			if (!used.contains(l.toString())) System.out.println(l);;
			i++;
		}
		System.out.println(i);
	}
	
	static long f(int x) {
		long m = 1;
		for (int i = 2; i <= x; i++) m *= i;
		return m;
	}
}
