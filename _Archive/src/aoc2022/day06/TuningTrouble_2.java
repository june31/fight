package aoc2022.day06;

import java.util.ArrayList;
import java.util.List;

import tools.scanner.Scan;

public class TuningTrouble_2 {
	public static void main(String[] args) {
		byte[] in = Scan.readString().getBytes();
		L : for (int i = 0;; i++) {
			List<Byte> l = new ArrayList<>();
			for (int j = 0; j < 14; j++) {
				if (l.contains(in[i + j])) continue L;
				l.add(in[i + j]);
			}
			System.out.println(i + 14);
			return;
		}
	}
}
