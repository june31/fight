package currentCG;

import java.util.ArrayList;
import java.util.List;

import tools.collections.multi.Lsi;
import tools.collections.string.Ls;
import tools.math.Num;
import tools.output.Out;
import tools.scanner.Scan;
import tools.solver.Solver;
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
		double[][] coefs = new double[atoms.size() + 1][in.size() + out.size()];
		coefs[0][0] = 1;
		double[] vals = new double[atoms.size() + 1];
		vals[0] = 1;
		for (int i = 0; i < in.size(); i++) {
			Lsi lsi = in.get(i);
			for (int j = 0; j < lsi.size(); j++) coefs[atoms.indexOf(lsi.get(j).s) + 1][i] = lsi.get(j).i;
		}
		for (int i = 0; i < out.size(); i++) {
			Lsi lsi = out.get(i);
			for (int j = 0; j < lsi.size(); j++) coefs[atoms.indexOf(lsi.get(j).s) + 1][in.size() + i] = -lsi.get(j).i;
		}
		molNb = in.size() + out.size();
		
		double[] res = Solver.reduceAndSolve(coefs, vals);
		for (int i = 0; i < res.length; i++) {
			int x = 1;
			while (!Num.isInt(res[i] * x)) x++;
			for (int j = 0; j < res.length; j++) res[j] *= x;
		}
		
		for (int i = 0; i < in.size(); i++) {
			if (i > 0) Out.space("+");
			Out.space((res[i] > 1 ? "" + Math.round(res[i]) : "") + tIn[i]);
		}
		Out.space("->");
		for (int i = 0; i < out.size(); i++) {
			if (i > 0) Out.space("+");
			Out.space((res[i + tIn.length] > 1 ? "" + Math.round(res[i + tIn.length]) : "") + tOut[i]);
		}
		Out.flushln();
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
