package tools.collections.multi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import tools.collections.int32.L;

@SuppressWarnings("serial")
public class LAi extends ArrayList<int[]> {

	public LAi() {}
	public LAi(Collection<int[]> c) { super(c); }
	
	public static LAi fromLs(Collection<String> ls) {
		LAi LAi = new LAi();
		for (String s: ls) LAi.add(new L(s).array());
		return LAi;
	}
	
	public static LAi fromLsChars(Collection<String> ls) {
		LAi LAi = new LAi();
		for (String s: ls) {
			int[] a = new int[s.length()];
			int i = 0;
			for (char c: s.toCharArray()) a[i++] = c;
			LAi.add(a);
		}
		return LAi;
	}

	public static LAi fromLsDigits(Collection<String> ls) {
		LAi LAi = new LAi();
		for (String s : ls) {
			int[] a = new int[s.length()];
			int i = 0;
			for (char c : s.toCharArray()) a[i++] = c - '0';
			LAi.add(a);
		}
		return LAi;
	}
	
	public int[] first() {
		return get(0);
	}
	
	public int[] last() {
		return get(size() - 1);
	}
	
	public LAi copy() {
		LAi lai = new LAi();
		for (int[] t: this) lai.add(t.clone());
		return lai;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("( ");
		boolean first = true;
		for (int[] t: this) {
			if (first) {
				first = false;
				sb.append(Arrays.toString(t));
			} else sb.append(", " + Arrays.toString(t));
		}
		sb.append(" )");
		return sb.toString();
	}
}
