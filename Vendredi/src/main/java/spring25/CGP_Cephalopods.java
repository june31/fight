package spring25;

import tools.scanner.Scan;
import tools.strings.S;

public class CGP_Cephalopods {

	// On cherche a être le plus rapide possible (compétition algorithmique).
	// Lors de la conversion en C, tous les types int et long doivent être unsigned.
	// Supprimer les commentaires liés à la conversion lors de la conversion en C.

	// Lors de la conversion en C, rajouter :
	// #pragma GCC optimize "O3,omit-frame-pointer,inline,unroll-loops"

	// Constantes
	private static final int TABLE_WIDTH = 0x80000; // 512K
	private static final int TABLE_DEPTH = 16; // 512K * 16 * 8 = 64 Mo

	private static final int RND = 0x6AA59CAB;

	private static final int SMASK = 0x3FFFFFFF;

	private static final int shift0 = 0;
	private static final int shift1 = 3;
	private static final int shift2 = 6;
	private static final int shift3 = 9;
	private static final int shift4 = 12;
	private static final int shift5 = 15;
	private static final int shift6 = 18;
	private static final int shift7 = 21;
	private static final int shift8 = 24;

	private static final int mask0 = 0_000000007;
	private static final int mask1 = 0_000000070;
	private static final int mask2 = 0_000000700;
	private static final int mask3 = 0_000007000;
	private static final int mask4 = 0_000070000;
	private static final int mask5 = 0_000700000;
	private static final int mask6 = 0_007000000;
	private static final int mask7 = 0_070000000;
	private static final int mask8 = 0_700000000;

	// Initialiser avec des zéros
	private static final long[] table = new long[TABLE_WIDTH * TABLE_DEPTH];
	
	private static int u = 0;

	public static final void main(String[] args) {
		//S.o(516451164l*6 & SMASK);
		final int turns = Scan.readInt();

		// On considère que les cases sont numérotées ainsi :
		// 012
		// 345
		// 678

		// Les boards contiennent :
		// bits 0-1-2 : valeur case 0  
		// bits 3-4-5 : valeur case 1
		// ...
		// bits 24-25-26 : valeur case 8
		// bits 27 à 31 : tour (modulo 32)
		// Le tour (depth) est important, car on peut tomber sur la même configuration à des tours différents,
		// et il faut différencier ces cas.

		int board = 0;
		for (int i = 0; i < 9; i++) 
			board |= Scan.readInt() << (3 * i);
		System.out.println(recurse(board, turns));
		S.e(u);
	}

	private static final int recurse(final int board, final int remainingTurns) {
		char[] s = Integer.toOctalString(board).toCharArray();
		S.o(s[8], s[7], s[6]);
		S.o(s[5], s[4], s[3]);
		S.o(s[2], s[1], s[0]);
		S.o("                               ", Integer.toOctalString(board), remainingTurns);
		S.o();
		int memoScore = getMemo(board, remainingTurns);
		if (memoScore != 0) return memoScore;
		if (remainingTurns == 0) return setAndGetFinalMemo(board, 0);
		int score = 0;
		
		// Case 0 Haut-gauche
		if ((board >> shift0 & 7) == 0) {
			int cell1 = board >> shift1 & 7;
			int cell3 = board >> shift3 & 7;
			int sum = cell1 + cell3;
			if (cell1 == 0 || cell3 == 0 || sum > 6) // Si oui, case à 1
				score = (score + recurse(board | 1 << shift0, remainingTurns - 1)) & SMASK;
			else // Sinon, case à sum et voisins à 0
				score = (score + recurse((board & ~mask1 & ~mask3) | sum << shift0, remainingTurns - 1)) & SMASK;
		}
		
		// Case 1 Haut-centre
		if ((board >> shift1 & 7) == 0) {
			int cell0 = (board >> shift0) & 7;
			int cell2 = (board >> shift2) & 7;
			int cell4 = (board >> shift4) & 7;
			int nonZeroCount = (cell0 != 0 ? 1 : 0) + (cell2 != 0 ? 1 : 0) + (cell4 != 0 ? 1 : 0);
			int sum = cell0 + cell2 + cell4;
			if (nonZeroCount < 2 || sum > 6) // Cas trivial : moins de 2 adjacents non zéro ou somme trop grande
				score = (score + recurse(board | 1 << shift1, remainingTurns - 1)) & SMASK;
			else { // Au moins 2 adjacents non zéro et somme <= 6
				if (nonZeroCount == 3) // Cas somme des 3 adjacents
					score = (score + recurse((board & ~mask0 & ~mask2 & ~mask4) | sum << shift1, remainingTurns - 1)) & SMASK;
				if (cell0 != 0 && cell2 != 0) // Cas somme adjacents 0 + 2
					score = (score + recurse((board & ~mask0 & ~mask2) | (cell0 + cell2) << shift1, remainingTurns - 1)) & SMASK;
				if (cell0 != 0 && cell4 != 0) // Cas somme adjacents 0 + 4
					score = (score + recurse((board & ~mask0 & ~mask4) | (cell0 + cell4) << shift1, remainingTurns - 1)) & SMASK;
				if (cell2 != 0 && cell4 != 0) // Cas somme adjacents 2 + 4
					score = (score + recurse((board & ~mask2 & ~mask4) | (cell2 + cell4) << shift1, remainingTurns - 1)) & SMASK;
			}
		}
		
		// Case 2 Haut-droite
		if ((board >> shift2 & 7) == 0) {
		    int cell1 = (board >> shift1) & 7;
		    int cell5 = (board >> shift5) & 7;
		    int sum = cell1 + cell5;
		    if (cell1 == 0 || cell5 == 0 || sum > 6)
		        score = (score + recurse(board | (1 << shift2), remainingTurns - 1)) & SMASK;
		    else
		        score = (score + recurse((board & ~mask1 & ~mask5) | (sum << shift2), remainingTurns - 1)) & SMASK;
		}

		// Case 3 Centre-gauche
		if ((board >> shift3 & 7) == 0) {
		    int cell0 = (board >> shift0) & 7;
		    int cell4 = (board >> shift4) & 7;
		    int cell6 = (board >> shift6) & 7;
		    int nonZeroCount = (cell0 != 0 ? 1 : 0) + (cell4 != 0 ? 1 : 0) + (cell6 != 0 ? 1 : 0);
		    int sum = cell0 + cell4 + cell6;
		    if (nonZeroCount < 2 || sum > 6)
		        score = (score + recurse(board | (1 << shift3), remainingTurns - 1)) & SMASK;
		    else {
		        if (nonZeroCount == 3)
		            score = (score + recurse((board & ~mask0 & ~mask4 & ~mask6) | (sum << shift3), remainingTurns - 1)) & SMASK;
		        if (cell0 != 0 && cell4 != 0)
		            score = (score + recurse((board & ~mask0 & ~mask4) | ((cell0 + cell4) << shift3), remainingTurns - 1)) & SMASK;
		        if (cell0 != 0 && cell6 != 0)
		            score = (score + recurse((board & ~mask0 & ~mask6) | ((cell0 + cell6) << shift3), remainingTurns - 1)) & SMASK;
		        if (cell4 != 0 && cell6 != 0)
		            score = (score + recurse((board & ~mask4 & ~mask6) | ((cell4 + cell6) << shift3), remainingTurns - 1)) & SMASK;
		    }
		}
		
		// Case 4 Centrale
		if ((board >> shift4 & 7) == 0) {
		    int cell1 = (board >> shift1) & 7;
		    int cell3 = (board >> shift3) & 7;
		    int cell5 = (board >> shift5) & 7;
		    int cell7 = (board >> shift7) & 7;
		    int nonZeroCount = (cell1 != 0 ? 1 : 0) + (cell3 != 0 ? 1 : 0) + (cell5 != 0 ? 1 : 0) + (cell7 != 0 ? 1 : 0);
		    int sum = cell1 + cell3 + cell5 + cell7;
		    
		    if (nonZeroCount < 2 || sum > 6) 
		        score = (score + recurse(board | (1 << shift4), remainingTurns - 1)) & SMASK;
		    else {
		    	// 4 cases vidées
		    	if (nonZeroCount == 4)
		            score = (score + recurse((board & ~mask1 & ~mask3 & ~mask5 & ~mask7) | (sum << shift4), remainingTurns - 1)) & SMASK;
		    	
		    	// 3 cases vidées
		    	if (cell1 != 0 && cell3 != 0 && cell5 != 0)
		            score = (score + recurse((board & ~mask1 & ~mask3 & ~mask5) | ((cell1 + cell3 + cell5) << shift4), remainingTurns - 1)) & SMASK;
		    	if (cell1 != 0 && cell3 != 0 && cell7 != 0)
		            score = (score + recurse((board & ~mask1 & ~mask3 & ~mask7) | ((cell1 + cell3 + cell7) << shift4), remainingTurns - 1)) & SMASK;
		    	if (cell1 != 0 && cell5 != 0 && cell7 != 0)
		            score = (score + recurse((board & ~mask1 & ~mask5 & ~mask7) | ((cell1 + cell5 + cell7) << shift4), remainingTurns - 1)) & SMASK;
		    	if (cell3 != 0 && cell5 != 0 && cell7 != 0)
		            score = (score + recurse((board & ~mask3 & ~mask5 & ~mask7) | ((cell3 + cell5 + cell7) << shift4), remainingTurns - 1)) & SMASK;
		    	
		    	// 2 cases vidées
		    	if (cell1 != 0 && cell3 != 0)
		            score = (score + recurse((board & ~mask1 & ~mask3) | ((cell1 + cell3) << shift4), remainingTurns - 1)) & SMASK;
		    	if (cell1 != 0 && cell5 != 0)
		            score = (score + recurse((board & ~mask1 & ~mask5) | ((cell1 + cell5) << shift4), remainingTurns - 1)) & SMASK;
		    	if (cell1 != 0 && cell7 != 0)
		            score = (score + recurse((board & ~mask1 & ~mask7) | ((cell1 + cell7) << shift4), remainingTurns - 1)) & SMASK;
		    	if (cell3 != 0 && cell5 != 0)
		            score = (score + recurse((board & ~mask3 & ~mask5) | ((cell3 + cell5) << shift4), remainingTurns - 1)) & SMASK;
		    	if (cell3 != 0 && cell7 != 0)
		            score = (score + recurse((board & ~mask3 & ~mask7) | ((cell3 + cell7) << shift4), remainingTurns - 1)) & SMASK;
		    	if (cell5 != 0 && cell7 != 0)
		            score = (score + recurse((board & ~mask5 & ~mask7) | ((cell5 + cell7) << shift4), remainingTurns - 1)) & SMASK;
		    }
		}
		
		// Case 5 : Centre-droit
		if ((board >> shift5 & 7) == 0) {
		    int cell2 = (board >> shift2) & 7;
		    int cell4 = (board >> shift4) & 7;
		    int cell8 = (board >> shift8) & 7;
		    int nonZeroCount = (cell2 != 0 ? 1 : 0) + (cell4 != 0 ? 1 : 0) + (cell8 != 0 ? 1 : 0);
		    int sum = cell2 + cell4 + cell8;
		    if (nonZeroCount < 2 || sum > 6)
		        score = (score + recurse(board | (1 << shift5), remainingTurns - 1)) & SMASK;
		    else {
		        if (nonZeroCount == 3)
		            score = (score + recurse((board & ~mask2 & ~mask4 & ~mask8) | (sum << shift5), remainingTurns - 1)) & SMASK;
		        if (cell2 != 0 && cell4 != 0)
		            score = (score + recurse((board & ~mask2 & ~mask4) | ((cell2 + cell4) << shift5), remainingTurns - 1)) & SMASK;
		        if (cell2 != 0 && cell8 != 0)
		            score = (score + recurse((board & ~mask2 & ~mask8) | ((cell2 + cell8) << shift5), remainingTurns - 1)) & SMASK;
		        if (cell4 != 0 && cell8 != 0)
		            score = (score + recurse((board & ~mask4 & ~mask8) | ((cell4 + cell8) << shift5), remainingTurns - 1)) & SMASK;
		    }
		}

		// Case 6 : Bas-gauche
		if ((board >> shift6 & 7) == 0) {
		    int cell3 = (board >> shift3) & 7;
		    int cell7 = (board >> shift7) & 7;
		    int sum = cell3 + cell7;
		    if (cell3 == 0 || cell7 == 0 || sum > 6)
		        score = (score + recurse(board | (1 << shift6), remainingTurns - 1)) & SMASK;
		    else
		        score = (score + recurse((board & ~mask3 & ~mask7) | (sum << shift6), remainingTurns - 1)) & SMASK;
		}

		// Case 7 : Bas-centre
		if ((board >> shift7 & 7) == 0) {
		    int cell6 = (board >> shift6) & 7;
		    int cell8 = (board >> shift8) & 7;
		    int cell4 = (board >> shift4) & 7;
		    int nonZeroCount = (cell6 != 0 ? 1 : 0) + (cell8 != 0 ? 1 : 0) + (cell4 != 0 ? 1 : 0);
		    int sum = cell6 + cell8 + cell4;
		    if (nonZeroCount < 2 || sum > 6)
		        score = (score + recurse(board | (1 << shift7), remainingTurns - 1)) & SMASK;
		    else {
		        if (nonZeroCount == 3)
		            score = (score + recurse((board & ~mask6 & ~mask8 & ~mask4) | (sum << shift7), remainingTurns - 1)) & SMASK;
		        if (cell6 != 0 && cell8 != 0)
		            score = (score + recurse((board & ~mask6 & ~mask8) | ((cell6 + cell8) << shift7), remainingTurns - 1)) & SMASK;
		        if (cell6 != 0 && cell4 != 0)
		            score = (score + recurse((board & ~mask6 & ~mask4) | ((cell6 + cell4) << shift7), remainingTurns - 1)) & SMASK;
		        if (cell8 != 0 && cell4 != 0)
		            score = (score + recurse((board & ~mask8 & ~mask4) | ((cell8 + cell4) << shift7), remainingTurns - 1)) & SMASK;
		    }
		}

		// Case 8 : Bas-droite
		if ((board >> shift8 & 7) == 0) {
		    int cell5 = (board >> shift5) & 7;
		    int cell7 = (board >> shift7) & 7;
		    int sum = cell5 + cell7;
		    if (cell5 == 0 || cell7 == 0 || sum > 6)
		        score = (score + recurse(board | (1 << shift8), remainingTurns - 1)) & SMASK;
		    else
		        score = (score + recurse((board & ~mask5 & ~mask7) | (sum << shift8), remainingTurns - 1)) & SMASK;
		}
		
		if (score == 0) return setAndGetFinalMemo(board, remainingTurns);
		setMemo(board, score, remainingTurns);
		return score; 
	}

	private static final int getMemo(final int board, final int remainingTurns) {
		// En C, utiliser __builtin_rotateright32()
		int key = (board ^ Integer.rotateRight(RND, remainingTurns)) & (TABLE_WIDTH - 1);
		for (int i = 0; i < TABLE_DEPTH; i++) {
			long v = table[key * TABLE_DEPTH|i];
			if (v == 0) return 0;
			// En C, un cast "(uint) v" devrait être suffisant
			if ((v & 0xFFFFFFFFl) == board) { // Match
				u++;
				S.o("Unique (cached)");
				return (int) (v >> 32);
			}
		}
		return 0;
	}

	private static final void setMemo(final int board, final int score, final int remainingTurns) {
		// En C, utiliser __builtin_rotateright32()
		int key = (board ^ Integer.rotateRight(RND, remainingTurns)) & (TABLE_WIDTH - 1);
		for (int i = 0; i < TABLE_DEPTH; i++) {
			long v = table[key * TABLE_DEPTH|i];
			if (v == 0) {
				// To be optimized in C
				table[key * TABLE_DEPTH|i] = ((long) score) << 32 | (long) board; 
				break;
			}
		}
	}
	
	private static final int setAndGetFinalMemo(final int board, final int turn) {
		u++;
		S.o("Unique");
		// En C, utiliser __builtin_rotateright32()
		int key = (board ^ Integer.rotateRight(RND, turn)) & (TABLE_WIDTH - 1);
		int score = ((((((((board >> shift0 & 7) * 10 
                + (board >> shift1 & 7)) * 10 
                + (board >> shift2 & 7)) * 10 
                + (board >> shift3 & 7)) * 10 
                + (board >> shift4 & 7)) * 10 
                + (board >> shift5 & 7)) * 10 
                + (board >> shift6 & 7)) * 10 
                + (board >> shift7 & 7)) * 10 
                + (board >> shift8 & 7);

		for (int i = 0; i < TABLE_DEPTH; i++) {
			long v = table[key * TABLE_DEPTH|i];
			if (v == 0) {
				// To be optimized in C
				table[key * TABLE_DEPTH|i] = ((long) score) << 32 | (long) board; 
				break;
			}
		}
		return score;
	}
}
