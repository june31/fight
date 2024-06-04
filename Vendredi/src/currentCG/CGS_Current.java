package currentCG;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import tools.random.CGRandom;
import tools.strings.S;

public class CGS_Current {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		var c = new CGRandom();
		c.setSeed(12);
		S.o(c.nextInt(1000));
		S.o(c.nextInt(1000));
		S.o(c.nextInt(100));

		SecureRandom r = SecureRandom.getInstance("SHA1PRNG");
		r.setSeed(12);
		S.o(r.nextInt(1000));
		S.o(r.nextInt(1000));
		S.o(r.nextInt(100));
		S.o();
	}
}
