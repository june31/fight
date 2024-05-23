package tooltests.reference;

import java.util.stream.Collectors;

import tools.collections.map.Mii;
import tools.reference.Marienbad;
import tools.scanner.Scan;
import tools.scanner.list.ScanL;
import tools.strings.S;

// https://www.codingame.com/ide/puzzle/misere-nim
public class CGS_MisereNim {
	public static void main(String[] args) {
		int n = Scan.readInt();
		for (int z = 0; z < Scan.readOnce(); z++) {
			Mii m = Marienbad.solve(ScanL.read(n));
			if (m.isEmpty()) S.o("CONCEDE");
			else S.o(m.entrySet().stream()
					.map(e -> (e.getKey() + 1) + ":" + e.getValue())
					.collect(Collectors.joining(" ")));
		}
	}
}
