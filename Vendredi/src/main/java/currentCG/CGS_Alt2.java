package currentCG;

import java.util.ArrayList;
import java.util.List;

import tools.collections.multi.Lsi;
import tools.collections.string.Ls;
import tools.output.Out;
import tools.scanner.Scan;
import tools.tuple.SI;

public class CGS_Alt2 {
	private static final int MAX = 20;
	private static Ls atoms = new Ls();
	private static int molNb;
	
	public static void main(String[] args) {
		String s = Scan.readLine();
		String[] t = s.split(" -> ");
		String[] tIn = t[0].split(" \\+ ");
		String[] tOut = t[1].split(" \\+ ");
		List<Lsi> in = new ArrayList<>();
		for (String mol: tIn) put(in, mol);
		List<Lsi> out = new ArrayList<>();
		for (String mol: tOut) put(out, mol);
		double[][] coefs = new double[atoms.size()][in.size() + out.size()];
		for (int i = 0; i < in.size(); i++) {
			Lsi lsi = in.get(i);
			for (int j = 0; j < lsi.size(); j++) coefs[atoms.indexOf(lsi.get(j).s)][i] = lsi.get(j).i;
		}
		for (int i = 0; i < out.size(); i++) {
			Lsi lsi = out.get(i);
			for (int j = 0; j < lsi.size(); j++) coefs[atoms.indexOf(lsi.get(j).s)][in.size() + i] = -lsi.get(j).i;
		}
		molNb = in.size() + out.size();
		int[] res = recurse(0, coefs, new int[molNb]);
		for (int i = 0; i < in.size(); i++) {
			if (i > 0) Out.space("+");
			Out.space((res[i] > 1 ? "" + res[i] : "") + tIn[i]);
		}
		Out.space("->");
		for (int i = 0; i < out.size(); i++) {
			if (i > 0) Out.space("+");
			Out.space((res[i + tIn.length] > 1 ? "" + res[i + tIn.length] : "") + tOut[i]);
		}
		Out.flushln();
	}

	private static int[] recurse(int depth, double[][] coefs, int[] test) {
		if (depth == molNb) {
			for (int i = 0; i < coefs.length; i++) {
				int sum = 0;
				for (int j = 0; j < coefs[i].length; j++) sum += coefs[i][j] * test[j];
				if (sum != 0) return null;
			}
			return test;
		}
		for (int i = 1; i < MAX; i++) {
			test[depth] = i;
            int[] res = recurse(depth + 1, coefs, test);
            if (res != null) return res;
		}
		return null;
	}

	private static void put(List<Lsi> formula, String mol) {
		Lsi lsi = new Lsi();
		while (!mol.isEmpty()) {
			String atom = mol.replaceAll("^([A-Z][a-z]?)\\d*.*", "$1");
			String card = mol.replaceAll("^[A-Z][a-z]?(\\d*).*", "$1");
			mol = mol.substring(atom.length() + card.length());
			if (!atoms.contains(atom)) atoms.add(atom);
			lsi.add(new SI(atom, card.isEmpty() ? 1 : Integer.parseInt(card)));
		}
		formula.add(lsi);
	}
}
