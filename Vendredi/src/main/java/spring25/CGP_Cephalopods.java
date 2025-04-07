package spring25;

import tools.scanner.Scan;

public class CGP_Cephalopods {
	
	// On cherche a être le plus rapide possible (compétition algorithmique).
	// Lors de la conversion en C, tous les types int et long doivent être unsigned.
	// Supprimer les commentaires lors de la conversion, sauf ceux qui commencent par 'case' ou ceux en fin de ligne.

	// Constantes
	private static final int TABLE_WIDTH = 0x80000; // 512K
	private static final int TABLE_DEPTH = 16; // 512K * 16 * 8 = 64 Mo
	
	private static final int b0 = 0;
	private static final int b1 = 3;
	private static final int b2 = 6;
	private static final int b3 = 9;
	private static final int b4 = 12;
	private static final int b5 = 15;
	private static final int b6 = 18;
	private static final int b7 = 21;
	private static final int b8 = 24;

	private static final int m0 = 0_777777770;
	private static final int m1 = 0_777777707;
	private static final int m2 = 0_777777077;
	private static final int m3 = 0_777770777;
	private static final int m4 = 0_777707777;
	private static final int m5 = 0_777077777;
	private static final int m6 = 0_770777777;
	private static final int m7 = 0_707777777;
	private static final int m8 = 0_077777777;

	private static final int mb0 = 0b111111110;
	private static final int mb1 = 0b111111101;
	private static final int mb2 = 0b111111011;
	private static final int mb3 = 0b111110111;
	private static final int mb4 = 0b111101111;
	private static final int mb5 = 0b111011111;
	private static final int mb6 = 0b110111111;
	private static final int mb7 = 0b101111111;
	private static final int mb8 = 0b011111111;

	
	// Bien penser à aligner sur 8 octets lors de la conversion en C
	// Initialiser avec des zéros
	private static final long[] table = new long[TABLE_WIDTH * TABLE_DEPTH];
	
	public static final void main(String[] args) {
		final int turns = Scan.readInt();
		// Peu importe les valeurs initiales en C
		final int[] boards = new int[turns];
		int board = 0;
		// Peu importe les valeurs initiales en C
		final int empties[] = new int[turns];
		int empty = 0;
		for (int i = 0; i < 9; i++) {
			int c = Scan.readInt(); 
			board <<= 3;
			if (c == 0) {
				empty |= 1 << i;
			} else {
				board |= c;
			}
		}
		boards[0] = board;
		empties[0] = empty;
		int score = 0;
		int t = 0;

		while (boards[0] != 0) {
			int e = empties[t];
			int es = e;
			while (e != 0) {
				if (e == 0) { // Remonter d'un cran
					t--;
					break;
				}
				// En C, utiliser __builtin_ctz()
				int bit = Integer.numberOfTrailingZeros(e);
				int b = boards[t];
				int f;
				// bit vaut forcément 0, 1, 2, 3, 4, 5, 6, 7 ou 8 -> optimiser le switch pendant la conversion en C
				// peut être avec des if imbriqués (if < 4, if < 2, if == 0...) ou un tableau de fonctions, ou une meilleure
				// solution question performance
				switch (bit) {
				// Case Haut-gauche
				case (0) :
					int n1 = b >> b1 & 7;
					int n3 = b >> b3 & 7;
					int s;
					if (n1 == 0 || n3 == 0 || (s = n1 + n3) > 6) {
						b = 1;
						f = es & mb0;
					} else {
						b &= m1 & m3;
						b |= s;
						int memoScore = getMemo(b);
						if (memoScore > 0) {
							score += memoScore;
							score &= 0x3FFFFFFF;
							continue;
						}
						f = es & mb0 | mb1 | mb3; 
					}
					
					break;
				}
			}
		}
		System.out.println(score);
	}

	private static final int getMemo(final int b) {
		int res = 0;
		int key = b & (TABLE_WIDTH - 1);
		for (int i = 0; i < TABLE_DEPTH; i++) {
			long v = table[key * TABLE_DEPTH|i];
			if (v == 0) return 0;
			// En C, un cast "(uint) v" devrait être suffisant
			if ((v & 0xFFFFFFFFl) == b) { // Match
				return (int) (v >> 32);
			}
		}
		return 0;
	}
	
	private static final void storeMemo(final int b, final int s) {
		int key = b & (TABLE_WIDTH - 1);
		for (int i = 0; i < TABLE_DEPTH; i++) {
			long v = table[key * TABLE_DEPTH|i];
			if (v == 0) {
				// To be optimized in C
				table[key * TABLE_DEPTH|i] = ((long) s) << 32 | (long) b; 
				break;
			}
		}
	}
}
