package currentISO;

import tools.bfs.BFSGraph;
import tools.collections.map.Msi;
import tools.collections.string.Ls;
import tools.output.Out;
import tools.scanner.Scan;
import tools.scanner.list.ScanLs;
import tools.structures.graph.node.Node;

public class ISO_1 {
	static BFSGraph bfs = new BFSGraph();
	public static void main(String[] args) {
		int n = Scan.readInt();
		Ls l = ScanLs.readLines().mapped(s -> s.replace(" <->", "")
				.replace(" :", "").replace("A", "0").replace("B", "" + (n+1)));
		Msi m = new Msi();
		int[] minsA = new int[n+2];
		int[] minsB = new int[n+2];
		for (int i = 1; i <= n; i++) {
			minsA[i] = Integer.MAX_VALUE;
			minsB[i] = Integer.MAX_VALUE;
		}
		minsA[n+1] = Integer.MAX_VALUE;
		minsB[0] = Integer.MAX_VALUE;
		for (String s: l) {
			String[] t = s.split(" ");
			Node.buildDual(t[0], t[1]);
			int i = Integer.parseInt(t[2]);
			m.put(t[0] + "-" + t[1], i);
			m.put(t[1] + "-" + t[0], i);
		}
		/*
		bfs.diffuse(Node.get("0"), () -> {
			String key = bfs.n1.name + "-" + bfs.n2.name;
			if (!m.containsKey(key))
				return false;
			int v = m.get(key);
			int k1 = Integer.parseInt(bfs.n1.name);
			int k2 = Integer.parseInt(bfs.n2.name);
			if (minsA[k2] > v + minsA[k1])
				minsA[k2] = v + minsA[k1];
			return false;
		});
		bfs.diffuse(Node.get("0"), () -> {
			String key = bfs.n1.name + "-" + bfs.n2.name;
			if (!m.containsKey(key)) return false;
			int v = m.get(key);
			int k1 = Integer.parseInt(bfs.n1.name);
			int k2 = Integer.parseInt(bfs.n2.name);
			if (minsA[k2] > v + minsA[k1]) minsA[k2] = v + minsA[k1];
			return false;
		});
		bfs.diffuse(Node.get("" + (n+1)), () -> {
			String key = bfs.n1.name + "-" + bfs.n2.name;
			if (!m.containsKey(key)) return false;
			int v = m.get(key);
			int k1 = Integer.parseInt(bfs.n1.name);
			int k2 = Integer.parseInt(bfs.n2.name);
			if (minsB[k2] > v + minsB[k1]) minsB[k2] = v + minsB[k1];
			return false;
		});
		*/
		for (int i=1; i <= n; i++) Out.space(minsA[i] + minsB[i]);
		Out.flush();
	}
}
