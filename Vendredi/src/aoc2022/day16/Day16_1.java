package aoc2022.day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day16_1 {
	public static void main(String[] args) throws IOException {
		Map<String, V1> namesToV1 = new LinkedHashMap<>();
		int n = 0;
		int start = 0;
		
		try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
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

		V[] superVs = new V[13];
		int sn = 0;
		for (V v : vs) if (v.flow > 0) superVs[sn++] = v;
		
		// Go for DFS
		int max = -1;
		int[] visit = new int[sn];
		int[] times = new int[sn];
		int[] scores = new int[sn];
		int[] useds = new int[sn];
		int depth = 0;
		int current = 0;
		while (depth >= 0) {
			int oldScore;
			int oldTime;
			int oldVisit;
			int used;
			if (depth == 0) {
				oldScore = 0;
				oldTime = 0;
				used = 0;
			} else {
				oldScore = scores[depth - 1];
				oldTime = times[depth - 1];
				used = useds[depth - 1];
			}
			int bit = 1 << current;
			if ((used & bit) == 0) {
				visit[depth] = current;
				int time
				int score = oldScore + superVs[i] * (30 - )
				if (oldScore = 1)
			}
		}
		
		System.out.println();
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

}
