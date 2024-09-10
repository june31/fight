package summer;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import tools.Scanner;

class WLMutant {
	@SerializedName("mutant")
	public int mutant;
	@SerializedName("squad")
	public String squad;
	@SerializedName("priority")
	public int priority;
}

class Friendship {
	@SerializedName("friend1")
	public int friend1;
	@SerializedName("friend2")
	public int friend2;
	@SerializedName("strength")
	public int strength;
}

public class CGS_7 {

	/* Ignore and do not change the code below */
	private static final Gson gson = new Gson();

	/**
	 * Try a solution
	 * @param maxPossibleScore The best possible score across all configurations
	 */
	public static void trySolution(int maxPossibleScore) {
		System.out.println("" + gson.toJson(maxPossibleScore));
	}

	public static void main(String args[]) {
		try (Scanner in = new Scanner(System.in)) {
			trySolution(maxScore(
					gson.fromJson(in.nextLine(), new TypeToken<Integer>(){}.getType()),
					gson.fromJson(in.nextLine(), new TypeToken<Map<Integer, Integer>>(){}.getType()),
					gson.fromJson(in.nextLine(), new TypeToken<Map<Integer, Integer>>(){}.getType()),
					gson.fromJson(in.nextLine(), new TypeToken<List<WLMutant>>(){}.getType()),
					gson.fromJson(in.nextLine(), new TypeToken<List<Friendship>>(){}.getType())
					));
		}
	}

	public static int maxScore(int n, Map<Integer, Integer> wym, Map<Integer, Integer> wzm, List<WLMutant> wml, List<Friendship> fl) {
		int[] wys = new int[n];
		for (var e: wym.entrySet()) wys[e.getKey()] = (int) e.getValue();
		int[] wzs = new int[n];
		for (var e: wzm.entrySet()) wzs[e.getKey()] = (int) e.getValue();
		for (var w: wml) if (w.squad.charAt(0) == 'Y') wys[w.mutant] += w.priority; else wzs[w.mutant] += w.priority;
		boolean[] ys = new boolean[n];

		Collections.sort(fl, (f1, f2) -> Integer.compare(f2.strength, f1.strength));
		int maxN = 1 << 18;
		int max = 0;
		for (int mask = 0; mask < maxN; mask++) {
			int score = 0;
			for (int i = 0; i < n; i++) {
				if ((mask & 1 << i) == 0) {
					score += wys[i];
					ys[i] = true;
				} else {
					score += wzs[i];
					ys[i] = false;
				}
			}
			for (Friendship f : fl) {
				if (ys[f.friend1] == ys[f.friend2]) {
					score += f.strength;
				}
			}
			if (max < score) max = score;
		}
		
		return max;
	}
}

