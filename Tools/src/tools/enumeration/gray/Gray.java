package tools.enumeration.gray;

import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.ToIntFunction;

public class Gray {
	public static <Context> int calc(Context c, long start, long n, ToIntFunction<Context> scorer, ObjLongConsumer<Context> highScorer,
			Consumer<Context> fastSwitch0, Consumer<Context> fastSwitch1, Consumer<Context> fastSwitch2,
			Consumer<Context> fastSwitch3, Consumer<Context> fastSwitch4, Consumer<Context> fastSwitch5, ObjIntConsumer<Context> fastSwitchN) {

		int score;
		int maxScore = 0;
		int grayBit;
		long pos = start;
		long max = n / 64;
		
		for (long l = 0; l < max; l++) {
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 2;
			fastSwitch1.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 4;
			fastSwitch2.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 2;
			fastSwitch1.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 8;
			fastSwitch3.accept(c);

			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 2;
			fastSwitch1.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 4;
			fastSwitch2.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 2;
			fastSwitch1.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 16;
			fastSwitch4.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 2;
			fastSwitch1.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 4;
			fastSwitch2.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 2;
			fastSwitch1.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 8;
			fastSwitch3.accept(c);

			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 2;
			fastSwitch1.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 4;
			fastSwitch2.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 2;
			fastSwitch1.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 32;
			fastSwitch5.accept(c);

			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 2;
			fastSwitch1.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 4;
			fastSwitch2.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 2;
			fastSwitch1.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 8;
			fastSwitch3.accept(c);

			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 2;
			fastSwitch1.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 4;
			fastSwitch2.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 2;
			fastSwitch1.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 16;
			fastSwitch4.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 2;
			fastSwitch1.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 4;
			fastSwitch2.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 2;
			fastSwitch1.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 8;
			fastSwitch3.accept(c);

			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 2;
			fastSwitch1.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 4;
			fastSwitch2.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			pos ^= 2;
			fastSwitch1.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}

			pos ^= 1;
			fastSwitch0.accept(c);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			
			grayBit = Long.numberOfTrailingZeros(pos) + 1;
			pos ^= 1l << grayBit;
			fastSwitchN.accept(c, grayBit);
		}
		return maxScore;
	}
	
	public static <Context> int slowCalc(Context c, long start, long n, ToIntFunction<Context> scorer, ObjLongConsumer<Context> highScorer,
			ObjIntConsumer<Context> fastSwitch) {

		int score;
		int maxScore = 0;
		int grayBit;
		long pos = start;
		long max = n / 2;

		for (long l = 0; l < max; l++) {
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			pos ^= 1;
			fastSwitch.accept(c, 0);
			
			score = scorer.applyAsInt(c);
			if (score > maxScore) {
				maxScore = score;
				highScorer.accept(c, pos);
				if (maxScore == Integer.MAX_VALUE) return maxScore;
			}
			grayBit = Long.numberOfTrailingZeros(pos) + 1;
			pos ^= 1l << grayBit;
			fastSwitch.accept(c, grayBit);
		}
		return maxScore;
	}
}
