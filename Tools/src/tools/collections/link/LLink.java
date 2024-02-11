package tools.collections.link;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import tools.function.IntObjConsumer;
import tools.function.IntObjPredicate;
import tools.structures.graph.link.Link;

@SuppressWarnings("serial")
public class LLink extends ArrayList<Link> {
	public LLink() { super(); }
	public LLink(int capacity) { super(capacity); }
	public LLink(Iterable<Link> it) { for (Link l: it) add(l); }
	public LLink(Link[] t) { for (Link l: t) add(l); }

	public Link g(int i) { return get(i); }
	
	public LLink mapped(Function<Link, Link> f) {
		LLink l = new LLink();
		for (Link i: this) l.add(f.apply(i));
		return l;
	}

	public void foreach(Consumer<Link> c) {
		for (Link i: this) c.accept(i);
	}

	public void foreach(IntObjConsumer<Link> c) {
		for (int i = 0; i < size(); i++) c.accept(i, get(i));
	}
	
	public LLink subbed(int s) { return subbed(s, size(), 1); }
	public LLink subbed(int s, int e) { return subbed(s, e, 1); }
	public LLink subbed(int s, int e, int k) {
		LLink l = new LLink();
		while (s < 0) s += size();
		while (e < 0) e += size();
		if (k > 0) for (int i = s; i < e; i += k) l.add(get(i));
		else for (int i = e-1; i >= s; i += k) l.add(get(i));
		return l;
	}

	public String join() { return join(" "); };
	public String join(String s) {
		StringBuilder sb = new StringBuilder();
		boolean w = false;
		for (Link i: this) {
			if (w) sb.append(s);
			sb.append(i);
			w = true;
		}
		return sb.toString();
	}

	public LLink filtered(Predicate<Link> f) {
		LLink l = new LLink();
		for (Link i: this) if (f.test(i)) l.add(i);
		return l;
	}
	
	public LLink filtered(IntObjPredicate<Link> f) {
		LLink l = new LLink();
		for (int i = 0; i < size(); i++) {
			Link v = get(i);
			if (f.test(i, v)) l.add(v);
		}
		return l;
	}

	public LLink reversed() {
		LLink l = new LLink();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public LLink shuffled() {
		LLink l = new LLink(this);
		Collections.shuffle(l);
		return l;
	}

	public Link[] array() {
		Link[] t = new Link[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}
	
	public LLink distinct() {
		return new LLink(new LinkedHashSet<>(this)); 
	}
}
