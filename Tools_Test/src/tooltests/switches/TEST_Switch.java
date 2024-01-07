package tooltests.switches;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tools.chrono.Chrono;
import tools.collections.bool.Lb;
import tools.enumeration.gray.Gray;
import tools.mapper.MapLb;
import tools.switches.Switch;
import tools.tuple.LI;

public class TEST_Switch {
	
	private static long l;
	private static final String[] SWITCHES_DEF = {
			"000011000100101111110010101111",
			"100001000101111010010000111011",
			"100110010111010100100100010110",
			"111100010000010110011100010110",
			"000100001101001001111111011011",
			"001001100011011001001010000110",
			"101101110110001110111100101011",
			"101101001000111101000010101101",
			"011111001111100001101011001010",
			"100000010010001001010100100111",
			"011101100111101011111101101111",
			"000100001101000010010110011111",
			"111100111111111010100000011001",
			"010010001001110111001010010010",
			"101001000100011011111101111010",
			"011100111101010000101110110101",
			"110101011101010001011010101101",
			"010001001000011001101111010010",
			"000110011100111011001001001101",
			"100011101111011011100011111100",
			"111100010000010000001000111000",
			"101010000001100101011100010000",
			"010000010001100001111101001100",
			"010110111110100111100100101010",
			"110001101000100000010100101011",
			"110010101110011100111011001000",
			"101111111101101110000010101100",
			"001100111100101110101010111111",
			"110000000100110000000110100110",
			"111000101100011001011000101011",
			"001000101011000110110100110011",
			"001001101100101010110100100001",
			"101101001001101011110010110001",
			"000111111101011100000001111111",
			"010110101101100000100000010010",
			"101111000101001100000010101110",
			"110100101110111011101111001001",
			"111000111110110010110000000010",
			"101101110111100100010100110001",
			"010111010110111000110111111010",
			"011111000000110010110100111100",
			"000110101010101111111001010001",
			"100100010001011000001010010110",
			"101101010101010011000101001110",
			"011100111111110011010111100010",
			"110100011010111000110100100001",
			"101011001110100110110111110100",
			"001000010100000111011111111101",
			"011110111000101100111111101011",
			"010101010011010111111000000010",
			"001101101101111101001000100111",
			"001100011101111001000110011111",
			"101000111101110111110101111000",
			"000101010111101000001101111101",
			"010110111110111011010010100000",
			"100110110110101011100110000011",
			"001101100110110010000001001000",
			"110011001111001000000110110001",
			"100011001001111100000001001011",
			"001100100100010110010010001010"
	};
	
	public static void main(String[] args) {
		
		List<Lb> switches = new ArrayList<>();
		for (String s: SWITCHES_DEF) switches.add(new Lb(s));
		int nSwitches = switches.size();
		
		// Recherche d'une solution
		Lb solution = Switch.lightAll(switches);
		long solAny = solution.asLong();
		System.out.println("Solution: " + solution);
		System.out.println("Vérification: " + Switch.play(switches, solution));
		System.out.println();
		
		// Recherche des zéros
		List<Lb> zeroList = Switch.retrieveZeroSeeds(switches);
		int nZeros = zeroList.size();
		System.out.println(zeroList);
		System.out.println("Vérification:");
		for (int i = 0; i < zeroList.size(); i++) System.out.println(i + ": " + Switch.play(switches, zeroList.get(i)));
		System.out.println();
		
		// Gray
		long[] zeros = MapLb.toLl(zeroList).array();
		Chrono.start();
		l = solAny;
		Lb test = new Lb(solAny, nSwitches);
		System.out.println("Vérif init: " + Switch.play(switches, test) + "\n");
		LI sol = Gray.calc(nZeros, () -> -Long.bitCount(l), li -> {
			Lb selectedZeroSwitches = Switch.play(zeroList, new Lb(li.a, nZeros).reversed()); // = new Lb(l^solAny, nSwitches);
			System.out.println("Combo zéro: " + selectedZeroSwitches);
			System.out.println("Vérif combo zéro: " + Switch.play(switches, selectedZeroSwitches));
			Lb sw1 = Lb.xor(selectedZeroSwitches, solution);
			System.out.println("Vérif lumières: " + Switch.play(switches, sw1));
			System.out.println("Vérif inters: calculés=" + -li.b + ", réels=" + sw1.countTrue());
			System.out.println("Solution: " + sw1 + "\n");
		}, b -> l ^= zeros[b]);
		Chrono.stop();
		System.out.println("------------------------");
		System.out.println("-- Meilleure solution --");
		System.out.println("------------------------");
		Lb selectedZeroSwitches = Switch.play(zeroList, new Lb(sol.a, nZeros).reversed());
		System.out.println("Combo zéro: " + selectedZeroSwitches);
		System.out.println("Vérif combo zéro: " + Switch.play(switches, selectedZeroSwitches));
		Lb sw1 = Lb.xor(selectedZeroSwitches, solution);
		System.out.println("Vérif lumières: " + Switch.play(switches, sw1));
		System.out.println("Vérif inters: calculés=" + -sol.b + ", réels=" + sw1.countTrue());
		System.out.println("Solution: " + sw1 + "\n");
		
		// Fast Gray
		Chrono.start();
		l = solAny;
		long zeros0 = zeros[0];
		long zeros1 = zeros[0];
		long zeros2 = zeros[0];
		long zeros3 = zeros[0];
		long zeros4 = zeros[0];
		long zeros5 = zeros[0];
		Lb test2 = new Lb(solAny, nSwitches);
		System.out.println("Vérif init: " + Switch.play(switches, test2) + "\n");
		LI sol2 = Gray.fastCalc(nZeros, () -> -Long.bitCount(l), li -> {
			Lb selectedZeroSwitches2 = Switch.play(zeroList, new Lb(li.a, nZeros).reversed()); // = new Lb(l^solAny, nSwitches);
			System.out.println("Combo zéro: " + selectedZeroSwitches2);
			System.out.println("Vérif combo zéro: " + Switch.play(switches, selectedZeroSwitches2));
			Lb sw2 = Lb.xor(selectedZeroSwitches2, solution);
			System.out.println("Vérif lumières: " + Switch.play(switches, sw2));
			System.out.println("Vérif inters: calculés=" + -li.b + ", réels=" + sw2.countTrue());
			System.out.println("Solution: " + sw2 + "\n");
		}, 
				() -> l ^= zeros0,
				() -> l ^= zeros1,
				() -> l ^= zeros2,
				() -> l ^= zeros3,
				() -> l ^= zeros4,
				() -> l ^= zeros5,
				b -> l ^= zeros[b]);
		Chrono.stop();
		System.out.println("------------------------");
		System.out.println("-- Meilleure solution --");
		System.out.println("------------------------");
		Lb selectedZeroSwitches2 = Switch.play(zeroList, new Lb(sol2.a, nZeros).reversed());
		System.out.println("Combo zéro: " + selectedZeroSwitches2);
		System.out.println("Vérif combo zéro: " + Switch.play(switches, selectedZeroSwitches2));
		Lb sw2 = Lb.xor(selectedZeroSwitches2, solution);
		System.out.println("Vérif lumières: " + Switch.play(switches, sw2));
		System.out.println("Vérif inters: calculés=" + -sol2.b + ", réels=" + sw2.countTrue());
	}

	public static void generate() {
		for (int i = 0; i < 60; i++) {
			System.out.print("\t\t\"");
			for (int j = 0; j < 30; j++) {
				System.out.print(new Random().nextInt(2));
			}
			System.out.println("\",");
		}
	}
}
