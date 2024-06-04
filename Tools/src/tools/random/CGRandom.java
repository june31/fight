package tools.random;

import java.security.MessageDigest;
import java.util.Random;

@SuppressWarnings("serial")
public final class CGRandom extends Random {

    private MessageDigest digest;
    private byte[] remainder = new byte[20];
    private byte[] state = new byte[20];
    private int remCount = 20;

	public CGRandom() {
		super(0);
		try {
			digest = MessageDigest.getInstance("SHA-1");
		} catch (Exception e) {
			throw new InternalError("Can't find SHA-1!");
		}
	}

    @Override
    public void setSeed(long seed) {
    	if (seed == 0) return; // else crash on constructor
        byte[] t = new byte[8];
        for (int i = 0; i < 8; i++) {
            t[i] = (byte) seed;
            seed >>= 8;
        }
        state = digest.digest(t);
        remCount = 0;
    }
    
	@Override
    protected final int next(int bits) {
        int bytes = (bits + 7) / 8;
        byte[] t = new byte[bytes];
        nextBytes(t);
        int v = 0;
        for (int i = 0; i < bytes; i++) v = (v << 8) + (t[i] & 255);
        return v >>> bytes * 8 - bits;
    }
    
    @Override
    public void nextBytes(byte[] result) {
        int index = 0;
        int todo;
        byte[] output = remainder;

        // Use remainder from last time
        int r = remCount;
        if (r > 0) {
            // How many bytes?
            todo = Math.min(result.length - index, 20 - r);
            // Copy the bytes, zero the buffer
            for (int i = 0; i < todo; i++) {
                result[i] = output[r];
                output[r++] = 0;
            }
            remCount += todo;
            index += todo;
        }

        // If we need more bytes, make them.
        while (index < result.length) {
            // Step the state
            digest.update(state);
            output = digest.digest();
            updateState(state, output);

            // How many bytes?
            todo = Math.min((result.length - index), 20);
            // Copy the bytes, zero the buffer
            for (int i = 0; i < todo; i++) {
                result[index++] = output[i];
                output[i] = 0;
            }
            remCount += todo;
        }

        // Store remainder for next time
        remainder = output;
        remCount %= 20;
    }
    
    private static void updateState(byte[] state, byte[] output) {
        int last = 1;
        int v;
        byte t;
        boolean zf = false;

        // state(n + 1) = (state(n) + output(n) + 1) % 2^160;
        for (int i = 0; i < state.length; i++) {
            // Add two bytes
            v = (int)state[i] + (int)output[i] + last;
            // Result is lower 8 bits
            t = (byte)v;
            // Store result. Check for state collision.
            zf = zf | (state[i] != t);
            state[i] = t;
            // High 8 bits are carry. Store for next iteration.
            last = v >> 8;
        }

        // Make sure at least one bit changes!
        if (!zf) {
           state[0]++;
        }
    }
}
