package aoc.done;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import tools.collections.int64.Ll;
import tools.scanner.Scan;

public class Day_20_2 {

	private static String[] input = Scan.readRawStrings();
	private static BroadCaster broadcaster; 
	private static Queue<Runnable> actions = new LinkedList<>();
	private static long turn;
	private static Ll susList = new Ll();

	public static void main(String[] args) {

		for (String line: input) {
			String[] t = line.split(" -> ");

			if (t[0].equals("broadcaster")) {
				broadcaster = new BroadCaster(t[0]);
			} else if (t[0].startsWith("%")) {
				new FlipFlop(t[0].substring(1));
			} else if (t[0].startsWith("&")) {
				new Conjunction(t[0].substring(1));
			} else System.out.println("?? " + t[0]);
		}

		for (String line: input) {
			String[] t = line.split(" -> ");

			String name = t[0].charAt(0) == '%' || t[0].charAt(0) == '&' ? t[0].substring(1) : t[0]; 
			Component c = Component.map.get(name);
			if (c == null) c = new FinalComponent(name);

			for (String to : t[1].split(", ")) {
				Component c2 = Component.map.get(to);
				if (c2 == null) c2 = new FinalComponent(to);
				c.to.add(c2);
				c2.from.add(c);
			}
		}

		for (var c: Component.all) c.init();

		for (turn = 1; turn < 5000; turn++) {
			actions.add(() -> { broadcaster.receive(null, 1); });
			while (!actions.isEmpty()) actions.remove().run();
		}
		
		System.out.println(susList.lcm());
	}

	private static abstract class Component {

		static List<Component> all = new ArrayList<>();
		static Map<String, Component> map = new LinkedHashMap<>();

		String name;

		List<Component> from = new ArrayList<>();
		List<Component> to = new ArrayList<>();

		public Component(String name) {
			this.name = name;
			all.add(this);
			map.put(name,  this);
		}

		@Override
		public String toString() {
			return name;
		}

		public abstract void receive(Component source, int value);

		public void init() {}
	}

	private static class BroadCaster extends Component {
		BroadCaster(String name) {
			super(name);
		}

		@Override
		public void receive(Component source, int value) {
			for (var c : to) actions.add(() -> { c.receive(this, 1); });
		}
	}

	private static class FlipFlop extends Component {
		boolean state = false;

		FlipFlop(String name) {
			super(name);
		}
		
		@Override
		public void receive(Component source, int value) {
			if (value == 1) { 
				state = !state;
				for (var c : to) actions.add(() -> { c.receive(this, state ? 2 : 1); });
			}
		}
	}

	private static class Conjunction extends Component {
		Map<Component, Integer> fromMap = new LinkedHashMap<>();
		final boolean suspect;

		Conjunction(String name) {
			super(name);
			suspect = name.equals("vt")
					|| name.equals("sk")
					|| name.equals("xc")
					|| name.equals("kk");
			if (suspect) System.out.println(name + " sus");
		}

		public void receive(Component source, int value) {
			fromMap.put(source, value);
			boolean allHigh = true;
			for (var e : fromMap.entrySet()) {
				allHigh &= e.getValue() == 2;
			}
			boolean fAllHigh = allHigh;
			if (suspect && !fAllHigh) {
				System.out.println(turn + " " + name);
				susList.add(turn);
			}
			for (var c : to) actions.add(() -> { c.receive(this, fAllHigh ? 1 : 2); });
		}

		public void init() {
			for (var c: from) fromMap.put(c,  1);
		}
	}

	private static class FinalComponent extends Component {
		FinalComponent(String name) {
			super(name);
		}
		
		public void receive(Component source, int value) {
			if (value == 1) {
				System.out.println(turn);
			}
		}
	}
}
