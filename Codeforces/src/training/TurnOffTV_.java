package training;

import java.util.Arrays;

import tools.scanner.Scan;

public class TurnOffTV_ {
	public static void main(String[] args) {
		int n = Scan.readInt();
		TV[] tvs = new TV[n];
		for (int i = 0; i < n; i++) {
			tvs[i] = new TV(i + 1, Scan.readInt(), Scan.readInt());
		}
		Arrays.sort(tvs, (a, b) -> b.l() - a.l() == 0 ? b.r() - a.r() : a.l() - b.l());
	
		System.out.println(seek(tvs, n));
	}

	private static int seek(TV[] tvs, int n) {
		int r = tvs[0].r();
		for (int i = 1; i < n; i++) {
			if (tvs[i].r() <= r) return tvs[i].id();
			r = tvs[i].r();
		}
		for (int i = 1; i < n - 1; i++) {
			if (tvs[i - 1].r() >= tvs[i + 1].l() - 1) return tvs[i].id();
		}
		return -1;
	}
}

record TV(int id, int l, int r) {}