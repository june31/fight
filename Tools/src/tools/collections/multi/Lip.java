package tools.collections.multi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import tools.collections.int32.L;
import tools.collections.pos.Lp;
import tools.tuple.IP;
import tools.tuple.Pos;

@SuppressWarnings("serial")
public class Lip extends ArrayList<IP> {
	public Lip() { }
	public Lip(Iterable<IP> it) { for (IP IP: it) add(IP); }
	public Lip(IP[] t) { for (IP IP: t) add(IP); }
	
	public static Lip of(Iterable<Pos> it) {
		Map<Pos, IP> m = new LinkedHashMap<>();
		for (Pos p : it) {
			IP IP = m.get(p);
			if (IP == null) m.put(p, new IP(1, p));
			else IP.i++;
		}
		return new Lip(m.values()); 
	}		

	public L integers() {
		L l = new L();
		for (IP IP : this)
			l.add(IP.i);
		return l;
	}

	public Lp Positions() {
		Lp l = new Lp();
		for (IP IP : this)
			l.add(IP.p);
		return l;
	}
	
	public Lip filtered(Predicate<IP> f) {
		Lip l = new Lip();
		for (IP IP: this) if (f.test(IP)) l.add(IP);
		return l;
	}

	public Lip mapped(Function<IP, IP> f) {
		Lip l = new Lip();
		for (IP is: this) l.add(f.apply(is));
		return l;
	}

	public Lip sortedUp() {
		Lip l = new Lip(this);
		l.sort((s1, s2) -> s1.i - s2.i);
		return l;
	}

	public Lip sortedDown() {
		Lip l = new Lip(this);
		l.sort((s1, s2) -> s2.i - s1.i);
		return l;
	}

	public Lip reversed() {
		Lip l = new Lip();
		int max = size() - 1;
		for (int i = 0; i <= max; i++) l.add(get(max - i));
		return l;
	}

	public Lip shuffled() {
		Lip l = new Lip(this);
		Collections.shuffle(l);
		return l;
	}

	public Lip copy() {
		return new Lip(this);
	}
	
	public int sum() {
		int s = 0;
		for (IP IP: this) s += IP.i;
		return s;
	}
	
	public int mul() {
		int p = 1;
		for (IP IP: this) p *= IP.i;
		return p; 
	}
	
	public long mulLong() {
		long p = 1;
		for (IP IP: this) p *= IP.i;
		return p; 
	}
	
	public IP first() {
		return get(0);
	}

	public IP last() {
		return get(size() - 1);
	}
	
	public double mean() {
		return (double) sum() / size();
	}
	
	public double median() {
		Lip l = sortedUp();
		int n = size();
		if (n % 2 == 1) return l.get(n / 2).i;
		return (l.get(n / 2 - 1).i + l.get(n / 2).i) / 2d;
	}

	public IP min() {
		IP min = new IP(Integer.MAX_VALUE, null);
		for (IP IP : this) if (IP.i < min.i) min = IP;
		return min;
	}

	public IP max() {
		IP max = new IP(Integer.MIN_VALUE, null);
		for (IP IP : this) if (IP.i > max.i) max = IP;
		return max;
	}

	public IP[] array() {
		IP[] t = new IP[size()];
		for (int i = 0; i < t.length; i++) t[i] = get(i);
		return t;
	}
	public void debug() {
		System.err.println(this);
	}
	
	public Lip addIP(int i, Pos p) {
		add(new IP(i, p));
		return this;
	}
}
