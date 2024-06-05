package tools.random;

import java.security.MessageDigest;
import java.util.Random;

import tools.strings.S;

@SuppressWarnings("serial")
public final class CGRandom extends Random {

    private static final MessageDigest digest;
    static {
		try {
			digest = MessageDigest.getInstance("SHA-1");
		} catch (Exception e) {
			throw new InternalError("Can't find SHA-1!");
		}
    }
    private byte[] state = new byte[20];
    private byte[] remainder = new byte[20];
    private int remId;
    private final static byte[] tSeed = new byte[8];
    private final byte[] t = new byte[4];
    
	public CGRandom() { super(0); }

    @Override
    public final void setSeed(long seed) {
    	if (seed == 0) return; // else crash on constructor
        for (int i = 0; i < 8; i++) {
        	tSeed[i] = (byte) seed;
            seed >>= 8;
        }
        state = digest.digest(tSeed);
        remId = 0;
    }
    
    public final int nextInt(int bound) {
        int r = next();
        int m = bound - 1;
        if ((bound & m) == 0) r = (int) ((bound * (long) r) >> 31);
        else for (int u = r; u - (r = Math.floorMod(u, bound)) + m < 0; u = next());
        return r;
    }

   private final int next() {
        nextSHA1();
        int v = 0;
        for (int i = 0; i < 4; i++) v = (v << 8) + (t[i] & 255);
        return v >>> 1;
    }
    
    private final void nextSHA1() {
        if (remId > 0) {
            for (int i = 0; i < 4; i++) {
                t[i] = remainder[remId + i];
                remainder[remId + i] = 0;
            }
		}
        else {
            digest.update(state);
            remainder = digest.digest();
            updateState();
            for (int i = 0; i < 4; i++) {
                t[i] = remainder[i];
                remainder[i] = 0;
            }
        }
        remId = Math.floorMod(remId + 4, 20);
    }
    
    private final void updateState() {
        boolean inc = true;
        int carry = 1;

        for (int i = 0; i < 20; i++) {
            int v = carry + state[i] + remainder[i];
            byte t = (byte) v;
            inc &= state[i] == t;
            state[i] = t;
            carry = v >> 8;
        }

        if (inc) state[0]++;
    }
}
