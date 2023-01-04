package tools.enumeration;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tools.enumeration.any.MixAnyNoOrder;
import tools.enumeration.permutations.MixPermutations;

public class _Debug_Enumeration {
	public static void main(String[] args) {
		var mix = new MixAnyNoOrder<String>(List.of("A", "B", "C", "D"));
		Set<String> used = new HashSet<>();
		int i = 0;
		for (var l : mix) {
			String s = l.toString();
			System.out.println(s + (used.contains(s) ? " *" : ""));
			Collections.reverse(l);
			used.add(l.toString());
			i++;
		}
		System.out.println(i);
	}
}
