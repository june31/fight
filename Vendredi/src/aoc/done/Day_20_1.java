package aoc.done;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import tools.scanner.Scan;

public class Day_20_1 {

	private static String[] input = Scan.readRawLines();
	private static BroadCaster broadcaster; 
	private static Queue<Runnable> actions = new LinkedList<>();
	private static int turn;

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

		for (turn = 1; turn <= 1000; turn++) {
			actions.add(() -> { broadcaster.receive(null, 1); });
			while (!actions.isEmpty()) actions.remove().run();
			System.out.println();
		}
		System.out.println(Component.low + " " + Component.high + " " + Component.low * Component.high);
	}

	private static class Component {

		static List<Component> all = new ArrayList<>();
		static Map<String, Component> map = new LinkedHashMap<>();

		static long low = 0;
		static long high = 0;

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

		public void receive(Component source, int value) {
			System.out.println((source == null ? "button" : source.toString()) + " " + g(value) + " " + this);
			if (value == 1) low++; else high++;
		}

		protected String g(int value) {
			if (value == 1) return "-low->";
			if (value == 2) return "-high->";
			throw new IllegalArgumentException("" + value);
		}

		public void init() {}
	}

	private static class BroadCaster extends Component {
		BroadCaster(String name) {
			super(name);
			//System.out.println("BC: " + name);
		}

		@Override
		public void receive(Component source, int value) {
			super.receive(source, value);
			for (var c : to) actions.add(() -> { c.receive(this, 1); });
		}
	}

	private static class FlipFlop extends Component {
		boolean state = false;

		FlipFlop(String name) {
			super(name);
			//System.out.println("FF: " + name);
		}
		
		@Override
		public void receive(Component source, int value) {
			super.receive(source, value);
			if (value == 1) { 
				state = !state;
				for (var c : to) actions.add(() -> { c.receive(this, state ? 2 : 1); });
			}
		}
	}

	private static class Conjunction extends Component {
		Map<Component, Integer> fromMap = new LinkedHashMap<>();

		Conjunction(String name) {
			super(name);
			//System.out.println("CO: " + name);
		}

		public void receive(Component source, int value) {
			super.receive(source, value);
			fromMap.put(source, value);
			boolean allHigh = true;
			for (var e : fromMap.entrySet()) {
				allHigh &= e.getValue() == 2;
			}
			boolean fAllHigh = allHigh;
			for (var c : to) actions.add(() -> { c.receive(this, fAllHigh ? 1 : 2); });
		}

		public void init() {
			for (var c: from) fromMap.put(c,  1);
		}
	}

	private static class FinalComponent extends Component {
		FinalComponent(String name) {
			super(name);
			System.out.println("??: " + name);
		}
	}
}
