package tools.bit;

public class BitMask {
	final long[] mask;
	public BitMask(int size) { mask = new long[(size + 63) / 64]; }
	public void set(int pos) { mask[pos / 64] |= 1l << Math.floorMod(pos, 64); }
	public void clear(int pos) { mask[pos / 64] &= ~(1l << Math.floorMod(pos, 64)); }
	public void xor(int pos) { mask[pos / 64] ^= 1l << Math.floorMod(pos, 64); }
	public boolean get(int pos) { return (mask[pos / 64] & 1l << Math.floorMod(pos, 64)) != 0; }
}
