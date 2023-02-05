package tools.enumeration;

import java.util.List;

import tools.chrono.Chrono;
import tools.enumeration.permutations.FastPermutations;
import tools.enumeration.permutations.MixPermutations;
import tools.scanner.Scan;

public class _Debug_Enumeration {
	public static void main(String[] args) {
		
		int a = 0;
		var mix1 = new MixPermutations<>(List.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"));
		var mix2 = new FastPermutations<>(new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"});
		Chrono.start();
		for (var l : mix1) {
			a += l.size();
		}
		Chrono.stop();
		for (var l : mix2) {
			a += l.length;
		}
		Chrono.stop();
		int p = Scan.readInt();
		int z = Scan.readInt();
		
	}
}
