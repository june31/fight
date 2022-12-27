package tools.tuple;

public class IPair {
	public int a;
	public int b;
	public IPair() {}
	public IPair(int pa, int pb) { a = pa; b = pb; }
	public String toString() { return "[" + a + ":" + b + "]"; }
}
