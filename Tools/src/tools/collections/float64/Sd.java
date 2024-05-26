package tools.collections.float64;

import java.util.TreeSet;

@SuppressWarnings("serial")
public class Sd extends TreeSet<Double> {
	public Sd() { super(); }
	public Sd(Iterable<Double> it) { for (double d: it) add(d); }
	public Sd(double[] t) { for (double d: t) add(d); }

	public static Sd of(double... t) {
		Sd s = new Sd();
		for (double d : t) s.add(d);
		return s;
	}
}
