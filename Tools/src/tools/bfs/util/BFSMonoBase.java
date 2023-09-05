package tools.bfs.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

public abstract class BFSMonoBase {
	
	protected final int USED_BIT = 1<<31; // to differentiate unused backtrack and backtrack to (0, 0)

	public boolean found;
	public int scanned; // includes start
	public int id;
	public int turn;
	public BooleanSupplier endCondition;
	
	public final int size;
	public final List<List<IntSupplier>> moves = new ArrayList<>();
	
	protected boolean clean = true;

	// bits 0 to 30 store index.
	// bit 31 is reserved for use purposes.
	protected final int[] backtrack;
	
	public BFSMonoBase(int size) {
		this.size = size;
		backtrack = new int[size];
		for (int i = 0; i < size; i++) moves.add(new ArrayList<>());
		
	}
	
	public void addLink(int i, int j) { moves.get(i).add(() -> j); }
	public void addDualLink(int i, int j) { moves.get(i).add(() -> j); moves.get(j).add(() -> i); }
	public void addLink(int i, IntSupplier is) { moves.get(i).add(is); }
	
	public int diffuse(int s) { return diffuse(s, () -> false); }
	public int diffuse(int s, int e) { return diffuse(s, () -> id == e); }
	public abstract int diffuse(int s, BooleanSupplier end);
	
	// This includes the start and the end. The order is start -> end.
	public List<Integer> shortestPath() { return shortestPath(id); }
	public List<Integer> shortestPath(int i) {
		if (backtrack[i] == 0) return null;
		List<Integer> track = new ArrayList<>();
		int bt = i;
		do {
			track.add(bt);
			bt = backtrack[bt] & Integer.MAX_VALUE;
		} while (bt != Integer.MAX_VALUE);
		Collections.reverse(track);
		return track;
	}
}
