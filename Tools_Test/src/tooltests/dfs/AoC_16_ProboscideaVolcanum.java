package tooltests.dfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import tools.chrono.Chrono;
import tools.dfs.DFSPermutation;
import tools.misc.Copyable;
import tools.scanner.Scan;
import tools.tables.Table;

public class AoC_16_ProboscideaVolcanum {

	static final int TIME = 30;
	static Map<String, Valve> namesToValves = new LinkedHashMap<>();

	public static void main(String[] args) throws IOException {
		Chrono.start();

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
		
		// Create 
		Valve[] valves = namesToValves.values().toArray(new Valve[0]);
		int[][] dists = new int[n][n];
		Table.fill(dists, TIME);
		for (int i = 0; i < n; i++) dists[i][i] = 1; // includes open valve
		Table.forEach(dists, (l, c, d) -> {
			if (d < TIME - 1)
				for (Valve v : valves[c].conns)
					if (dists[l][v.id] > d) dists[l][v.id] = d + 1;
		});

		// Main DFS: check all permutations
		Valve[] workingValves = Table.retainToList(valves, v -> v.flow > 0).toArray(new Valve[0]);
		var dfs = new DFSPermutation<Valve, Status>(workingValves, new Status());
		dfs.findNext((s, v) -> {
			s.time -= dists[s.pos][v.id];
			s.pos = v.id;
			s.score += v.flow * s.time;
			return s.time <= 0 ? Integer.MIN_VALUE : s.score;
		});
		System.out.println(dfs.max);

		Chrono.stop();
	}
	
	static class Valve {
		int flow;
		int id;
		List<Valve> conns = new ArrayList<>();
	}

	public static class Status implements Copyable<Status> {
		int score = 0;
		int time = 30;
		int pos = namesToValves.get("AA").id;
		@Override
		public void copyTo(Status a) {
			a.score = score;
			a.time = time;
			a.pos = pos;
		}
	}
}
