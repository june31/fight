package currentCG;

import tools.scanner.Scan;
import tools.tables.Table;

public class CGS_Enigma {
	public static void main(String[] args) {
		boolean encode = Scan.readString().equals("ENCODE");
		int shift = Scan.readInt();
		int[][] rotors = Table.map(Table.toMap(Scan.readStringArray(3)), x -> x - 'A');
		int[] message = Table.map(Table.toIntArray(Scan.readString()), x -> x - 'A');
		int n = message.length;

		if (encode) {
			for (int i = 0; i < n; i++) message[i] = Math.floorMod(message[i] + shift++, 26);
			for (int r = 0; r < 3; r++) for (int i = 0; i < n; i++) message[i] = rotors[r][message[i]];
		}  else {
			for (int r = 2; r >= 0; r--) for (int i = 0; i < n; i++) message[i] = Table.find(rotors[r], message[i]);
			for (int i = 0; i < n; i++) message[i] = Math.floorMod(message[i] - shift++, 26);
		}
		System.out.println(Table.toASCIIString(Table.map(message, x -> x + 'A')));
	}
}
