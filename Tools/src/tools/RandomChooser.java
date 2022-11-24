package tools;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RandomChooser<T> {

	private T[] objects;
	private int[] weights;
	private int[] curWeights;
	
	private int idx = 0; 
	private int totalWeight = 0;
	private int currentTotalWeight = 0;
	
	@SuppressWarnings("unchecked")
	public RandomChooser(Class<T> clazz, int capacity) {
		objects = (T[]) Array.newInstance(clazz, capacity);
		weights = new int[capacity];
		curWeights = new int[capacity];
	}
	
	public void add(T object, int weight) {
		objects[idx] = object;
		weights[idx] = weight;
		curWeights[idx] = weight;
		totalWeight += weight;
		currentTotalWeight = totalWeight;
		idx++;
	}
	
	public void reset() {
		for (int i = 0; i < idx; i++) {
			curWeights[i] = weights[i];
		}
		currentTotalWeight = totalWeight;
	}
	
	public T select() {
		int r = Utils.RND.nextInt(currentTotalWeight);
		int w = 0;
		for (int i = 0; i < idx; i++) {
			w += curWeights[i];
			if (w > r) {
				currentTotalWeight -= curWeights[i];
				curWeights[i] = 0;
				return objects[i];
			}
		}
		return null;
	}
	
	public List<T> getRemainings() {
		List<T> list = new ArrayList<>();
		for (int i = 0; i < idx; i++) {
			if (curWeights[i] > 0) {
				list.add(objects[i]);
			}
		}
		return list;
	}
}
