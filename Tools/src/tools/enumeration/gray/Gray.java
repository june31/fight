package tools.enumeration.gray;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

import tools.tuple.LI;

public class Gray {
	
	public static LI fastCalc(int bits, IntSupplier scorer,
			Runnable fastSwitch0, Runnable fastSwitch1, Runnable fastSwitch2,
			Runnable fastSwitch3, Runnable fastSwitch4, Runnable fastSwitch5, IntConsumer fastSwitchN) {
		
		return fastCalc(bits, scorer, null, fastSwitch0, fastSwitch1, fastSwitch2,
				fastSwitch3, fastSwitch4, fastSwitch5, fastSwitchN);
	}
	
	public static LI fastCalc(int bits, IntSupplier scorer, Consumer<LI> highScorer,
			Runnable fastSwitch0, Runnable fastSwitch1, Runnable fastSwitch2,
			Runnable fastSwitch3, Runnable fastSwitch4, Runnable fastSwitch5, IntConsumer fastSwitchN) {
		
		if (bits < 5) return calc(bits, scorer, fastSwitchN);
		int score;
		int highScore = Integer.MIN_VALUE;
		long highGray = 0;
		long gray = 0;
		long max = (1l << bits) / 64;
		
		for (long l = 0; l < max; l++) {
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 2;
			fastSwitch1.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 4;
			fastSwitch2.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 2;
			fastSwitch1.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 8;
			fastSwitch3.run();

			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 2;
			fastSwitch1.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 4;
			fastSwitch2.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 2;
			fastSwitch1.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 16;
			fastSwitch4.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 2;
			fastSwitch1.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 4;
			fastSwitch2.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 2;
			fastSwitch1.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 8;
			fastSwitch3.run();

			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 2;
			fastSwitch1.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 4;
			fastSwitch2.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 2;
			fastSwitch1.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 32;
			fastSwitch5.run();

			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 2;
			fastSwitch1.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 4;
			fastSwitch2.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 2;
			fastSwitch1.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 8;
			fastSwitch3.run();

			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 2;
			fastSwitch1.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 4;
			fastSwitch2.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 2;
			fastSwitch1.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 16;
			fastSwitch4.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 2;
			fastSwitch1.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 4;
			fastSwitch2.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 2;
			fastSwitch1.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 8;
			fastSwitch3.run();

			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 2;
			fastSwitch1.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 4;
			fastSwitch2.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			gray ^= 2;
			fastSwitch1.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}

			gray ^= 1;
			fastSwitch0.run();
			
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				LI li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			
			int grayBit = Long.numberOfTrailingZeros(gray) + 1;
			gray ^= 1l << grayBit;
			if (grayBit != bits) fastSwitchN.accept(grayBit);
		}
		return new LI(highGray, highScore);
	}

	public static LI calc(int bits, IntSupplier scorer, IntConsumer fastSwitch) {
		return calc(bits, scorer, null, fastSwitch);
	}
	public static LI calc(int bits, IntSupplier scorer, Consumer<LI> highScorer, IntConsumer fastSwitch) {
		int score;
		int highScore = scorer.getAsInt();
		long highGray = 0;
		LI li = new LI(highGray, highScore);
		if (highScorer != null) highScorer.accept(li);
		if (highScore == Integer.MAX_VALUE) return li;
		long gray = 0;
		long max = (1l << bits) / 2;

		for (long l = 1; l < max; l++) {
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			gray ^= 1;
			fastSwitch.accept(0);
			score = scorer.getAsInt();
			if (score > highScore) {
				highScore = score;
				highGray = gray;
				li = new LI(highGray, highScore);
				if (highScorer != null) highScorer.accept(li);
				if (highScore == Integer.MAX_VALUE) return li;
			}
			int grayBit = Long.numberOfTrailingZeros(gray) + 1;
			gray ^= 1l << grayBit;
			fastSwitch.accept(grayBit);
		}
		return new LI(highGray, highScore);
	}

    public static long fromGray(long gray) {
        long binary = gray;
        while (gray > 0) {
            gray >>= 1;
            binary ^= gray;
        }
        return binary;
    }

    public static long toGray(long num) {
        return num ^ (num >> 1);
    }
    
    public static int fromGray(int gray) {
        int binary = gray;
        while (gray > 0) {
            gray >>= 1;
            binary ^= gray;
        }
        return binary;
    }

    public static int toGray(int num) {
        return num ^ (num >> 1);
    }

}
