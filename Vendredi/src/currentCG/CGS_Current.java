package currentCG;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import tools.chrono.Chrono;
import tools.random.CGRandom;
import tools.random.CGSeedFinder;
import tools.strings.S;

public class CGS_Current {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		S.o(Thread.currentThread().getId());
		CGRandom rnd = new CGRandom();
		Chrono.start();
		int g = 0;
		for (int i = 1; i <= 10_000_000; i++) {
			rnd.setSeed(i);
			if (rnd.nextInt(200) == 42) {
				g++;
			}
		}
		Chrono.stop();
		long a1 = System.currentTimeMillis();
		long b1 = System.nanoTime();
		Random r = ThreadLocalRandom.current();
		long a2 = System.currentTimeMillis();
		long b2 = System.nanoTime();
		S.o(a1, b1, a2, b2);
		
		long l = r.nextLong();
		CGSeedFinder.findSlow(a1, b1, a2, b2, l);
		
		var c = new CGRandom();
		c.setSeed(12);

		r = SecureRandom.getInstance("SHA1PRNG");
		r.setSeed(12);
		
		System.out.println("Check mismatch");
		for (int i = 1; i < 1000; i++) {
			if (r.nextInt(i) != c.nextInt(i)) {
				System.out.println("Mismatch at " + i);
				break;
			}
		}
		System.out.println("Done");
	}
}
