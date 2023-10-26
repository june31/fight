package tools.switches;

import java.util.ArrayList;
import java.util.List;

import tools.tables.Table;

public class Switch {

	public static boolean[] lightAll(boolean[] start, boolean[][] switches) {
		int bulbNb = start.length;
		int switchNb = switches.length;
		if (switches[0].length != bulbNb) throw new IllegalArgumentException("Switches and lights do not match.");

		boolean[] current = Table.copy(start);
		boolean[][] modifiedSwitches = Table.copy(switches);
		boolean[][] modifiedSwitchTable = new boolean[switchNb][switchNb];
		boolean[] solutionSwitches = new boolean[switchNb]; 
		for (int i = 0; i < switchNb; i++) modifiedSwitchTable[i][i] = true;
		
		for (int iBulb = 0; iBulb < bulbNb; iBulb++) {
			boolean[] selectedSwitch = null;
			int switchIndex = 0;
			while (switchIndex < switchNb && (modifiedSwitches[switchIndex] == null || !modifiedSwitches[switchIndex][iBulb])) switchIndex++;
			if (current[iBulb]) { // Bulb is already lit
				if (switchIndex == switchNb) continue; // No switch tries to turn the bulb off.
				// Found a modified switch that toggles bulb
				selectedSwitch = modifiedSwitches[switchIndex];
				// Do not take it into account as bulb is already lit.
			} else { // Bulb needs to be lit.
				if (switchIndex == switchNb) return null; // This bulb cannot be lit if previous bulbs are lit -> no solution.
				// Found a modified switch that toggles bulb
				selectedSwitch = modifiedSwitches[switchIndex];
				// So take it into account.
				xor(current, selectedSwitch);
				xor(solutionSwitches, modifiedSwitchTable[switchIndex]);
			}
			modifiedSwitches[switchIndex] = null; // Switch is disabled.
			// Subsequent switches are not allowed to toggle this bulb -> xor them all!
			for (int nextIndex = switchIndex + 1; nextIndex < switchNb; nextIndex++)
				if (modifiedSwitches[nextIndex] != null && modifiedSwitches[nextIndex][iBulb]) {
					xor(modifiedSwitches[nextIndex], selectedSwitch);
					xor(modifiedSwitchTable[nextIndex], modifiedSwitchTable[switchIndex]);
				}
		}
		
		return solutionSwitches;
	}

	public static boolean[] lightAll(boolean[][] switches) {
		return lightAll(new boolean[switches[0].length], switches);
	}

	public static boolean[] reach(boolean[] start, boolean[] end, boolean[][] switches) {
		boolean[] nx = new boolean[start.length];
		for (int i = 0; i < nx.length; i++) nx[i] = start[i] == end[i];
		return lightAll(nx, switches);
	}

	public static boolean[] reach(boolean[] end, boolean[][] switches) {
		return reach(new boolean[end.length], switches);
	}
	
	public static boolean[][] retrieveZeroSeeds(boolean[][] switches) {
		int bulbNb = switches[0].length;
		int switchNb = switches.length;

		boolean[][] modifiedSwitches = Table.copy(switches);
		boolean[][] modifiedSwitchTable = new boolean[switchNb][switchNb];
		for (int i = 0; i < switchNb; i++) modifiedSwitchTable[i][i] = true;
		
		for (int iBulb = 0; iBulb < bulbNb; iBulb++) {
			boolean[] selectedSwitch = null;
			int switchIndex = 0;
			while (switchIndex < switchNb && (modifiedSwitches[switchIndex] == null || !modifiedSwitches[switchIndex][iBulb])) switchIndex++;
			if (switchIndex == switchNb) continue; // No switch tries to turn the bulb off.
			// Found a modified switch that toggles bulb
			selectedSwitch = modifiedSwitches[switchIndex];
			// Disable that switch.
			modifiedSwitches[switchIndex] = null;
			// Subsequent switches are not allowed to toggle this bulb -> xor them all!
			for (int nextIndex = switchIndex + 1; nextIndex < switchNb; nextIndex++) {
				if (modifiedSwitches[nextIndex] != null && modifiedSwitches[nextIndex][iBulb]) {
					xor(modifiedSwitches[nextIndex], selectedSwitch);
					xor(modifiedSwitchTable[nextIndex], modifiedSwitchTable[switchIndex]);
				}
			}
		}
		
		// Surviving switches are seeds
		List<boolean[]> allSolutionList = new ArrayList<>();
		for (int i = 0; i < switchNb; i++) if (modifiedSwitches[i] != null) allSolutionList.add(modifiedSwitchTable[i]);
		boolean[][] allSolutions = new boolean[allSolutionList.size()][];
		for (int i = 0; i < allSolutions.length; i++) allSolutions[i] = allSolutionList.get(i);
		
		return allSolutions;
	}
	
	// a = a ^ b
	private static void xor(boolean[] a, boolean[] b) {
		for (int i = 0; i < a.length; i++) a[i] ^= b[i];
	}
}
