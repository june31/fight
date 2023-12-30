package aoc.done;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import tools.bfs.BFSGraph;
import tools.scanner.Scan;
import tools.structures.graph.Graph;


public class Day_25_1 {
	
	private static String[] input = Scan.readRawStrings();
	private static List<Node> l;
	
	public static void main(String[] args) {

		Map<String, Node> map = new LinkedHashMap<>();
		for (var in: input) {
			String[] t = in.split(": ");
			Node n = new Node();
			n.name = t[0];
			if (!map.containsKey(t[0])) map.put(n.name, n);
			for (String u: t[1].split(" ")) {
				n = new Node();
				n.name = u;
				if (!map.containsKey(u)) map.put(n.name, n);
			}
			
		}
		for (var in: input) {
			String[] t = in.split(": ");
			Node n = map.get(t[0]);
			for (String u: t[1].split(" ")) {
				n.links.add(map.get(u));
				map.get(u).links.add(n);
			}
		}

		l = new ArrayList<>(map.values());
		for (int i = 0; i < l.size(); i++) {
			l.get(i).id = i;
		}

		System.out.println("A");
		int max = diffuse(null, null, null, null, null, null);
		System.out.println(max);
		for (int i = 0; i < l.size(); i++) {
			Node ni = l.get(i);
			if (ni.links.size() > 3) continue;
			for (int j = i; j < l.size(); j++) {
				Node nj = l.get(j);
				if (nj.links.size() > 4) continue;
				for (int k = j; k < l.size(); k++) {
					Node nk = l.get(k);
					if (nk.links.size() > 4) continue;
					for (Node ci: ni.links) {
						for (Node cj: nj.links) {
							for (Node ck: nk.links) {
								int h = diffuse(ni, ci, nj, cj, nk, ck);
								if (h != max) {
									System.out.println(h);
									System.out.println(max - h);
									System.out.println(h * (max - h));
								}
							}
						}
					}
				}
			}			
		}
		
		long s = 0;
		
		
		System.out.println(s);
	}

	private static int diffuse(Node ni, Node ci, Node nj, Node cj, Node nk, Node ck) {
		Node n = l.get(0);
		boolean[] v = new boolean[l.size()];
		return rec(v, n, ni, ci, nj, cj, nk, ck);
		
	}

	private static int rec(boolean[] v, Node n, Node ni, Node ci, Node nj, Node cj, Node nk, Node ck) {
		int s = 1;
		v[n.id] = true;
		for (Node c: n.links) {
			if (v[c.id]) continue;
			if (n == ni && c == ci) continue;
			if (n == ci && c == ni) continue;
			if (n == nj && c == cj) continue;
			if (n == cj && c == nj) continue;
			if (n == nk && c == ck) continue;
			if (n == ck && c == nk) continue;
			s += rec(v, c, ni, ci, nj, cj, nk, ck);
		}
		return s;
	}

	private static class Node {
		public String name;
		public int id;
		public List<Node> links = new ArrayList<>();
		public Node() {}
		public String toString() {
			return name + links.stream().map(n->n.name).collect(Collectors.toList());
		}
	}

}
