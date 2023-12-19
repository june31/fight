package aoc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import tools.collections.int32.L;
import tools.scanner.Scan;

public class Day_19_2 {

	private static final RuleSet A = new ARuleSet();
	private static final RuleSet R = new RRuleSet();

	private static final String[] workflowsIn = Scan.readRawStrings();

	private static Map<String, RuleSet> ruleSetMap = new LinkedHashMap<>();
	static {
		ruleSetMap.put("A", A);
		ruleSetMap.put("R", R);
	}

	private static boolean accept;

	public static void main(String[] args) {
		var ruleSets = new ArrayList<RuleSet>();
		for (var w: workflowsIn) {
			String[] t1 = w.split("\\{");
			String name = t1[0];
			var rs = getRuleSet(t1[0]);
			ruleSets.add(rs);
			String[] t2 = t1[1].substring(0, t1[1].length() - 1).split(",");
			for (var r: t2) {
				rs.rules.add(new Rule(r));
			}
		}
		var in = ruleSetMap.get("in");

		var sx = new TreeSet<Integer>();
		var sm = new TreeSet<Integer>();
		var sa = new TreeSet<Integer>();
		var ss = new TreeSet<Integer>();
		for (RuleSet rs: ruleSetMap.values()) {
			for (var r: rs.rules) {
				if (r.cat == 'x') sx.add(r.op == '<' ? r.val : r.val + 1);
				if (r.cat == 'm') sm.add(r.op == '<' ? r.val : r.val + 1);
				if (r.cat == 'a') sa.add(r.op == '<' ? r.val : r.val + 1);
				if (r.cat == 's') ss.add(r.op == '<' ? r.val : r.val + 1);
			}
		}
		
		sx.add(0);
		sm.add(0);
		sa.add(0);
		ss.add(0);
		
		L lx = new L(sx);
		L lm = new L(sm);
		L la = new L(sa);
		L ls = new L(ss);
		
		lx.add(4001);
		lm.add(4001);
		la.add(4001);
		ls.add(4001);
		
		System.out.println(lx.size());
		System.out.println(lm.size());
		System.out.println(la.size());
		System.out.println(ls.size());
		
		L vx = new L();
		L vm = new L();
		L va = new L();
		L vs = new L();
		
		for (int i = 0; i < lx.size() - 1; i++) vx.add(lx.get(i+1) - lx.get(i));
		for (int i = 0; i < lm.size() - 1; i++) vm.add(lm.get(i+1) - lm.get(i));
		for (int i = 0; i < la.size() - 1; i++) va.add(la.get(i+1) - la.get(i));
		for (int i = 0; i < ls.size() - 1; i++) vs.add(ls.get(i+1) - ls.get(i));

		long s = 0;
		for (int i = 0; i < lx.size() - 1; i++) {
			System.out.println("a" + i);
			for (int j = 0; j < lm.size() - 1; j++) {
				for (int k = 0; k < la.size() - 1; k++) {
					for (int l = 0; l < ls.size() - 1; l++) {
						in.process(new int[] {lx.get(i), lm.get(j), la.get(k), ls.get(l)});
						if (accept) s += 1l * vx.get(i) * vm.get(j) * va.get(k) * vs.get(l);
					}
				}
			}
		}
		System.out.println(s);	
	}

	private static RuleSet getRuleSet(String name) {
		RuleSet r = ruleSetMap.get(name);
		if (r == null) {
			r = new RuleSet(name);
			ruleSetMap.put(name, r);
		}
		return r;
	}

	private static class RuleSet {
		String name;
		List<Rule> rules = new ArrayList<>();

		public RuleSet(String name) {
			this.name = name;
		}
		@Override
		public String toString() {
			return name;
		}

		void process(int[] r) {
			RuleSet rs = null;
			for (var rule : rules) {
				rs = rule.process(r);
				if (rs != null) break;
			}
			rs.process(r);
		}
	}

	private static class Rule {
		String name;
		int cat;
		char op;
		int val;
		RuleSet target;

		public Rule(String r) {
			this.name = r;
			String[] t = r.split(":");
			cat = getCat(t.length == 2 ? r.charAt(0) : 'a');
			op = r.indexOf('<') != -1 ? '<' : '>';
			val = t.length == 2 ? new L(t[0]).get(0) : -1;
			String rsName = t.length == 2 ? t[1] : t[0];
			target = getRuleSet(rsName);
		}

		private static int getCat(char c) {
			if (c == 'x') return 0;
			if (c == 'm') return 1;
			if (c == 'a') return 2;
			return 3;
		}

		RuleSet process(int[] r) {
			if (op == '<' && r[cat] < val) return target;
			else if (op == '>' && r[cat] > val) return target;
			return null;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	private static class ARuleSet extends RuleSet {

		public ARuleSet() {
			super("A");
		}
		@Override
		void process(int[] r) {
			accept = true;
		}
	}

	private static class RRuleSet extends RuleSet {
		public RRuleSet() {
			super("R");
		}
		@Override
		void process(int[] r) {
			accept = false;
		}
	}

}