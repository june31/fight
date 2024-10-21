package currentCG;

import java.util.ArrayList;
import java.util.List;

import tools.collections.map.LinkedMsi;
import tools.scanner.Scan;

public class CGS_Alt {
	public static void main(String[] args) {
		String s = Scan.readLine();
		String[] t = s.split(" -> ");
		List<LinkedMsi> in = new ArrayList<>();
		for (String m: t[0].split(" \\+ ")) put(in, m);
		List<LinkedMsi> out = new ArrayList<>();
		for (String m: t[1].split(" \\+ ")) put(out, m);
	}

	private static void put(List<LinkedMsi> formula, String mol) {
		LinkedMsi msi = new LinkedMsi();
		while (!mol.isEmpty()) {
			String atom = mol.replaceAll("^([A-Z][a-z]?)\\d*.*", "$1");
			String card = mol.replaceAll("^[A-Z][a-z]?(\\d*).*", "$1");
			mol = mol.substring(atom.length() + card.length());
			msi.put(atom, card.isEmpty() ? 1 : Integer.parseInt(card));
		}
		formula.add(msi);
	}
}
