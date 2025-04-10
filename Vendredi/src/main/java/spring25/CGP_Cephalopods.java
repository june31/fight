package spring25;

import tools.scanner.Scan;

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

	private static final int e0 = 0b000000001;
	private static final int e1 = 0b000000010;
	private static final int e2 = 0b000000100;
	private static final int e3 = 0b000001000;
	private static final int e4 = 0b000010000;
	private static final int e5 = 0b000100000;
	private static final int e6 = 0b001000000;
	private static final int e7 = 0b010000000;
	private static final int e8 = 0b100000000;

	
	// Initialiser avec des zéros
	private static final long[] table = new long[TABLE_WIDTH * TABLE_DEPTH];
	
	public static final void main(String[] args) {
		final int turns = Scan.readInt();
		// Peu importe les valeurs initiales en C
		final int[] boards = new int[turns + 1];
		
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
		// Le tour est important, car on peut tomber sur la même configuration à des tours différents,
		// et il faut différencier ces cas.
		int board = 0;
		// Peu importe les valeurs initiales de ce tableau en C
		final int empties[] = new int[turns + 1];
		int empty = 0;
		for (int i = 0; i < 9; i++) {
			int c = Scan.readInt(); 
			board <<= 3;
			if (c == 0) empty |= 1 << i;
			else board |= c;
		}
		boards[0] = board;
		empties[0] = empty;
		int depth = 0;

		int score = 0;
		
		// Vu que tout sera unsigned en C, peut être mettre 'depth < 100' avec un commentaire
		DFS: while (depth >= 0) {
			score = 0;
			MOVES: while (empty != 0) {
				// En C, utiliser __builtin_ctz()
				int bit = Integer.numberOfTrailingZeros(empty);
				empty -= 1 << bit;
				int nextBoard = board;
				int nextEmpty = empty;
				// bit vaut forcément 0, 1, 2, 3, 4, 5, 6, 7 ou 8 -> optimiser le switch pendant la conversion en C
				// peut être avec des if imbriqués (if < 4, if < 2, if == 0...) ou un tableau de fonctions, ou une meilleure
				// solution question performance
				if (bit < 4) {
					if (bit < 2) {
						if (bit == 0) { // Case 0 Haut-gauche
							int cell1, cell3, sum;
							if ((cell1 = board >> shift1 & 7) == 0 || (cell3 = board >> shift3 & 7) == 0 || (sum = cell1 + cell3) > 6) { // Si oui, case à 1
								nextBoard |= 1 << shift0;
							} else { // Sinon, case à sum et voisins à 0
								nextBoard &= ~mask1 & ~mask3;
								nextBoard |= sum << shift0;
								int memoScore = getMemo(nextBoard, depth);
								if (memoScore > 0) {
									score += memoScore;
									score &= 0x3FFFFFFF;
									continue MOVES;
								}
								nextEmpty |= e1 | e3; // Deux cases se libèrent 
							}
							if (nextEmpty > 0 && depth < turns) {
								empties[depth] = empty;
								board = nextBoard;
								empty = nextEmpty; 
								depth++;
								boards[depth] = board; 
								continue DFS;
							}
						} else { // Case 1 Haut-centre
						    int cell0 = (board >> shift0) & 7;
						    int cell2 = (board >> shift2) & 7;
						    int cell4 = (board >> shift4) & 7;

						    int nonZeroCount = (cell0 != 0 ? 1 : 0) + (cell2 != 0 ? 1 : 0) + (cell4 != 0 ? 1 : 0);
						    int sum = cell0 + cell2 + cell4;

						    if (nonZeroCount < 2 || sum > 6) {
						        // Cas trivial : moins de 2 adjacents non zéro ou somme trop grande
						        nextBoard |= 1 << shift1;
						        if (nextEmpty > 0 && depth < turns) {
						            empties[depth] = empty;
						            board = nextBoard;
						            empty = nextEmpty;
						            depth++;
						            boards[depth] = board;
						            continue DFS;
						        }
						    } else {
						        // Au moins 2 adjacents non zéro et somme ≤ 6
						        int memoScore, updatedBoard, updatedEmpty;

						        // Cas 3 adjacents non zéro : 4 possibilités à considérer explicitement
						        if (nonZeroCount == 3) {
						            // Cas somme des 3 adjacents
						            updatedBoard = (nextBoard & ~mask0 & ~mask2 & ~mask4) | (sum << shift1);
						            memoScore = getMemo(updatedBoard, depth);
						            if (memoScore > 0) {
						                score = (score + memoScore) & 0x3FFFFFFF;
						            } else {
						                updatedEmpty = nextEmpty | e0 | e2 | e4;
						                if (updatedEmpty > 0 && depth < turns) {
						                    empties[depth] = empty;
						                    board = updatedBoard;
						                    empty = updatedEmpty;
						                    depth++;
						                    boards[depth] = board;
						                    continue DFS;
						                }
						            }

						            // Cas somme adjacents 0+2
						            updatedBoard = (nextBoard & ~mask0 & ~mask2) | ((cell0 + cell2) << shift1);
						            memoScore = getMemo(updatedBoard, depth);
						            if (memoScore > 0) {
						                score = (score + memoScore) & 0x3FFFFFFF;
						            } else {
						                updatedEmpty = nextEmpty | e0 | e2;
						                if (updatedEmpty > 0 && depth < turns) {
						                    empties[depth] = empty;
						                    board = updatedBoard;
						                    empty = updatedEmpty;
						                    depth++;
						                    boards[depth] = board;
						                    continue DFS;
						                }
						            }

						            // Cas somme adjacents 0+4
						            updatedBoard = (nextBoard & ~mask0 & ~mask4) | ((cell0 + cell4) << shift1);
						            memoScore = getMemo(updatedBoard, depth);
						            if (memoScore > 0) {
						                score = (score + memoScore) & 0x3FFFFFFF;
						            } else {
						                updatedEmpty = nextEmpty | e0 | e4;
						                if (updatedEmpty > 0 && depth < turns) {
						                    empties[depth] = empty;
						                    board = updatedBoard;
						                    empty = updatedEmpty;
						                    depth++;
						                    boards[depth] = board;
						                    continue DFS;
						                }
						            }

						            // Cas somme adjacents 2+4
						            updatedBoard = (nextBoard & ~mask2 & ~mask4) | ((cell2 + cell4) << shift1);
						            memoScore = getMemo(updatedBoard, depth);
						            if (memoScore > 0) {
						                score = (score + memoScore) & 0x3FFFFFFF;
						            } else {
						                updatedEmpty = nextEmpty | e2 | e4;
						                if (updatedEmpty > 0 && depth < turns) {
						                    empties[depth] = empty;
						                    board = updatedBoard;
						                    empty = updatedEmpty;
						                    depth++;
						                    boards[depth] = board;
						                    continue DFS;
						                }
						            }
						        } else { // Cas exactement 2 adjacents non zéro : une seule combinaison
						            if (cell0 == 0) {
						                // adjacents 2 et 4
						                updatedBoard = (nextBoard & ~mask2 & ~mask4) | ((cell2 + cell4) << shift1);
						                updatedEmpty = nextEmpty | e2 | e4;
						            } else if (cell2 == 0) {
						                // adjacents 0 et 4
						                updatedBoard = (nextBoard & ~mask0 & ~mask4) | ((cell0 + cell4) << shift1);
						                updatedEmpty = nextEmpty | e0 | e4;
						            } else {
						                // adjacents 0 et 2
						                updatedBoard = (nextBoard & ~mask0 & ~mask2) | ((cell0 + cell2) << shift1);
						                updatedEmpty = nextEmpty | e0 | e2;
						            }
						            memoScore = getMemo(updatedBoard, depth);
						            if (memoScore > 0) {
						                score = (score + memoScore) & 0x3FFFFFFF;
						            } else if (updatedEmpty > 0 && depth < turns) {
						                empties[depth] = empty;
						                board = updatedBoard;
						                empty = updatedEmpty;
						                depth++;
						                boards[depth] = board;
						                continue DFS;
						            }
						        }
						    }
						} 
					} else if (bit == 2) { // Case 2 Haut-droite
					    // Adjacent : cellule 1 (gauche) et cellule 5 (en dessous)
					    int cell1 = (board >> shift1) & 7;
					    int cell5 = (board >> shift5) & 7;
					    int sum = cell1 + cell5;
					    if (cell1 == 0 || cell5 == 0 || sum > 6) {
					        // Si l'un des adjacents est zéro ou que la somme dépasse 6, on place 1
					        nextBoard |= 1 << shift2;
					        if (nextEmpty > 0 && depth < turns) {
					            empties[depth] = empty;
					            board = nextBoard;
					            empty = nextEmpty;
					            depth++;
					            boards[depth] = board;
					            continue DFS;
					        }
					    } else {
					        // Sinon, on vide les cases utilisées (masque précis pour cell1 et cell5) et place la somme
					        int updatedBoard = (nextBoard & ~mask1 & ~mask5) | (sum << shift2);
					        int memoScore = getMemo(updatedBoard, depth);
					        if (memoScore > 0) {
					            score = (score + memoScore) & 0x3FFFFFFF;
					        } else {
					            int updatedEmpty = nextEmpty | e1 | e5;
					            if (updatedEmpty > 0 && depth < turns) {
					                empties[depth] = empty;
					                board = updatedBoard;
					                empty = updatedEmpty;
					                depth++;
					                boards[depth] = board;
					                continue DFS;
					            }
					        }
					    }
					} else if (bit == 3) { // Case 3 Centre-gauche
					    // Adjacent : cellule 0 (au-dessus), cellule 4 (à droite) et cellule 6 (en dessous)
					    int cell0 = (board >> shift0) & 7;
					    int cell4 = (board >> shift4) & 7;
					    int cell6 = (board >> shift6) & 7;
					    int nonZeroCount = (cell0 != 0 ? 1 : 0) + (cell4 != 0 ? 1 : 0) + (cell6 != 0 ? 1 : 0);
					    int sum = cell0 + cell4 + cell6;
					    if (nonZeroCount < 2 || sum > 6) {
					        // Cas trivial : moins de 2 adjacents non nuls ou somme > 6
					        nextBoard |= 1 << shift3;
					        if (nextEmpty > 0 && depth < turns) {
					            empties[depth] = empty;
					            board = nextBoard;
					            empty = nextEmpty;
					            depth++;
					            boards[depth] = board;
					            continue DFS;
					        }
					    } else {
					        int memoScore, updatedBoard, updatedEmpty;
					        if (nonZeroCount == 3) {
					            // Possibilité 1 : somme des 3 adjacents
					            updatedBoard = (nextBoard & ~mask0 & ~mask4 & ~mask6) | (sum << shift3);
					            memoScore = getMemo(updatedBoard, depth);
					            if (memoScore > 0) {
					                score = (score + memoScore) & 0x3FFFFFFF;
					            } else {
					                updatedEmpty = nextEmpty | e0 | e4 | e6;
					                if (updatedEmpty > 0 && depth < turns) {
					                    empties[depth] = empty;
					                    board = updatedBoard;
					                    empty = updatedEmpty;
					                    depth++;
					                    boards[depth] = board;
					                    continue DFS;
					                }
					            }
					            // Possibilité 2 : somme des paires parmi (cell0, cell4, cell6)
					            // a) cell0 + cell4
					            updatedBoard = (nextBoard & ~mask0 & ~mask4) | ((cell0 + cell4) << shift3);
					            memoScore = getMemo(updatedBoard, depth);
					            if (memoScore > 0) {
					                score = (score + memoScore) & 0x3FFFFFFF;
					            } else {
					                updatedEmpty = nextEmpty | e0 | e4;
					                if (updatedEmpty > 0 && depth < turns) {
					                    empties[depth] = empty;
					                    board = updatedBoard;
					                    empty = updatedEmpty;
					                    depth++;
					                    boards[depth] = board;
					                    continue DFS;
					                }
					            }
					            // b) cell0 + cell6
					            updatedBoard = (nextBoard & ~mask0 & ~mask6) | ((cell0 + cell6) << shift3);
					            memoScore = getMemo(updatedBoard, depth);
					            if (memoScore > 0) {
					                score = (score + memoScore) & 0x3FFFFFFF;
					            } else {
					                updatedEmpty = nextEmpty | e0 | e6;
					                if (updatedEmpty > 0 && depth < turns) {
					                    empties[depth] = empty;
					                    board = updatedBoard;
					                    empty = updatedEmpty;
					                    depth++;
					                    boards[depth] = board;
					                    continue DFS;
					                }
					            }
					            // c) cell4 + cell6
					            updatedBoard = (nextBoard & ~mask4 & ~mask6) | ((cell4 + cell6) << shift3);
					            memoScore = getMemo(updatedBoard, depth);
					            if (memoScore > 0) {
					                score = (score + memoScore) & 0x3FFFFFFF;
					            } else {
					                updatedEmpty = nextEmpty | e4 | e6;
					                if (updatedEmpty > 0 && depth < turns) {
					                    empties[depth] = empty;
					                    board = updatedBoard;
					                    empty = updatedEmpty;
					                    depth++;
					                    boards[depth] = board;
					                    continue DFS;
					                }
					            }
					        } else { 
					            // Cas : exactement 2 cellules non nulles
					            if (cell0 == 0) {
					                // Les cellules 4 et 6 sont non nulles
					                updatedBoard = (nextBoard & ~mask4 & ~mask6) | ((cell4 + cell6) << shift3);
					                updatedEmpty = nextEmpty | e4 | e6;
					            } else if (cell4 == 0) {
					                // Les cellules 0 et 6 sont non nulles
					                updatedBoard = (nextBoard & ~mask0 & ~mask6) | ((cell0 + cell6) << shift3);
					                updatedEmpty = nextEmpty | e0 | e6;
					            } else {
					                // Les cellules 0 et 4 sont non nulles
					                updatedBoard = (nextBoard & ~mask0 & ~mask4) | ((cell0 + cell4) << shift3);
					                updatedEmpty = nextEmpty | e0 | e4;
					            }
					            memoScore = getMemo(updatedBoard, depth);
					            if (memoScore > 0) {
					                score = (score + memoScore) & 0x3FFFFFFF;
					            } else if (updatedEmpty > 0 && depth < turns) {
					                empties[depth] = empty;
					                board = updatedBoard;
					                empty = updatedEmpty;
					                depth++;
					                boards[depth] = board;
					                continue DFS;
					            }
					        }
					    }
					}
				} else if (bit > 4) {
					if (bit < 7) {
						if (bit == 5) { // Case 5 Centre-droit
						    int cell2 = (board >> shift2) & 7;
						    int cell4 = (board >> shift4) & 7;
						    int cell8 = (board >> shift8) & 7;
						    int nonZeroCount = (cell2 != 0 ? 1 : 0) + (cell4 != 0 ? 1 : 0) + (cell8 != 0 ? 1 : 0);
						    int sum = cell2 + cell4 + cell8;

						    if (nonZeroCount < 2 || sum > 6) {
						        // Cas trivial : moins de 2 adjacents non nuls ou somme > 6
						        nextBoard |= 1 << shift5;
						        if (nextEmpty > 0 && depth < turns) {
						            empties[depth] = empty;
						            board = nextBoard;
						            empty = nextEmpty;
						            depth++;
						            boards[depth] = board;
						            continue DFS;
						        }
						    } else {
						        int memoScore, updatedBoard, updatedEmpty;
						        if (nonZeroCount == 3) {
						            // Possibilité 1 : somme des 3 adjacents
						            updatedBoard = (nextBoard & ~mask2 & ~mask4 & ~mask8) | (sum << shift5);
						            memoScore = getMemo(updatedBoard, depth);
						            if (memoScore > 0) {
						                score = (score + memoScore) & 0x3FFFFFFF;
						            } else {
						                updatedEmpty = nextEmpty | e2 | e4 | e8;
						                if (updatedEmpty > 0 && depth < turns) {
						                    empties[depth] = empty;
						                    board = updatedBoard;
						                    empty = updatedEmpty;
						                    depth++;
						                    boards[depth] = board;
						                    continue DFS;
						                }
						            }
						            // Possibilité 2 : somme des paires
						            // a) cell2 + cell4
						            updatedBoard = (nextBoard & ~mask2 & ~mask4) | ((cell2 + cell4) << shift5);
						            memoScore = getMemo(updatedBoard, depth);
						            if (memoScore > 0) {
						                score = (score + memoScore) & 0x3FFFFFFF;
						            } else {
						                updatedEmpty = nextEmpty | e2 | e4;
						                if (updatedEmpty > 0 && depth < turns) {
						                    empties[depth] = empty;
						                    board = updatedBoard;
						                    empty = updatedEmpty;
						                    depth++;
						                    boards[depth] = board;
						                    continue DFS;
						                }
						            }
						            // b) cell2 + cell8
						            updatedBoard = (nextBoard & ~mask2 & ~mask8) | ((cell2 + cell8) << shift5);
						            memoScore = getMemo(updatedBoard, depth);
						            if (memoScore > 0) {
						                score = (score + memoScore) & 0x3FFFFFFF;
						            } else {
						                updatedEmpty = nextEmpty | e2 | e8;
						                if (updatedEmpty > 0 && depth < turns) {
						                    empties[depth] = empty;
						                    board = updatedBoard;
						                    empty = updatedEmpty;
						                    depth++;
						                    boards[depth] = board;
						                    continue DFS;
						                }
						            }
						            // c) cell4 + cell8
						            updatedBoard = (nextBoard & ~mask4 & ~mask8) | ((cell4 + cell8) << shift5);
						            memoScore = getMemo(updatedBoard, depth);
						            if (memoScore > 0) {
						                score = (score + memoScore) & 0x3FFFFFFF;
						            } else {
						                updatedEmpty = nextEmpty | e4 | e8;
						                if (updatedEmpty > 0 && depth < turns) {
						                    empties[depth] = empty;
						                    board = updatedBoard;
						                    empty = updatedEmpty;
						                    depth++;
						                    boards[depth] = board;
						                    continue DFS;
						                }
						            }
						        } else { // exactement 2 adjacents non nuls
						            if (cell2 == 0) {
						                updatedBoard = (nextBoard & ~mask4 & ~mask8) | ((cell4 + cell8) << shift5);
						                updatedEmpty = nextEmpty | e4 | e8;
						            } else if (cell4 == 0) {
						                updatedBoard = (nextBoard & ~mask2 & ~mask8) | ((cell2 + cell8) << shift5);
						                updatedEmpty = nextEmpty | e2 | e8;
						            } else {
						                updatedBoard = (nextBoard & ~mask2 & ~mask4) | ((cell2 + cell4) << shift5);
						                updatedEmpty = nextEmpty | e2 | e4;
						            }
						            memoScore = getMemo(updatedBoard, depth);
						            if (memoScore > 0) {
						                score = (score + memoScore) & 0x3FFFFFFF;
						            } else if (updatedEmpty > 0 && depth < turns) {
						                empties[depth] = empty;
						                board = updatedBoard;
						                empty = updatedEmpty;
						                depth++;
						                boards[depth] = board;
						                continue DFS;
						            }
						        }
						    }
						} else { // Case 6 Bas-gauche
						    int cell3 = (board >> shift3) & 7;
						    int cell7 = (board >> shift7) & 7;
						    int sum = cell3 + cell7;
						    if (cell3 == 0 || cell7 == 0 || sum > 6) {
						        nextBoard |= 1 << shift6;
						        if (nextEmpty > 0 && depth < turns) {
						            empties[depth] = empty;
						            board = nextBoard;
						            empty = nextEmpty;
						            depth++;
						            boards[depth] = board;
						            continue DFS;
						        }
						    } else {
						        int updatedBoard = (nextBoard & ~mask3 & ~mask7) | (sum << shift6);
						        int memoScore = getMemo(updatedBoard, depth);
						        if (memoScore > 0) {
						            score = (score + memoScore) & 0x3FFFFFFF;
						        } else {
						            int updatedEmpty = nextEmpty | e3 | e7;
						            if (updatedEmpty > 0 && depth < turns) {
						                empties[depth] = empty;
						                board = updatedBoard;
						                empty = updatedEmpty;
						                depth++;
						                boards[depth] = board;
						                continue DFS;
						            }
						        }
						    }
						}
					} else if (bit == 7) { // Case 7 Bas-centre
					    int cell6 = (board >> shift6) & 7;
					    int cell8 = (board >> shift8) & 7;
					    int cell4 = (board >> shift4) & 7;
					    int nonZeroCount = (cell6 != 0 ? 1 : 0) + (cell8 != 0 ? 1 : 0) + (cell4 != 0 ? 1 : 0);
					    int sum = cell6 + cell8 + cell4;
					    if (nonZeroCount < 2 || sum > 6) {
					        nextBoard |= 1 << shift7;
					        if (nextEmpty > 0 && depth < turns) {
					            empties[depth] = empty;
					            board = nextBoard;
					            empty = nextEmpty;
					            depth++;
					            boards[depth] = board;
					            continue DFS;
					        }
					    } else {
					        int memoScore, updatedBoard, updatedEmpty;
					        if (nonZeroCount == 3) {
					            // Possibilité 1 : somme des 3 adjacents
					            updatedBoard = (nextBoard & ~mask6 & ~mask8 & ~mask4) | (sum << shift7);
					            memoScore = getMemo(updatedBoard, depth);
					            if (memoScore > 0) {
					                score = (score + memoScore) & 0x3FFFFFFF;
					            } else {
					                updatedEmpty = nextEmpty | e6 | e8 | e4;
					                if (updatedEmpty > 0 && depth < turns) {
					                    empties[depth] = empty;
					                    board = updatedBoard;
					                    empty = updatedEmpty;
					                    depth++;
					                    boards[depth] = board;
					                    continue DFS;
					                }
					            }
					            // Possibilité 2 : somme des paires
					            // a) cell6 + cell8
					            updatedBoard = (nextBoard & ~mask6 & ~mask8) | ((cell6 + cell8) << shift7);
					            memoScore = getMemo(updatedBoard, depth);
					            if (memoScore > 0) {
					                score = (score + memoScore) & 0x3FFFFFFF;
					            } else {
					                updatedEmpty = nextEmpty | e6 | e8;
					                if (updatedEmpty > 0 && depth < turns) {
					                    empties[depth] = empty;
					                    board = updatedBoard;
					                    empty = updatedEmpty;
					                    depth++;
					                    boards[depth] = board;
					                    continue DFS;
					                }
					            }
					            // b) cell6 + cell4
					            updatedBoard = (nextBoard & ~mask6 & ~mask4) | ((cell6 + cell4) << shift7);
					            memoScore = getMemo(updatedBoard, depth);
					            if (memoScore > 0) {
					                score = (score + memoScore) & 0x3FFFFFFF;
					            } else {
					                updatedEmpty = nextEmpty | e6 | e4;
					                if (updatedEmpty > 0 && depth < turns) {
					                    empties[depth] = empty;
					                    board = updatedBoard;
					                    empty = updatedEmpty;
					                    depth++;
					                    boards[depth] = board;
					                    continue DFS;
					                }
					            }
					            // c) cell8 + cell4
					            updatedBoard = (nextBoard & ~mask8 & ~mask4) | ((cell8 + cell4) << shift7);
					            memoScore = getMemo(updatedBoard, depth);
					            if (memoScore > 0) {
					                score = (score + memoScore) & 0x3FFFFFFF;
					            } else {
					                updatedEmpty = nextEmpty | e8 | e4;
					                if (updatedEmpty > 0 && depth < turns) {
					                    empties[depth] = empty;
					                    board = updatedBoard;
					                    empty = updatedEmpty;
					                    depth++;
					                    boards[depth] = board;
					                    continue DFS;
					                }
					            }
					        } else { // exactement 2 non nulles
					            if (cell6 == 0) {
					                updatedBoard = (nextBoard & ~mask8 & ~mask4) | ((cell8 + cell4) << shift7);
					                updatedEmpty = nextEmpty | e8 | e4;
					            } else if (cell8 == 0) {
					                updatedBoard = (nextBoard & ~mask6 & ~mask4) | ((cell6 + cell4) << shift7);
					                updatedEmpty = nextEmpty | e6 | e4;
					            } else {
					                updatedBoard = (nextBoard & ~mask6 & ~mask8) | ((cell6 + cell8) << shift7);
					                updatedEmpty = nextEmpty | e6 | e8;
					            }
					            memoScore = getMemo(updatedBoard, depth);
					            if (memoScore > 0) {
					                score = (score + memoScore) & 0x3FFFFFFF;
					            } else if (updatedEmpty > 0 && depth < turns) {
					                empties[depth] = empty;
					                board = updatedBoard;
					                empty = updatedEmpty;
					                depth++;
					                boards[depth] = board;
					                continue DFS;
					            }
					        }
					    }
					} else { // Case 8 Bas-droite
					    int cell5 = (board >> shift5) & 7;
					    int cell7 = (board >> shift7) & 7;
					    int sum = cell5 + cell7;
					    if (cell5 == 0 || cell7 == 0 || sum > 6) {
					        nextBoard |= 1 << shift8;
					        if (nextEmpty > 0 && depth < turns) {
					            empties[depth] = empty;
					            board = nextBoard;
					            empty = nextEmpty;
					            depth++;
					            boards[depth] = board;
					            continue DFS;
					        }
					    } else {
					        int updatedBoard = (nextBoard & ~mask5 & ~mask7) | (sum << shift8);
					        int memoScore = getMemo(updatedBoard, depth);
					        if (memoScore > 0) {
					            score = (score + memoScore) & 0x3FFFFFFF;
					        } else {
					            int updatedEmpty = nextEmpty | e5 | e7;
					            if (updatedEmpty > 0 && depth < turns) {
					                empties[depth] = empty;
					                board = updatedBoard;
					                empty = updatedEmpty;
					                depth++;
					                boards[depth] = board;
					                continue DFS;
					            }
					        }
					    }
					}
				} else { // Case 4 centrale
				    // Adjacent : cell1 (haut), cell3 (gauche), cell5 (droite), cell7 (bas)
				    int cell1 = (board >> shift1) & 7;
				    int cell3 = (board >> shift3) & 7;
				    int cell5 = (board >> shift5) & 7;
				    int cell7 = (board >> shift7) & 7;
				    int count = (cell1 != 0 ? 1 : 0) + (cell3 != 0 ? 1 : 0) + (cell5 != 0 ? 1 : 0) + (cell7 != 0 ? 1 : 0);
				    int sumAll = cell1 + cell3 + cell5 + cell7;

				    if (count < 2 || sumAll > 6) {
				        // Cas trivial : pas assez de cellules non nulles ou somme trop grande
				        nextBoard |= 1 << shift4;
				        if (nextEmpty > 0 && depth < turns) {
				            empties[depth] = empty;
				            board = nextBoard;
				            empty = nextEmpty;
				            depth++;
				            boards[depth] = board;
				            continue DFS;
				        }
				    } else {
				        int memoScore, updatedBoard, updatedEmpty;
				        if (count == 2) {
				            // Trouver le couple non nul (il y a un seul couple possible)
				            if (cell1 == 0) {
				                if (cell3 != 0 && cell5 != 0) {
				                    updatedBoard = (nextBoard & ~mask3 & ~mask5) | ((cell3 + cell5) << shift4);
				                    updatedEmpty = nextEmpty | e3 | e5;
				                } else { // cell3 et cell7 non nuls
				                    updatedBoard = (nextBoard & ~mask3 & ~mask7) | ((cell3 + cell7) << shift4);
				                    updatedEmpty = nextEmpty | e3 | e7;
				                }
				            } else if (cell3 == 0) {
				                if (cell1 != 0 && cell5 != 0) {
				                    updatedBoard = (nextBoard & ~mask1 & ~mask5) | ((cell1 + cell5) << shift4);
				                    updatedEmpty = nextEmpty | e1 | e5;
				                } else { // cell1 et cell7 non nuls
				                    updatedBoard = (nextBoard & ~mask1 & ~mask7) | ((cell1 + cell7) << shift4);
				                    updatedEmpty = nextEmpty | e1 | e7;
				                }
				            } else if (cell5 == 0) {
				                // alors cell1 et cell7 sont non nuls
				                updatedBoard = (nextBoard & ~mask1 & ~mask7) | ((cell1 + cell7) << shift4);
				                updatedEmpty = nextEmpty | e1 | e7;
				            } else { // cell7 == 0 => non nul: cell1 et cell3
				                updatedBoard = (nextBoard & ~mask1 & ~mask3) | ((cell1 + cell3) << shift4);
				                updatedEmpty = nextEmpty | e1 | e3;
				            }
				            memoScore = getMemo(updatedBoard, depth);
				            if (memoScore > 0) {
				                score = (score + memoScore) & 0x3FFFFFFF;
				            } else if (updatedEmpty > 0 && depth < turns) {
				                empties[depth] = empty;
				                board = updatedBoard;
				                empty = updatedEmpty;
				                depth++;
				                boards[depth] = board;
				                continue DFS;
				            }
				        } else if (count == 3) {
				            // Identifier laquelle des 4 est nulle et combiner les 3 non nulles
				            if (cell1 == 0) { 
				                // non nul : cell3, cell5, cell7
				                // Possibilité 1 : somme de 3
				                updatedBoard = (nextBoard & ~mask3 & ~mask5 & ~mask7) | ((cell3 + cell5 + cell7) << shift4);
				                memoScore = getMemo(updatedBoard, depth);
				                if (memoScore > 0) {
				                    score = (score + memoScore) & 0x3FFFFFFF;
				                } else {
				                    updatedEmpty = nextEmpty | e3 | e5 | e7;
				                    if (updatedEmpty > 0 && depth < turns) {
				                        empties[depth] = empty;
				                        board = updatedBoard;
				                        empty = updatedEmpty;
				                        depth++;
				                        boards[depth] = board;
				                        continue DFS;
				                    }
				                }
				                // Possibilité 2 : les 3 couples parmi les 3 non nulles
				                // a) cell3 + cell5
				                updatedBoard = (nextBoard & ~mask3 & ~mask5) | ((cell3 + cell5) << shift4);
				                memoScore = getMemo(updatedBoard, depth);
				                if (memoScore > 0) {
				                    score = (score + memoScore) & 0x3FFFFFFF;
				                } else {
				                    updatedEmpty = nextEmpty | e3 | e5;
				                    if (updatedEmpty > 0 && depth < turns) {
				                        empties[depth] = empty;
				                        board = updatedBoard;
				                        empty = updatedEmpty;
				                        depth++;
				                        boards[depth] = board;
				                        continue DFS;
				                    }
				                }
				                // b) cell3 + cell7
				                updatedBoard = (nextBoard & ~mask3 & ~mask7) | ((cell3 + cell7) << shift4);
				                memoScore = getMemo(updatedBoard, depth);
				                if (memoScore > 0) {
				                    score = (score + memoScore) & 0x3FFFFFFF;
				                } else {
				                    updatedEmpty = nextEmpty | e3 | e7;
				                    if (updatedEmpty > 0 && depth < turns) {
				                        empties[depth] = empty;
				                        board = updatedBoard;
				                        empty = updatedEmpty;
				                        depth++;
				                        boards[depth] = board;
				                        continue DFS;
				                    }
				                }
				                // c) cell5 + cell7
				                updatedBoard = (nextBoard & ~mask5 & ~mask7) | ((cell5 + cell7) << shift4);
				                memoScore = getMemo(updatedBoard, depth);
				                if (memoScore > 0) {
				                    score = (score + memoScore) & 0x3FFFFFFF;
				                } else {
				                    updatedEmpty = nextEmpty | e5 | e7;
				                    if (updatedEmpty > 0 && depth < turns) {
				                        empties[depth] = empty;
				                        board = updatedBoard;
				                        empty = updatedEmpty;
				                        depth++;
				                        boards[depth] = board;
				                        continue DFS;
				                    }
				                }
				            } else if (cell3 == 0) {
				                // non nul : cell1, cell5, cell7
				                updatedBoard = (nextBoard & ~mask1 & ~mask5 & ~mask7) | ((cell1 + cell5 + cell7) << shift4);
				                memoScore = getMemo(updatedBoard, depth);
				                if (memoScore > 0) {
				                    score = (score + memoScore) & 0x3FFFFFFF;
				                } else {
				                    updatedEmpty = nextEmpty | e1 | e5 | e7;
				                    if (updatedEmpty > 0 && depth < turns) {
				                        empties[depth] = empty;
				                        board = updatedBoard;
				                        empty = updatedEmpty;
				                        depth++;
				                        boards[depth] = board;
				                        continue DFS;
				                    }
				                }
				                // Paires
				                updatedBoard = (nextBoard & ~mask1 & ~mask5) | ((cell1 + cell5) << shift4);
				                memoScore = getMemo(updatedBoard, depth);
				                if (memoScore > 0) {
				                    score = (score + memoScore) & 0x3FFFFFFF;
				                } else {
				                    updatedEmpty = nextEmpty | e1 | e5;
				                    if (updatedEmpty > 0 && depth < turns) {
				                        empties[depth] = empty;
				                        board = updatedBoard;
				                        empty = updatedEmpty;
				                        depth++;
				                        boards[depth] = board;
				                        continue DFS;
				                    }
				                }
				                updatedBoard = (nextBoard & ~mask1 & ~mask7) | ((cell1 + cell7) << shift4);
				                memoScore = getMemo(updatedBoard, depth);
				                if (memoScore > 0) {
				                    score = (score + memoScore) & 0x3FFFFFFF;
				                } else {
				                    updatedEmpty = nextEmpty | e1 | e7;
				                    if (updatedEmpty > 0 && depth < turns) {
				                        empties[depth] = empty;
				                        board = updatedBoard;
				                        empty = updatedEmpty;
				                        depth++;
				                        boards[depth] = board;
				                        continue DFS;
				                    }
				                }
				                updatedBoard = (nextBoard & ~mask5 & ~mask7) | ((cell5 + cell7) << shift4);
				                memoScore = getMemo(updatedBoard, depth);
				                if (memoScore > 0) {
				                    score = (score + memoScore) & 0x3FFFFFFF;
				                } else {
				                    updatedEmpty = nextEmpty | e5 | e7;
				                    if (updatedEmpty > 0 && depth < turns) {
				                        empties[depth] = empty;
				                        board = updatedBoard;
				                        empty = updatedEmpty;
				                        depth++;
				                        boards[depth] = board;
				                        continue DFS;
				                    }
				                }
				            } else if (cell5 == 0) {
				                // non nul : cell1, cell3, cell7
				                updatedBoard = (nextBoard & ~mask1 & ~mask3 & ~mask7) | ((cell1 + cell3 + cell7) << shift4);
				                memoScore = getMemo(updatedBoard, depth);
				                if (memoScore > 0) {
				                    score = (score + memoScore) & 0x3FFFFFFF;
				                } else {
				                    updatedEmpty = nextEmpty | e1 | e3 | e7;
				                    if (updatedEmpty > 0 && depth < turns) {
				                        empties[depth] = empty;
				                        board = updatedBoard;
				                        empty = updatedEmpty;
				                        depth++;
				                        boards[depth] = board;
				                        continue DFS;
				                    }
				                }
				                // Paires : (cell1+cell3), (cell1+cell7), (cell3+cell7)
				                updatedBoard = (nextBoard & ~mask1 & ~mask3) | ((cell1 + cell3) << shift4);
				                memoScore = getMemo(updatedBoard, depth);
				                if (memoScore > 0) {
				                    score = (score + memoScore) & 0x3FFFFFFF;
				                } else {
				                    updatedEmpty = nextEmpty | e1 | e3;
				                    if (updatedEmpty > 0 && depth < turns) {
				                        empties[depth] = empty;
				                        board = updatedBoard;
				                        empty = updatedEmpty;
				                        depth++;
				                        boards[depth] = board;
				                        continue DFS;
				                    }
				                }
				                updatedBoard = (nextBoard & ~mask1 & ~mask7) | ((cell1 + cell7) << shift4);
				                memoScore = getMemo(updatedBoard, depth);
				                if (memoScore > 0) {
				                    score = (score + memoScore) & 0x3FFFFFFF;
				                } else {
				                    updatedEmpty = nextEmpty | e1 | e7;
				                    if (updatedEmpty > 0 && depth < turns) {
				                        empties[depth] = empty;
				                        board = updatedBoard;
				                        empty = updatedEmpty;
				                        depth++;
				                        boards[depth] = board;
				                        continue DFS;
				                    }
				                }
				                updatedBoard = (nextBoard & ~mask3 & ~mask7) | ((cell3 + cell7) << shift4);
				                memoScore = getMemo(updatedBoard, depth);
				                if (memoScore > 0) {
				                    score = (score + memoScore) & 0x3FFFFFFF;
				                } else {
				                    updatedEmpty = nextEmpty | e3 | e7;
				                    if (updatedEmpty > 0 && depth < turns) {
				                        empties[depth] = empty;
				                        board = updatedBoard;
				                        empty = updatedEmpty;
				                        depth++;
				                        boards[depth] = board;
				                        continue DFS;
				                    }
				                }
				            } else { // cell7 == 0
				                // non nul : cell1, cell3, cell5
				                updatedBoard = (nextBoard & ~mask1 & ~mask3 & ~mask5) | ((cell1 + cell3 + cell5) << shift4);
				                memoScore = getMemo(updatedBoard, depth);
				                if (memoScore > 0) {
				                    score = (score + memoScore) & 0x3FFFFFFF;
				                } else {
				                    updatedEmpty = nextEmpty | e1 | e3 | e5;
				                    if (updatedEmpty > 0 && depth < turns) {
				                        empties[depth] = empty;
				                        board = updatedBoard;
				                        empty = updatedEmpty;
				                        depth++;
				                        boards[depth] = board;
				                        continue DFS;
				                    }
				                }
				                // Paires : (cell1+cell3), (cell1+cell5), (cell3+cell5)
				                updatedBoard = (nextBoard & ~mask1 & ~mask3) | ((cell1 + cell3) << shift4);
				                memoScore = getMemo(updatedBoard, depth);
				                if (memoScore > 0) {
				                    score = (score + memoScore) & 0x3FFFFFFF;
				                } else {
				                    updatedEmpty = nextEmpty | e1 | e3;
				                    if (updatedEmpty > 0 && depth < turns) {
				                        empties[depth] = empty;
				                        board = updatedBoard;
				                        empty = updatedEmpty;
				                        depth++;
				                        boards[depth] = board;
				                        continue DFS;
				                    }
				                }
				                updatedBoard = (nextBoard & ~mask1 & ~mask5) | ((cell1 + cell5) << shift4);
				                memoScore = getMemo(updatedBoard, depth);
				                if (memoScore > 0) {
				                    score = (score + memoScore) & 0x3FFFFFFF;
				                } else {
				                    updatedEmpty = nextEmpty | e1 | e5;
				                    if (updatedEmpty > 0 && depth < turns) {
				                        empties[depth] = empty;
				                        board = updatedBoard;
				                        empty = updatedEmpty;
				                        depth++;
				                        boards[depth] = board;
				                        continue DFS;
				                    }
				                }
				                updatedBoard = (nextBoard & ~mask3 & ~mask5) | ((cell3 + cell5) << shift4);
				                memoScore = getMemo(updatedBoard, depth);
				                if (memoScore > 0) {
				                    score = (score + memoScore) & 0x3FFFFFFF;
				                } else {
				                    updatedEmpty = nextEmpty | e3 | e5;
				                    if (updatedEmpty > 0 && depth < turns) {
				                        empties[depth] = empty;
				                        board = updatedBoard;
				                        empty = updatedEmpty;
				                        depth++;
				                        boards[depth] = board;
				                        continue DFS;
				                    }
				                }
				            }
				        } else { // count == 4
				            // Pour réduire le nombre de branches, nous considérons ici 5 possibilités :
				            // Possibilité A : combinaison de toutes les 4
				            updatedBoard = (nextBoard & ~mask1 & ~mask3 & ~mask5 & ~mask7) | (sumAll << shift4);
				            memoScore = getMemo(updatedBoard, depth);
				            if (memoScore > 0) {
				                score = (score + memoScore) & 0x3FFFFFFF;
				            } else {
				                updatedEmpty = nextEmpty | e1 | e3 | e5 | e7;
				                if (updatedEmpty > 0 && depth < turns) {
				                    empties[depth] = empty;
				                    board = updatedBoard;
				                    empty = updatedEmpty;
				                    depth++;
				                    boards[depth] = board;
				                    continue DFS;
				                }
				            }
				            // Possibilité B : pour chaque position, exclure celle-ci et combiner les 3 autres
				            // Exclure cell1
				            updatedBoard = (nextBoard & ~mask3 & ~mask5 & ~mask7) | ((cell3 + cell5 + cell7) << shift4);
				            memoScore = getMemo(updatedBoard, depth);
				            if (memoScore > 0) {
				                score = (score + memoScore) & 0x3FFFFFFF;
				            } else {
				                updatedEmpty = nextEmpty | e3 | e5 | e7;
				                if (updatedEmpty > 0 && depth < turns) {
				                    empties[depth] = empty;
				                    board = updatedBoard;
				                    empty = updatedEmpty;
				                    depth++;
				                    boards[depth] = board;
				                    continue DFS;
				                }
				            }
				            // Exclure cell3
				            updatedBoard = (nextBoard & ~mask1 & ~mask5 & ~mask7) | ((cell1 + cell5 + cell7) << shift4);
				            memoScore = getMemo(updatedBoard, depth);
				            if (memoScore > 0) {
				                score = (score + memoScore) & 0x3FFFFFFF;
				            } else {
				                updatedEmpty = nextEmpty | e1 | e5 | e7;
				                if (updatedEmpty > 0 && depth < turns) {
				                    empties[depth] = empty;
				                    board = updatedBoard;
				                    empty = updatedEmpty;
				                    depth++;
				                    boards[depth] = board;
				                    continue DFS;
				                }
				            }
				            // Exclure cell5
				            updatedBoard = (nextBoard & ~mask1 & ~mask3 & ~mask7) | ((cell1 + cell3 + cell7) << shift4);
				            memoScore = getMemo(updatedBoard, depth);
				            if (memoScore > 0) {
				                score = (score + memoScore) & 0x3FFFFFFF;
				            } else {
				                updatedEmpty = nextEmpty | e1 | e3 | e7;
				                if (updatedEmpty > 0 && depth < turns) {
				                    empties[depth] = empty;
				                    board = updatedBoard;
				                    empty = updatedEmpty;
				                    depth++;
				                    boards[depth] = board;
				                    continue DFS;
				                }
				            }
				            // Exclure cell7
				            updatedBoard = (nextBoard & ~mask1 & ~mask3 & ~mask5) | ((cell1 + cell3 + cell5) << shift4);
				            memoScore = getMemo(updatedBoard, depth);
				            if (memoScore > 0) {
				                score = (score + memoScore) & 0x3FFFFFFF;
				            } else {
				                updatedEmpty = nextEmpty | e1 | e3 | e5;
				                if (updatedEmpty > 0 && depth < turns) {
				                    empties[depth] = empty;
				                    board = updatedBoard;
				                    empty = updatedEmpty;
				                    depth++;
				                    boards[depth] = board;
				                    continue DFS;
				                }
				            }
				            
				            // 6 cas où on sélectionne 2 sur 4
				         // Possibilité 1: combiner cell1 et cell3
				            updatedBoard = (nextBoard & ~(mask1 | mask3)) | ((cell1 + cell3) << shift4);
				            memoScore = getMemo(updatedBoard, depth);
				            if (memoScore > 0) {
				                score = (score + memoScore) & 0x3FFFFFFF;
				            } else {
				                updatedEmpty = nextEmpty | e1 | e3;
				                if (updatedEmpty > 0 && depth < turns) {
				                    empties[depth] = empty;
				                    board = updatedBoard;
				                    empty = updatedEmpty;
				                    depth++;
				                    boards[depth] = board;
				                    continue DFS;
				                }
				            }

				            // Possibilité 2: combiner cell1 et cell5
				            updatedBoard = (nextBoard & ~(mask1 | mask5)) | ((cell1 + cell5) << shift4);
				            memoScore = getMemo(updatedBoard, depth);
				            if (memoScore > 0) {
				                score = (score + memoScore) & 0x3FFFFFFF;
				            } else {
				                updatedEmpty = nextEmpty | e1 | e5;
				                if (updatedEmpty > 0 && depth < turns) {
				                    empties[depth] = empty;
				                    board = updatedBoard;
				                    empty = updatedEmpty;
				                    depth++;
				                    boards[depth] = board;
				                    continue DFS;
				                }
				            }

				            // Possibilité 3: combiner cell1 et cell7
				            updatedBoard = (nextBoard & ~(mask1 | mask7)) | ((cell1 + cell7) << shift4);
				            memoScore = getMemo(updatedBoard, depth);
				            if (memoScore > 0) {
				                score = (score + memoScore) & 0x3FFFFFFF;
				            } else {
				                updatedEmpty = nextEmpty | e1 | e7;
				                if (updatedEmpty > 0 && depth < turns) {
				                    empties[depth] = empty;
				                    board = updatedBoard;
				                    empty = updatedEmpty;
				                    depth++;
				                    boards[depth] = board;
				                    continue DFS;
				                }
				            }

				            // Possibilité 4: combiner cell3 et cell5
				            updatedBoard = (nextBoard & ~(mask3 | mask5)) | ((cell3 + cell5) << shift4);
				            memoScore = getMemo(updatedBoard, depth);
				            if (memoScore > 0) {
				                score = (score + memoScore) & 0x3FFFFFFF;
				            } else {
				                updatedEmpty = nextEmpty | e3 | e5;
				                if (updatedEmpty > 0 && depth < turns) {
				                    empties[depth] = empty;
				                    board = updatedBoard;
				                    empty = updatedEmpty;
				                    depth++;
				                    boards[depth] = board;
				                    continue DFS;
				                }
				            }

				            // Possibilité 5: combiner cell3 et cell7
				            updatedBoard = (nextBoard & ~(mask3 | mask7)) | ((cell3 + cell7) << shift4);
				            memoScore = getMemo(updatedBoard, depth);
				            if (memoScore > 0) {
				                score = (score + memoScore) & 0x3FFFFFFF;
				            } else {
				                updatedEmpty = nextEmpty | e3 | e7;
				                if (updatedEmpty > 0 && depth < turns) {
				                    empties[depth] = empty;
				                    board = updatedBoard;
				                    empty = updatedEmpty;
				                    depth++;
				                    boards[depth] = board;
				                    continue DFS;
				                }
				            }

				            // Possibilité 6: combiner cell5 et cell7
				            updatedBoard = (nextBoard & ~(mask5 | mask7)) | ((cell5 + cell7) << shift4);
				            memoScore = getMemo(updatedBoard, depth);
				            if (memoScore > 0) {
				                score = (score + memoScore) & 0x3FFFFFFF;
				            } else {
				                updatedEmpty = nextEmpty | e5 | e7;
				                if (updatedEmpty > 0 && depth < turns) {
				                    empties[depth] = empty;
				                    board = updatedBoard;
				                    empty = updatedEmpty;
				                    depth++;
				                    boards[depth] = board;
				                    continue DFS;
				                }
				            }
				        }
				    }
				}
			}
			setMemo(board, depth, score);
			depth--;
		}
		System.out.println(score);
	}

	private static final int getMemo(final int board, final int turn) {
		// En C, utiliser __builtin_rotateright32()
		int key = (board ^ Integer.rotateRight(RND, turn)) & (TABLE_WIDTH - 1);
		for (int i = 0; i < TABLE_DEPTH; i++) {
			long v = table[key * TABLE_DEPTH|i];
			if (v == 0) return 0;
			// En C, un cast "(uint) v" devrait être suffisant
			if ((v & 0xFFFFFFFFl) == board) { // Match
				return (int) (v >> 32);
			}
		}
		return 0;
	}
	
	private static final void setMemo(final int board, final int turn, final int score) {
		// En C, utiliser __builtin_rotateright32()
		int key = (board ^ Integer.rotateRight(RND, turn)) & (TABLE_WIDTH - 1);
		for (int i = 0; i < TABLE_DEPTH; i++) {
			long v = table[key * TABLE_DEPTH|i];
			if (v == 0) {
				// To be optimized in C
				table[key * TABLE_DEPTH|i] = ((long) score) << 32 | (long) board; 
				break;
			}
		}
	}
}
