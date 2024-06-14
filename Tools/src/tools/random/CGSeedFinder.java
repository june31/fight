package tools.random;

public class CGSeedFinder {

	static final int probe = 0x9e3779b9;

	
	public static long findSeed(long curMillis, long curNanos, long millis, long ref) {
		curMillis -= millis;
		curNanos -= millis * 1_000_000;
		long min = curMillis - 10000;
		for (long i = curMillis; i > min; i--) {
			for (long j = 0; j <= 10000; j += 100) {
				long seeder = murmurHash3(i) ^ murmurHash3(j);
				long s = murmurHash3(seeder);	
				if (nextLong(s) == ref){
					System.out.println("Seed found: " + s + " i=" + i + " j=" + j);
					return s;
				}
			}

		}
		return 0l;
		//seed = RandomSupport.mixMurmur64(seeder.getAndAdd(SEEDER_INCREMENT));
	}
	
	public static void findSlow(long a1, long b1, long a2, long b2, long l) {
		for (long i = a1; i <= a2; i++) {
			for (long j = b1; j <= b2; j += 100) {
				long seeder = murmurHash3(i) ^ murmurHash3(j);
				long s = murmurHash3(seeder);	
				if (nextLong(s) == l) {
					System.out.println("Seed found: " + s + " i=" + i + " j=" + j);
					return;
				}
			}

		}
		//seed = RandomSupport.mixMurmur64(seeder.getAndAdd(SEEDER_INCREMENT));
	}

	public static void find(long l) {
		long m = System.currentTimeMillis() + 1;
		while (m != System.currentTimeMillis());
		long n = System.nanoTime();
		for (int i = 0; i < 10000; i++) {
			for (int j = -2; j <= 2; j++) {
				long seeder = murmurHash3(m - i) ^ murmurHash3(n + j);
				long s = murmurHash3(seeder + 0xbb67ae8584caa73bL);	
				if (nextSeed(s) == l) {
					System.out.println("Seed found: " + s);
					return;
				}
			}

		}
		//seed = RandomSupport.mixMurmur64(seeder.getAndAdd(SEEDER_INCREMENT));
	}

	private static final long murmurHash3(long l) {
		l = (l ^ (l >>> 33)) * 0xff51afd7ed558ccdL;
		l = (l ^ (l >>> 33)) * 0xc4ceb9fe1a85ec53L;
		return l ^ (l >>> 33);
	}

	private static final long nextSeed(long s) {
		return s + (Thread.currentThread().getId() << 1) + 0x9e3779b97f4a7c15L;
	}

	public static final long nextLong(long s) {
		return mix64(nextSeed(s));
	}

	private static final long mix64(long z) {
		z = (z ^ (z >>> 33)) * 0xff51afd7ed558ccdL;
		z = (z ^ (z >>> 33)) * 0xc4ceb9fe1a85ec53L;
		return z ^ (z >>> 33);
	}
}
