package spring25;

import tools.scanner.Scan;

public class CGP_Cephalopods {
	
	// On cherche a être le plus rapide possible (compétition algorithmique).
	// Lors de la conversion en C, tous les types int et long doivent être unsigned.
	// Supprimer les commentaires lors de la conversion, sauf ceux qui commencent par 'case' ou ceux en fin de ligne.

	// Constantes
	private static final int ZOBRIST_WIDTH = 0x40000; // 262144
	private static final int ZOBRIST_DEPTH = 16; // 262144 * 16 * 16 = 64 Mo
	
	// Constant
	private static final long[] zBoard = {
			0, 0xC393A0C86CDC7BF9l, 0xB7BB7401DD773721l, 0x9F866BFB4AC89379l, 0x2CEBA624005F7125l, 0xA6D285D91E9E5167l, 0x5A33E1724FCB7650l, 0,
			0, 0x5D53F0408296E27El, 0x680D18BD02996CD9l, 0x3927F6307FA58FB5l, 0x81AA7F923E4F5FC3l, 0x502FB588AE827EB0l, 0x9265B39BCB88B671l, 0,
			0, 0x188B011866BC857Al, 0x967998460A0B117Fl, 0x7F50DED38B680571l, 0x4B1CC8A4648BA0CEl, 0x315B266413EC0181l, 0x3FB6C393532B46E0l, 0,
			0, 0x9B4F03E439CDE394l, 0x07F9361DB5411D67l, 0xA86462E7FD6FA549l, 0x3759082284702F18l, 0xAF0A85C032BB4FE3l, 0x67F90F35457E7AC3l, 0,
			0, 0xD7A84CFD66F20783l, 0x83D28BC4243093A8l, 0xF09AF3341184159Al, 0xE505D47AE7CE7302l, 0x6B7E4100B63AA698l, 0x5BFFD3E94B2B4EE8l, 0,
			0, 0xC6F57D4DEE532EE9l, 0x55C73C6336538A55l, 0x0DDC9DD4F3B7AB21l, 0xF48E9626AEA11285l, 0x3D3D7BE1F47E2F75l, 0x0ED68BC46D27BD82l, 0,
			0, 0x35DD115C888D7A6Dl, 0x783E18B920707DFDl, 0x88F1020776EB7ED7l, 0x58D47E5BB6938061l, 0xA1CDF503171618FEl, 0x769B22FD452E8525l, 0,
			0, 0xFC6382980415631Bl, 0x5F23BF4B6EB60C61l, 0xBA62BA353DFA5D0Cl, 0x27197DF4857526A4l, 0xBB3884B4E273DB43l, 0xB149F1943D78FF04l, 0,
			0, 0x9DD566E3314EF100l, 0xF8AD04C6ECE27275l, 0xCAD0683EED5749C2l, 0xEF39489F26E9485El, 0xDA0B2202585F25FAl, 0x0844453DBE81E069l
	};

	// Constant
	private static final long[] zTurn = {
			0xF6F148C6363E15BDl, 0x7F84E05C8D4E2895l, 0xE5505AA1CDD6280Fl, 0xA8F56C1AB315A579l, 0x99D74BD776AC767Cl, 0xE2629FA987558F68l, 0xFAE3A00E0DFD666Dl, 0x6E3DAA58499D155Cl,
			0xD4C83C2104187EA2l, 0x0400511825597B98l, 0x4D8DE495B0B0DD65l, 0x1091FAA5CEF49EFCl, 0x1E5EA06945025E02l, 0x4EAED9EEE407D99Bl, 0x52FE040D051F2175l, 0xF3ACDAB047001C8Al,
			0x654CA8B506C06E65l, 0xA490345D170952FAl, 0xF5F553CF4A311C51l, 0x9A1BC742D3E46289l, 0x784692CFF896FE08l, 0xD6A7A7F043AC5CD4l, 0x3E23CF35FD43FF5El, 0xC84A7CD233C4B650l,
			0x06251ECA150EEA1Cl, 0xBB88921CA773965Al, 0xA1AD54260A3F0638l, 0xD885C7F24348948Cl, 0x5F321375D4950945l, 0x24E5BEBD25A17751l, 0x8669EF821EAF092Cl, 0xF157E5DE17A3C7BBl,
			0x8CD0D2EA9BAC661Cl, 0xD983508B601B4E93l, 0x693339B42CD1DFBFl, 0xD9E074F4201ACEB1l, 0x08F4FF04163D49ADl, 0x274D39A7313D908El, 0xD9D0C3291EBB9325l, 0x3A09F535043D9317l
	};

	// Sera une structure C lors de la conversion
	private static final record ZE(long z, long score) {}
	
	// Bien penser à aligner sur 16 octets lors de la conversion en C
	// Contenu non constant, pas besoin d'initialisation du contenu
	private static final ZE[] zTable = new ZE[0x4000*16];
	
	public static final void main(String[] args) {
		final int turns = Scan.readInt();
		// Peu importe les valeurs initiales en C
		final int[] boards = new int[turns];
		int board = 0;
		// Peu importe les valeurs initiales en C
		final int empties[] = new int[turns];
		int empty = 0;
		// Peu importe les valeurs initiales en C
		final long[] zobrists = new long[turns];
		long zobrist = 0;
		for (int i = 0; i < 9; i++) {
			int c = Scan.readInt(); 
			board <<= 3;
			if (c == 0) {
				empty |= 1 << i;
			} else {
				board |= c;
				zobrist ^= zBoard[i<<3|c];
			}
		}
		boards[0] = board;
		empties[0] = empty;
		zobrists[0] = zobrist;
		long score = 0;

		for (int t = 0; t < turns; t++) {
			int b = boards[t];
			int e = empties[t];
			long z = zobrists[t];
			// En C, utiliser __builtin_ctz()
			int bit = Integer.numberOfTrailingZeros(t);
			while (bit != 32) {
				// bit vaut forcément 0, 1, 2, 3, 4, 5, 6, 7 ou 8 -> optimiser le switch pendant la conversion en C
				// peut être avec des if imbriqués (if < 4, if < 2, if == 0...) ou un tableau de fonctions, ou une meilleure solution
				switch (bit) {
				// Case Haut-gauche
				case (0) :
					int n1 = b >> 3 & 7;
					int n2 = b >> 9 & 7;
					int s;
					if (n1 == 0 || n2 == 0 || (s = n1 + n2) > 6) {
						z ^= 0xC393A0C86CDC7BF9l; 
					} else {
						board &= 0_707077777;
						board |= s;
						z = z ^ zBoard[8|n1] ^ zBoard[24|n2] ^ zBoard[s];
						long memoScore = memo(z, s);
						if (memoScore > 0) score += memoScore;								
						else {
							
						}
					}
				}
			}
		}

	}

	private static final long memo(final long z, final int s) {
		long key = z & (long) (ZOBRIST_WIDTH - 1);
		int base = ((int) key) * ZOBRIST_DEPTH;
		for (int i = 0; i < ZOBRIST_DEPTH; i++) {
			ZE ze = zTable[base|i];
			// En C, on considèrera que ze == null si (ze.z & (long) (ZOBRIST_WIDTH - 1)) != key
			if (ze == null) return 0;
			if (ze.z == z) { // Match
				return ze.score;
			}
		}
		return 0l;
	}
}
