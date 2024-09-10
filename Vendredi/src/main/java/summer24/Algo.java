package summer24;

import tools.math.Num;
import tools.strings.S;

public class Algo extends Common {

	public static int play() {
		simulate(0);
		S.e(dir(moveH[0]), dir(moveB[0]), dir(moveD[0]));
		//S.e(bestH[0], bestB[0], bestD[0]);
		return choose();
	}

	private final static void simulate(int player) {
		final int start = player * MAX_DEPTH;
		bestH[player] = Integer.MIN_VALUE;
		bestB[player] = Integer.MIN_VALUE;
		bestD[player] = Integer.MIN_VALUE;
		for (int d1 = 0; d1 < 4; d1++) {
			simulate(player, start, 0, d1);
			for (int d2 = 0; d2 < 4; d2++) {
				simulate(player, start, 1, d2);
				for (int d3 = 0; d3 < 4; d3++) {
					simulate(player, start, 2, d3);
					for (int d4 = 0; d4 < 4; d4++) {
						simulate(player, start, 3, d4);
						for (int d5 = 0; d5 < 4; d5++) {
							simulate(player, start, 4, d5);
							for (int d6 = 0; d6 < 4; d6++) {
								simulate(player, start, 5, d6);
								for (int d7 = 0; d7 < 4; d7++) {
									simulate(player, start, 6, d7);
									if (scoreH[player] > bestH[player]) {
//										int a = scoreH[player];
//										int y = 20 - (a >> 6);
//										int z = a & 31;
//										int b = bestH[player];
										bestH[player] = scoreH[player];
										moveH[player] = d1;
									}
									if (scoreB[player] > bestB[player]) {
										bestB[player] = scoreB[player];
										moveB[player] = d1;
									}
									if (scoreD[player] > bestD[player]) {
										bestD[player] = scoreD[player];
										moveD[player] = d1;
									}
								}
							}
						}
					}
				}
			}


			// Roller
			Roller r = rollers[player];
			if (r.stun >= 0) {
				if (Roller.eta == 1) {
					mR1[player] = Roller.dir32;
					mR2[player] = Roller.dir20;
					mR3[player] = Roller.dir21;
				} else {
					int dead = (Roller.dead[(r.pos + 1) % 10] ? 1 : 0)
							| (Roller.dead[(r.pos + 2) % 10] ? 2 : 0)
							| (Roller.dead[(r.pos + 3) % 10] ? 4 : 0);
				
					int dir1_2 = r.stun > 0 ? Roller.dir1m1 : Roller.dir21;
					int dir2_1 = r.stun > 0 ? Roller.dir21 : Roller.dir1m1;
					switch (dead) {
					case 0: mR1[player] = Roller.dir20; mR2[player] = dir1_2; mR3[player] = dir2_1; break;
					case 1: mR1[player] = Roller.dir20; mR2[player] = Roller.dir21; mR3[player] = Roller.dir32; break;
					case 2: mR1[player] = Roller.dir1m1; mR2[player] = Roller.dir32; mR3[player] = Roller.dir20; break;
					case 3: mR1[player] = Roller.dir32; mR2[player] = Roller.dir20; mR3[player] = dir1_2; break;
					case 4: mR1[player] = Roller.dir20; mR2[player] = dir1_2; mR3[player] = dir2_1; break;
					case 5: mR1[player] = Roller.dir20; mR2[player] = Roller.dir21; mR3[player] = Roller.dir1m1; break;
					default: mR1[player] = Roller.dir1m1; mR2[player] = Roller.dir20; mR3[player] = Roller.dir32; break; // 6
					}
				}
			}
		}
	}

	private final static void simulate(int player, int start, int depth, int dir) {
		// Hurdle
		Hurdle h0 = hurdles[start + depth];
		Hurdle h1 = hurdles[start + depth + 1];
		h1.active = h0.active && h0.pos < 30;
		if (h1.active) {
			int w;
			int s;
			if (h0.stun > 0) {
				h1.stun = h0.stun - 1;
				h1.pos = h0.pos;
			} else {
				switch (dir) {
				case L:
					h1.pos = h0.pos + 1;
					h1.stun = ((Hurdle.field >> h1.pos & 1) == 0) ? 0 : 3;
					break;
				case U:
					h1.pos = h0.pos + 2;
					h1.stun = ((Hurdle.field >> h1.pos & 1) == 0) ? 0 : 3;
					break;
				case R:
					w = (Hurdle.field >> h0.pos) & 14;
					s = Integer.numberOfTrailingZeros(w);
					if (s == 32) {
						h1.pos = h0.pos + 3;
						h1.stun = 0;
					} else {
						h1.pos = h0.pos + s;
						h1.stun = 3;
					}
					break;
				default: // D
					w = (Hurdle.field >> h0.pos) & 6;
					s = Integer.numberOfTrailingZeros(w);
					if (s == 32) {
						h1.pos = h0.pos + 2;
						h1.stun = 0;
					} else {
						h1.pos = h0.pos + s;
						h1.stun = 3;
					}
				}
			}
			scoreH[player] = h1.pos >= 30 ? ((20 - depth) << 6) | 31 : ((20 - depth) << 6) | h1.pos;
		}

		// Bow
		Bow b0 = bows[start + depth];
		Bow b1 = bows[start + depth + 1];
		if (b0.active) {
			switch (dir) {
			case L:
				b1.x = b0.x - b0.wind;
				b1.y = b0.y;
				if (b1.x < -20) b1.x = -20;
				break;
			case U:
				b1.x = b0.x;
				b1.y = b0.y - b0.wind;
				if (b1.y < -20) b1.y = -20;
				break;
			case R:
				b1.x = b0.x + b0.wind;
				b1.y = b0.y;
				if (b1.x > 20) b1.x = 20;
				break;
			default: // D
				b1.x = b0.x;
				b1.y = b0.y + b0.wind;
				if (b1.y > 20) b1.y = 20;
			}
			//S.e(dir(dir) + " " + b1);
			int sb = - b1.x * b1.x - b1.y * b1.y;
			scoreB[player] = sb;
		}

		// Dive
		int score;
		Dive d0 = dives[start + depth];
		Dive d1 = dives[start + depth + 1];
		d1.active = d0.active && d1.eta > 0;
		if (d1.active) {
			if (dir == d0.pattern) {
				d1.mul = d0.mul + 1; 
				score = d0.points + d1.mul;
			} else {
				d1.mul = 0;
				score = d0.points;
			}
			d1.points = score;
			scoreD[player] = score;
		}
	}
	
	private static int choose() {
		int sH = scores[0][1] * 3 + scores[0][2];
		int sB = scores[0][4] * 3 + scores[0][5];
		int sR = scores[0][7] * 3 + scores[0][8];
		int sD = scores[0][10] * 3 + scores[0][11];
		int min = Num.min(sH, sB, sR, sD);

		boolean useETA = turn <= 50
				| turn <= 60 && min >= 2
				| turn <= 65 && min >= 3
				| turn <= 70 && min >= 4
				| turn <= 75 && min >= 5
				| min >= 6;
				
		if (useETA) {
			int etaH = (30 - hurdles[0].pos) / 2;
			int etaB = bows[0].eta;
			int etaR = Roller.eta;
			int etaD = dives[0].eta;
			
			int mode;
			if (etaH <= etaB && etaH <= etaR && etaH <= etaD) {
				mode = 0;
			} else if (etaB <= etaR && etaB <= etaD) {
				mode = 1;
			} else if (etaR <= etaD) {
				mode = 2;
			} else {
				mode = 3;
			}
			
			boolean change = false;
			if (mode == 0) {
				Hurdle e0 = hurdles[0];
				Hurdle e1 = hurdles[MAX_DEPTH];
				Hurdle e2 = hurdles[2 * MAX_DEPTH];
				if (e1.stun == 0 && e0.pos <= e1.pos - 3
						|| e2.stun == 0 && e0.pos < e2.pos - 3
						|| e0.stun == 0 && e1.pos < e0.pos - 6
						   && e0.stun == 0 && e2.pos < e0.pos - 6) {
					change = true;
				}
				if (change) {
					e0.pos -= 200;
					return choose();
				}
			} else if (mode == 1) {
				if (bows[0].eta > 5) {
					bows[0].eta += 100;
					return choose();
				}
			} else if (mode == 2) {
				Roller e0 = rollers[0];
				Roller e1 = rollers[MAX_DEPTH];
				Roller e2 = rollers[2 * MAX_DEPTH];
				if (e1.stun >= 0 && e0.pos <= e1.pos - 5
						|| e2.stun >= 0 && e0.pos < e2.pos - 5
						|| e0.stun >= 0 && e1.pos < e0.pos - 6
						   && e0.stun >= 0 && e2.pos < e0.pos - 6) {
					change = true;
				}
				if (change) {
					Roller.eta += 100;
					return choose();
				}
			} else {
				Dive e0 = dives[0];
				Dive e1 = dives[MAX_DEPTH];
				Dive e2 = dives[2 * MAX_DEPTH];
				if (e0.points > e1.points + e1.eta * (e1.eta + 1 + e1.mul) / 2
					&& e0.points > e2.points + e2.eta * (e2.eta + 1 + e2.mul) / 2) {
					change = true;
				}
				if (e1.points > e0.points + e0.eta * (e0.eta + 1 + e0.mul) / 2
						|| e2.points > e0.points + e0.eta * (e0.eta + 1 + e0.mul) / 2) {
					change = true;
				}
				if (change) {
					e0.eta += 100;
					return choose();
				}
			}
			
			if (mode == 0) {
				S.e("Hurdle");
				int res = moveH[0];
				if (res == D) {
					Roller r = rollers[0];
					if (r.stun >= 0) {
						if (mR1[0] == U) {
							res = U;
						}
					} else if (dives[0].pattern == U) {
						res = U;
					}
				}	
				return res;
			} else if (mode == 1) {
				S.e("Bow");
				return moveB[0];
			} else if (mode == 2) {
				S.e("Roller");
				return mR1[0];
			} else {
				S.e("Dive");
				return moveD[0];
			}
		}
		
		if (sH <= sB && sH <= sR && sH <= sD) {
			S.e("Hurdle");
			return moveH[0];
		}
		else if (sB <= sR && sB <= sD) {
			S.e("Bow");
			return moveB[0];
		}
		else if (sR <= sD) {
			S.e("Roller");
			return mR1[0];
		}
		else {
			S.e("Dive");
			return moveD[0];
		}
	}
}

