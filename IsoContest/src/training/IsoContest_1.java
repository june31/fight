package training;

import java.util.ArrayList;
import java.util.List;

import tools.scanner.Scan;

public class IsoContest_1 {
	
	public static void main(String[] args) {
		int n = Scan.readInt();
		int v = Scan.readInt();
		M[] ms = new M[v];
		for (int i = 0; i < v; i++) ms[i] = new M(i);
		for (int i = 0; i < n; i++) {
			int a = Scan.readInt();
			int b = Scan.readInt();
			ms[a].children.add(ms[b]);
			ms[b].fathers.add(ms[a]);
		}
		for (M m : ms) if (m.children.isEmpty()) m.leaf = 1;

		int max = -1;
		int sol = 0;
		boolean found = true;
		while (found) {
			found = false;
			for (int i = 0; i < v; i++) {
				M m = ms[i];
				if (m.children.size() - m.done == 0) {
					for (M f : m.fathers) {
						f.leaf += m.leaf;
						f.done++;
						m.done = 99999;
						if (f.leaf > max) {
							max = f.leaf;
							sol = f.id;
						}
						found = true;
					}
				}
			}
		}
		System.out.println(sol);
	}
	
	static class M {
		int c = 0;
		int leaf = 0;
		int done = 0;
		List<M> fathers = new ArrayList<>();
		List<M> children = new ArrayList<>();
		private int id;
		public M(int i) {
			id = i;
		}
		@Override
		public String toString() {
			return id + " " + children.size() + "" + done + " - " + leaf;
		}
	}
}
