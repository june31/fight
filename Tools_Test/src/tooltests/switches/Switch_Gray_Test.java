package tooltests.switches;

import java.util.List;
import java.util.Random;

import tools.chrono.Chrono;
import tools.collections.bool.Lb;
import tools.enumeration.gray.Gray;
import tools.mapper.MapLb;
import tools.scanner.list.ScanLb;
import tools.switches.Switch;
import tools.tuple.LI;

public class Switch_Gray_Test {
	
	private static long l;

	public static void main(String[] args) {
		List<Lb> switches = ScanLb.readRawList();
		int nSwitches = switches.size();
		
		// Recherche d'une solution
		Lb solution = Switch.lightAll(switches);
		System.out.println("Solution: " + solution);
		System.out.println("V�rification: " + Switch.play(switches, solution));
		System.out.println();
		long solAny = solution.asLong();
		
		// Recherche des z�ros
		List<Lb> zeroList = Switch.retrieveZeroSeeds(switches);
		int nZeros = zeroList.size();
		System.out.println(zeroList);
		System.out.println("V�rification:");
		for (int i = 0; i < zeroList.size(); i++) System.out.println(i + ": " + Switch.play(switches, zeroList.get(i)));
		System.out.println();
		
		// Gray
		long[] zeros = MapLb.toLl(zeroList).array();
		Chrono.start();
		l = solAny;
		Lb test = new Lb(solAny, nSwitches);
		System.out.println("V�rif init: " + Switch.play(switches, test) + "\n");
		LI sol = Gray.calc(nZeros, () -> -Long.bitCount(l), li -> {
			Lb selectedZeroSwitches = Switch.play(zeroList, new Lb(li.l, nZeros).reversed()); // = new Lb(l^solAny, nSwitches);
			System.out.println("Combo z�ro: " + selectedZeroSwitches);
			System.out.println("V�rif combo z�ro: " + Switch.play(switches, selectedZeroSwitches));
			Lb sw1 = Lb.xor(selectedZeroSwitches, solution);
			System.out.println("V�rif lumi�res: " + Switch.play(switches, sw1));
			System.out.println("V�rif inters: calcul�s=" + -li.i + ", r�els=" + sw1.countTrue());
			System.out.println("Solution: " + sw1 + "\n");
		}, b -> l ^= zeros[b]);
		Chrono.stop();
		System.out.println("------------------------");
		System.out.println("-- Meilleure solution --");
		System.out.println("------------------------");
		Lb selectedZeroSwitches = Switch.play(zeroList, new Lb(sol.l, nZeros).reversed());
		System.out.println("Combo z�ro: " + selectedZeroSwitches);
		System.out.println("V�rif combo z�ro: " + Switch.play(switches, selectedZeroSwitches));
		Lb sw1 = Lb.xor(selectedZeroSwitches, solution);
		System.out.println("V�rif lumi�res: " + Switch.play(switches, sw1));
		System.out.println("V�rif inters: calcul�s=" + -sol.i + ", r�els=" + sw1.countTrue());
		System.out.println("Solution: " + sw1 + "\n");
		
		// Fast Gray
		Chrono.start();
		l = solAny;
		long zeros0 = zeros[0];
		long zeros1 = zeros[1];
		long zeros2 = zeros[2];
		long zeros3 = zeros[3];
		long zeros4 = zeros[4];
		long zeros5 = zeros[5];
		Lb test2 = new Lb(solAny, nSwitches);
		System.out.println("V�rif init: " + Switch.play(switches, test2) + "\n");
		LI sol2 = Gray.fastCalc(nZeros, () -> -Long.bitCount(l), li -> {
			Lb selectedZeroSwitches2 = Switch.play(zeroList, new Lb(li.l, nZeros).reversed()); // = new Lb(l^solAny, nSwitches);
			System.out.println("Combo z�ro: " + selectedZeroSwitches2);
			System.out.println("V�rif combo z�ro: " + Switch.play(switches, selectedZeroSwitches2));
			Lb sw2 = Lb.xor(selectedZeroSwitches2, solution);
			System.out.println("V�rif lumi�res: " + Switch.play(switches, sw2));
			System.out.println("V�rif inters: calcul�s=" + -li.i + ", r�els=" + sw2.countTrue());
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
		Lb selectedZeroSwitches2 = Switch.play(zeroList, new Lb(sol2.l, nZeros).reversed());
		System.out.println("Combo z�ro: " + selectedZeroSwitches2);
		System.out.println("V�rif combo z�ro: " + Switch.play(switches, selectedZeroSwitches2));
		Lb sw2 = Lb.xor(selectedZeroSwitches2, solution);
		System.out.println("V�rif lumi�res: " + Switch.play(switches, sw2));
		System.out.println("V�rif inters: calcul�s=" + -sol2.i + ", r�els=" + sw2.countTrue());
	}

	public static void generate() {
		for (int i = 0; i < 60; i++) {
			for (int j = 0; j < 30; j++) System.out.print(new Random().nextInt(2));
			System.out.println();
		}
	}
}
