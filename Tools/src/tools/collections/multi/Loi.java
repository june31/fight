package tools.collections.multi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import tools.collections.int32.L;
import tools.collections.object.Lo;
import tools.tuple.OI;

@SuppressWarnings("serial")
public class Loi<T> extends ArrayList<OI<T>> {
	
	public Loi() { }
	public Loi(Iterable<OI<T>> it) { for (OI<T> oi: it) add(oi); }
	public Loi(OI<T>[] t) { for (OI<T> oi: t) add(oi); }
	
	public L integers() {
		L l = new L();
		for (OI<T> oi : this)
			l.add(oi.i);
		return l;
	}

	public Lo<T> objectss() {
		Lo<T> l = new Lo<T>();
		for (OI<T> oi : this)
			l.add(oi.o);
		return l;
	}
	
	public Loi<T> filtered(Predicate<OI<T>> f) {
		Loi<T> l = new Loi<>();
		for (OI<T> oi: this) if (f.test(oi)) l.add(oi);
		return l;
	}

	public Loi<T> mapped(Function<OI<T>, OI<T>> f) {
		Loi<T> l = new Loi<>();
		for (OI<T> oi: this) l.add(f.apply(oi));
		return l;
	}

	public Loi<T> sortedUp() {
		Loi<T> l = new Loi<>(this);
		l.sort((s1, s2) -> s1.i - s2.i);
		return l;
	}

	public Loi<T> sortedDown() {
		Loi<T> l = new Loi<>(this);
		l.sort((s1, s2) -> s2.i - s1.i);
		return l;
	}

	public Loi<T> reversed() {
		Loi<T> l = new Loi<>();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Loi<T> shuffled() {
		Loi<T> l = new Loi<>(this);
		Collections.shuffle(l);
		return l;
	}

	public Loi<T> copy() {
		return new Loi<>(this);
	}
	
	public int sum() {
		int s = 0;
		for (OI<T> oi: this) s += oi.i;
		return s;
	}
	
	public int mul() {
		int p = 1;
		for (OI<T> oi: this) p *= oi.i;
		return p; 
	}
	
	public long mulLong() {
		long p = 1;
		for (OI<T> oi: this) p *= oi.i;
		return p; 
	}
	
	public OI<T> first() {
		return get(0);
	}

	public OI<T> last() {
		return get(size() - 1);
	}
	
	public double mean() {
		return (double) sum() / size();
	}
	
	public double median() {
		Loi<T> l = sortedUp();
		int n = size();
		if (n % 2 == 1) return l.get(n / 2).i;
		return (l.get(n / 2 - 1).i + l.get(n / 2).i) / 2d;
	}

	public OI<T> min() {
		OI<T> min = new OI<T>(null, Integer.MAX_VALUE);
		for (OI<T> oi : this) if (oi.i < min.i) min = oi;
		return min;
	}

	public OI<T> max() {
		OI<T> max = new OI<T>(null, Integer.MIN_VALUE);
		for (OI<T> oi : this) if (oi.i > max.i) max = oi;
		return max;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public OI<T>[] array() {
		OI[] t = new OI[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}

	public Loi<T> added() {
		Map<T, OI<T>> m = new LinkedHashMap<>();
		for (OI<T> oi : this) {
			OI<T> s = m.get(oi.o);
			if (s == null) m.put(oi.o, oi);
			else s.i += oi.i;
		}
		return new Loi<>(m.values());
	}
	
	public void debug() {
		System.err.println(this);
	}
	
	public OI<T> find(T o) {
		for (int i = 0; i < size(); i++) if (get(i).o.equals(o)) return get(i);
		return null;
	}
	
	public OI<T> findOrCreate(T o) {
		for (int i = 0; i < size(); i++) if (get(i).o.equals(o)) return get(i);
		OI<T> oi = new OI<>(o, 0);
		add(oi);
		return oi;
	}
	
	public Loi<T> addOI(T o, int i) {
		add(new OI<>(o, i));
		return this;
	}
}
