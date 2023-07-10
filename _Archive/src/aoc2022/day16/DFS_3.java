package aoc2022.day16;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import tools.chrono.Chrono;
import tools.dfs.DFSPermutation;
import tools.misc.Copyable;
import tools.scanner.Scan;

public class DFS_3 {
	static int start = 0;
	
	public static void main(String[] args) throws IOException {
		Chrono.start();
		Map<String, V1> namesToV1 = new LinkedHashMap<>();
		int n = 0;
		
		String[] lines = Scan.readRawStrings();
		for (String line: lines) {
			String name = line.substring(6, 8);
			if (name.equals("AA")) start = n;
			V1 v1 = new V1();
			v1.id = n;
			line = line.substring(23);
			String[] toks = line.split("; tunnels? leads? to valves? ");
			v1.flow = Integer.parseInt(toks[0]);
			String[] conns = toks[1].split(", ");
			for (String c : conns) v1.connections.add(c);
			namesToV1.put(name, v1);
			n++;
		}
		
		V[] vs = new V[n];
		int count = 0;
		for (var e : namesToV1.entrySet()) {
			V v = new V();
			v.name = e.getKey();
			v.id = e.getValue().id;
			v.flow = e.getValue().flow;
			vs[count++] = v;
		}
		
		for (V v : vs) {
			V1 v1 = namesToV1.get(v.name);
			for (String s : v1.connections) v.con.add(vs[namesToV1.get(s).id]);
		}

		int[] dists = new int[n * n];
		for (int i = 0; i < n*n; i++) dists[i] = 99;
		for (int i = 0; i < n; i++) dists[i*n + i] = 1; // includes open valve
		for (int t = 0; t < 30; t++) {
			for (int i = 0; i < n * n; i++) {
				int d = dists[i];
				if (d < 29) {
					int l = i/n, c = i%n;
					V v = vs[c];
					for (V v2 : v.con) {
						int dest = dists[l * n + v2.id];
						if (dest > d) dists[l * n + v2.id] = d + 1;
					}
				}
			}
		}

		V[] superVs = new V[15];
		int sn = 0;
		for (V v : vs) if (v.flow > 0) superVs[sn++] = v;
		
		// Go for DFS
		var dfs = new DFSPermutation<V, Status>(superVs, new Status());
		int fn = n;
		dfs.findNext((s, v) -> {
			s.time -= dists[s.pos * fn + v.id];
			s.pos = v.id;
			s.score += v.flow * s.time;
			return s.time <= 0 ? Integer.MIN_VALUE : s.score;
		});
		System.out.println(dfs.max);

		Chrono.stop();
	}
	
	static class V1 {
		int flow;
		int id;
		List<String> connections = new ArrayList<>();
	}
	
	static class V {
		String name;
		int flow;
		int id;
		List<V> con = new ArrayList<>();
		
		@Override
		public String toString() {
			String s = con.stream().map(x -> x.name).collect(Collectors.joining(", "));
			return name + '(' + flow + ") -> " + s;
		}
	}

	public static class Status implements Copyable<Status> {
		int score = 0;
		int time = 30;
		int pos = start;
		@Override
		public void copyTo(Status a) {
			a.score = score;
			a.time = time;
			a.pos = pos;
		}
	}
}
