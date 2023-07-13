package tooltests.dfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import tools.dfs.DFSPermutation;
import tools.math.Num;
import tools.misc.Copyable;
import tools.scanner.Scan;
import tools.tables.Table;

public class AoC_16_ProboscideaVolcanum {

	static Map<String, Valve> namesToValves = new LinkedHashMap<>();
	static int[][] dists;

	public static void main(String[] args) throws IOException {
		// Read data
		String[] lines = Scan.readRawStrings();
		for (String line: lines) namesToValves.put(line.substring(6, 8), new Valve());
		int n = 0;
		for (Valve valve: namesToValves.values()) {
			valve.id = n;
			String line = lines[n++].substring(23);
			String[] tokens = line.split("; tunnels? leads? to valves? ");
			valve.flow = Integer.parseInt(tokens[0]);
			Table.forEach(tokens[1].split(", "), conn -> valve.conns.add(namesToValves.get(conn)));
		}

		// Create distance table
		Valve[] valves = namesToValves.values().toArray(new Valve[0]);
		dists = new int[n][n];
		Table.fill(dists, 30);
		for (int i = 0; i < n; i++) dists[i][i] = 1; // includes open valve
		for (int t = 0; t < 30; t++)
			Table.forEach(dists, (l, c, d) -> {
				if (d < 29)
					for (Valve v : valves[c].conns)
						if (dists[l][v.id] > d) dists[l][v.id] = d + 1;
			});
		Valve[] workingValves = Table.retainFromValues(valves, v -> v.flow > 0);

		// First star
		System.out.println(mainDFS(workingValves, 30));
		
		// Second star
		System.out.println(Num.max(1 << workingValves.length - 1, i ->
			mainDFS(Table.retainFromIndexes(workingValves, j -> (i*2>>j & 1) == 0), 26)
			+ mainDFS(Table.retainFromIndexes(workingValves, j -> (i*2+1>>j & 1) == 1), 26)).value);
	}

	private static int mainDFS(Valve[] valves, int time) {
		var dfs = new DFSPermutation<Valve, Status>(valves, new Status(time));
		dfs.findNext((s, v) -> {
			s.time -= dists[s.pos][v.id];
			s.pos = v.id;
			s.score += v.flow * s.time;
			return s.time <= 0 ? Integer.MIN_VALUE : s.score;
		});
		return dfs.max < 0 ? 0 : dfs.max;
	}

	static class Valve {
		int flow;
		int id;
		List<Valve> conns = new ArrayList<>();
	}

	public static class Status implements Copyable<Status> {
		int score = 0;
		int time;
		int pos = namesToValves.get("AA").id;
		
		public Status(int t) { time = t; }
		public Status() { }
		@Override
		public void copyTo(Status a) {
			a.score = score;
			a.time = time;
			a.pos = pos;
		}
	}
}
