package tools.collections.segment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;

import tools.function.IntObjConsumer;
import tools.function.IntObjPredicate;
import tools.function.ToBooleanFunction;
import tools.tuple.Range;

@SuppressWarnings("serial")
public class LRange extends ArrayList<Range> {
	
	public LRange() { super(); }
	public LRange(int capacity) { super(capacity); }
	public LRange(Iterable<Range> it) { for (Range p: it) add(p); }
	public LRange(Range[] t) { for (Range i: t) add(i); }
	public LRange(int n, IntFunction<Range> o) { for (int i = 0; i < n; i++) add(o.apply(i)); }
	
	public static LRange of(Range... t) {
		LRange l = new LRange();
		for (Range p: t) l.add(p);
		return l;
	}

	public void add(long a, long b) { add(new Range(a, b)); }
	
	public LRange mapped(Function<Range, Range> f) {
		LRange l = new LRange();
		for (Range p: this) l.add(f.apply(p));
		return l;
	}

	public void foreach(Consumer<Range> c) {
		for (Range p: this) c.accept(p);
	}

	public void foreach(IntObjConsumer<Range> c) {
		for (int i = 0; i < size(); i++) c.accept(i, get(i));
	}
	
	public LRange subbed(int s) { return subbed(s, size(), 1); }
	public LRange subbed(int s, int e) { return subbed(s, e, 1); }
	public LRange subbed(int s, int e, int k) {
		LRange l = new LRange();
		while (s < 0) s += size();
		while (e < 0) e += size();
		if (k > 0) for (int i = s; i < e; i++) l.add(get(i));
		else for (int i = e-1; i >= s; i++) l.add(get(i));
		return l;
	}

	public String join() { return join(", "); };
	public String join(String s) {
		StringBuilder sb = new StringBuilder();
		boolean w = false;
		for (Range i: this) {
			if (w) sb.append(s);
			sb.append(i);
			w = true;
		}
		return sb.toString();
	}

	public LRange filtered(ToBooleanFunction<Range> f) {
		LRange l = new LRange();
		for (Range p: this) if (f.applyAsBoolean(p)) l.add(p);
		return l;
	}
	
	public LRange filtered(IntObjPredicate<Range> f) {
		LRange l = new LRange();
		for (int i = 0; i < size(); i++) {
			Range p = get(i);
			if (f.test(i, p)) l.add(p);
		}
		return l;
	}

	public LRange reversed() {
		LRange l = new LRange();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public LRange sorted() {
		LRange l = new LRange(this);
		Collections.sort(l);
		return l;
	}
	
	public LRange shuffled() {
		LRange l = new LRange(this);
		Collections.shuffle(l);
		return l;
	}

	public LRange copy() {
		return new LRange(this);
	}

	public Range[] array() {
		Range[] t = new Range[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}
	
	public void debug() {
		System.err.println(this);
	}
	
	public LRange distinct() {
		return new LRange(new LinkedHashSet<>(this)); 
	}
	
	public LRange flattened() {
		LRange lr = sorted();
		int n = size();
		for (int i = 0; i < n; i++) {
			Range m = lr.get(i);
			long max = m.b;
			for (int j = i + 1; j < n; j++) {
				Range o = lr.get(j);
				if (o.a <= max) {
					i++;
					if (max < o.b) max = o.b;
				} else break;
			}
			m.b = max;
			lr.add(m);
		}
		return lr;
	}
}
