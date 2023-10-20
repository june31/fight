package tooltests.enumeration;

import java.util.Random;

import tools.chrono.Chrono;
import tools.enumeration.gray.Gray;

public class _GrayTest {
	public static void main(String[] args) {
		
		Random rnd = new Random(0);
		long[] switches = new long[64];
		for (int i = 0; i < 64; i++) switches[i] = rnd.nextLong();
		
		long n = 1l << 32;

		long switches0 = switches[0];
		long switches1 = switches[1];
		long switches2 = switches[2];
		long switches3 = switches[3];
		long switches4 = switches[4];
		long switches5 = switches[5];
		
		Context c1 = new Context();
		Context c2 = new Context();
		
		Chrono.start();
		Gray.calc(c1, 0, n,
				ctx -> Long.bitCount(ctx.litBulbs),
				(ctx, pos) -> { ctx.maxSwitchPos = pos; },
				ctx -> { ctx.litBulbs ^= switches0; },
				ctx -> { ctx.litBulbs ^= switches1; },
				ctx -> { ctx.litBulbs ^= switches2; },
				ctx -> { ctx.litBulbs ^= switches3; },
				ctx -> { ctx.litBulbs ^= switches4; },
				ctx -> { ctx.litBulbs ^= switches5; },
				(ctx, i) -> { ctx.litBulbs ^= switches[i]; }
				);
		Chrono.stop();
		System.out.println(c1.maxSwitchPos);

		Chrono.start();
		Gray.slowCalc(c2, 0, n,
				ctx -> Long.bitCount(ctx.litBulbs),
				(ctx, pos) -> { ctx.maxSwitchPos = pos; },
				(ctx, i) -> { ctx.litBulbs ^= switches[i]; }
				);
		Chrono.stop();
		System.out.println(c2.maxSwitchPos);
	}
	 
	private static class Context {
		long maxSwitchPos;
		long litBulbs;
	}
}
