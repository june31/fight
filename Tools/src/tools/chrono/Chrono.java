package tools.chrono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.BooleanSupplier;

public class Chrono {
	private static long time;
	public static void start() { time = System.currentTimeMillis(); }
	public static void stop() { System.err.println(pretty(System.currentTimeMillis() - time)); }
	private static String pretty(long t) { return to2(t/3_600_000) + ":" + to2((t%3_600_000) / 60_000) + ":" + to2((t%60_000) / 1000) + "." + to3(t%1000); }
	private static String to2(long i) { return i < 10 ? "0" + i : "" + i; }
	private static String to3(long i) { return i < 100 ? (i < 10 ? "00" + i : "0" + i) : "" + i; }
	public static void time() { System.err.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH.mm.ss"))); }
	public static void play(long millis, Runnable r) {
		long t = System.currentTimeMillis();
		while (System.currentTimeMillis() - t < millis) r.run();
	}
	public static void play(long millis, BooleanSupplier b) {
		long t = System.currentTimeMillis();
		while (System.currentTimeMillis() - t < millis) if (!b.getAsBoolean()) break;
	}
	public static void wait(int l) { try { Thread.sleep(l); } catch (InterruptedException e) {} }
}
