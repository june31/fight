package tools.chrono;

public class Chrono {
	private static long time;
	public static void start() { time = System.currentTimeMillis(); }
	public static void stop() { System.err.println(pretty(System.currentTimeMillis() - time)); }
	public static String pretty(long t) { return to2(t/3_600_000) + ":" + to2((t%3_600_000) / 60_000) + ":" + to2((t%60_000) / 1000) + "." + to3(t%1000); }
	private static String to2(long i) { return i < 10 ? "0" + i : "" + i; }
	private static String to3(long i) { return i < 100 ? (i < 10 ? "00" + i : "0" + i) : "" + i; }
}
