package aoc.done;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import tools.collections.int32.L;
import tools.scanner.Scan;
import tools.tuple.Pos3;

public class Day_22_1 {
	
	private static String[] input = Scan.readRawLines();
	private static int[][] height = new int[10][10];
	private static int[][] highId = new int[10][10];
	private static B[] bid = new B[1500];
	
	public static void main(String[] args) {

		List<B> l = new ArrayList<>();
		for (var in: input) {
			var t = in.split("~");
			L l1 = new L(t[0]);
			Pos3 p1 = new Pos3(l1.get(0), l1.get(1), l1.get(2));
			L l2 = new L(t[1]);
			Pos3 p2 = new Pos3(l2.get(0), l2.get(1), l2.get(2));
			l.add(new B(p1, p2));
		}
		Collections.sort(l, (b1, b2) -> b1.p1.z - b2.p1.z);
		
		for (var b: l) {
			int max = 0;
			if (b.p1.x < b.p2.x) {
				for (int i = b.p1.x; i <= b.p2.x; i++) {
					if (height[i][b.p1.y] > max) max = height[i][b.p1.y]; 
				}
				for (int i = b.p1.x; i <= b.p2.x; i++) {
					int x = i;
					int y = b.p1.y;
					int v = highId[x][y];
					int h = height[x][y];
					if (v != 0 && h == max) {
						bid[v].supports.add(b);
						b.supported.add(bid[v]);
					}
					highId[x][y] = b.id;
					height[x][y] = max + 1;
				}
			}
			else if (b.p1.y < b.p2.y) {
				for (int i = b.p1.y; i <= b.p2.y; i++) {
					if (height[b.p1.x][i] > max) max = height[b.p1.x][i]; 
				}
				for (int i = b.p1.y; i <= b.p2.y; i++) {
					int x = b.p1.x;
					int y = i;
					int v = highId[x][y];
					int h = height[x][y];
					if (v != 0 && h == max) {
						bid[v].supports.add(b);
						b.supported.add(bid[v]);
					}
					highId[x][y] = b.id;
					height[x][y] = max + 1;
				}
			}
			else {
				max = height[b.p1.x][b.p1.y];
				int x = b.p1.x;
				int y = b.p1.y;
				int v = highId[x][y];
				int h = height[x][y];
				if (v != 0 && h == max) {
					bid[v].supports.add(b);
					b.supported.add(bid[v]);
				}
				highId[x][y] = b.id;
				height[x][y] = max + b.p2.z - b.p1.z + 1;
			}
		}
		long s = 0;
		for (var b: l) {
			boolean ok = true;
			for (var c: b.supports) ok &= c.supported.size() >= 2;
			if (ok) s++;
		}
		System.out.println(s);
	}
		
	private static class B {
		
		static int count = 0;
		Pos3 p1;
		Pos3 p2;
		int id = ++count;
		Set<B> supports = new LinkedHashSet<>();
		Set<B> supported = new LinkedHashSet<>();
		
		B(Pos3 p1, Pos3 p2) {
			this.p1 = p1;
			this.p2 = p2;
			bid[id] = this;
		}
	}
}
