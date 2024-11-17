package tooltests.bfs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import tools.bfs.BFS2DExt;
import tools.tuple.Pos;

class CGP_BFS2DExt_Transmutation {

	/**
	 * @param protonsStart The initial number of protons
	 * @param neutronsStart The initial number of neutrons
	 * @param protonsTarget The desired number of protons
	 * @param neutronsTarget The desired number of neutrons
	 * @param unstableIsotopes The list of unstable isotopes
	 */
	public static List<String> solve(int ps, int ns, int pt, int nt, List<List<Integer>> walls) {
		int[][] tab = new int[30][20];
		for (List<Integer> wall: walls) tab[wall.get(0)][wall.get(1)] = '#';
		BFS2DExt bfs = new BFS2DExt(tab);
		bfs.wall('#').end(pt, nt).setMoves(b -> List.of(
				() -> { b.c2++; },
				() -> { b.l2++; },
				() -> { b.c2-=2; bfs.l2-=2; }));
		bfs.diffuse(ps, ns);
		List<Pos> track = bfs.shortestPath();
		List<String> res = new ArrayList<>();
		for (int i = 1; i < track.size(); i++) {
			int d = track.get(i).l - track.get(i - 1).l; 
			res.add(d < 0 ? "ALPHA" : d > 0 ? "PROTON" : "NEUTRON");
		}
		return res;
	}

	/* Ignore and do not change the code below */
	private static final Gson gson = new Gson();

	/**
	 * Try a solution
	 */
	public static void trySolution(List<String> recipe) {
		System.out.println("" + gson.toJson(recipe));
	}

	public static void main(String args[]) {
		try (Scanner in = new Scanner(System.in)) {
			trySolution(solve(
					gson.fromJson(in.nextLine(), new TypeToken<Integer>(){}.getType()),
					gson.fromJson(in.nextLine(), new TypeToken<Integer>(){}.getType()),
					gson.fromJson(in.nextLine(), new TypeToken<Integer>(){}.getType()),
					gson.fromJson(in.nextLine(), new TypeToken<Integer>(){}.getType()),
					gson.fromJson(in.nextLine(), new TypeToken<List<List<Integer>>>(){}.getType())
					));
		}
	}
	/* Ignore and do not change the code above */
}
