package tools;

import java.util.ArrayList;
import java.util.List;

public class Leaderboard<T> {
	
	public int size;
	public int[] scores;
	public Object[] objects;
	
	public Leaderboard(int size) {
		this.size = size;
		scores = new int[size];
		objects = new Object[size];
		for (int i = 0; i < size; i++) {
			scores[i] = Integer.MIN_VALUE;
		}
	}
	
	// Not thread safe for speed !
	// A leaderboard only belongs to one thread.
	// Merge leaderboards using ParallelRunner.mergeBoards if needed!
	public void add(int score, T o) {
		int index = size - 1;
		if (score <= scores[index]) return; // fast fail
		while (index >= 0 && scores[index] < score) index--;
		if (index < size - 1) {
			for (int i = size - 2; i > index; i--) {
				scores[i + 1] = scores[i];
				objects[i + 1] = objects[i];
			}
			scores[index + 1] = score;
			objects[index + 1] = o;
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> copyToList() {
		List<T> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			list.add((T) objects[i]);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public T get(int i) {
		return (T) objects[i];
	}
}
