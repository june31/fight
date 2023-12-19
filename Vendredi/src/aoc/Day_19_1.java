package aoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import tools.collections.int32.L;
import tools.scanner.Scan;

public class Day_19_1 {
	
	private static final RuleSet A = new ARuleSet();
	private static final RuleSet R = new RRuleSet();
	
	private static final String[] workflowsIn = Scan.readRawStrings();
	private static final String[] ratingsIn = Scan.readRawStrings();
	
	private static Map<String, RuleSet> ruleSetMap = new HashMap<>();
	static {
		ruleSetMap.put("A", A);
		ruleSetMap.put("R", R);
	}

	private static long s = 0;

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
		
		for (var r: ratingsIn) {
			r = r.substring(1, r.length() - 1);
			Ratings ratings = new Ratings();
			for (var t1: r.split(",")) {
				String[] t2 = t1.split("=");
				ratings.vals.put(t2[0].charAt(0), Integer.parseInt(t2[1]));
			}
			in.process(ratings);
			System.out.println();
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
		
		void process(Ratings r) {
			System.out.print(name + " ");
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
		char cat;
		char op;
		int val;
		RuleSet target;
		
		public Rule(String r) {
			this.name = r;
			String[] t = r.split(":");
			cat = t.length == 2 ? r.charAt(0) : 'a';
			op = r.indexOf('<') != -1 ? '<' : '>';
			val = t.length == 2 ? new L(t[0]).get(0) : -1;
			String rsName = t.length == 2 ? t[1] : t[0];
			target = getRuleSet(rsName);
		}
		
		RuleSet process(Ratings r) {
			if (op == '<' && r.vals.get(cat) < val) return target;
			else if (op == '>' && r.vals.get(cat) > val) return target;
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
		void process(Ratings r) {
			for (int v: r.vals.values()) s += v;
		}
	}
	
	private static class RRuleSet extends RuleSet {
		public RRuleSet() {
			super("R");
		}
		@Override
		void process(Ratings r) { }
	}

	private static class Ratings {
		Map<Character, Integer> vals = new LinkedHashMap<>();
	}
}