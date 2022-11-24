package tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Utils {

	public static Random RND = new Random(); // Can be overridden

	public static <A, B> void putInList(Map<A, List<B>> map, A key, B value) {
		List<B> list = map.get(key);
		if (list == null) {
			list = new ArrayList<B>();
			map.put(key, list);
		}
		list.add(value);
	}
	
	public static <A> void clearAndFill(A[] array, Collection<A> reference) {
		int index = 0;
		for (Iterator<A> it = reference.iterator(); it.hasNext();) {
			array[index++] = it.next();
		}
		for (int i = reference.size(); i < array.length; i++) {
			array[i] = null;
		}
	}

	public static <A> void shuffle(A[] array) {
		for (int i = 0; i < array.length; i++) {
			int rndId = RND.nextInt(array.length);
			A temp = array[rndId];
			array[rndId] = array[i];
			array[i] = temp;
		}
	}
	
	public static int[] getRandomSequence(int size) {
		int[] tab = new int[size];
		for (int i = 0; i < size; i++) {
			tab[i] = i;
		}
		for (int i = 0; i < size; i++) {
			int rndId = RND.nextInt(size);
			int temp = tab[rndId];
			tab[rndId] = tab[i];
			tab[i] = temp;
		}
		return tab;
	}
	
	public static String getPaddedString(int n, int size) {
		String s = "" + n;
		StringBuilder builder = new StringBuilder();
		while (builder.length() < size - s.length()) {
			builder.append(' ');
		}
		builder.append(s);
		return builder.toString();
	}
	
	public static String getTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH.mm.ss"));
	}
	
	public static void debug(String[] array) {
		System.err.println(Arrays.stream(array).collect(Collectors.joining(", ", "[", "]")));
	}

	public static void debug(int[] array) {
		System.err.println(Arrays.stream(array).mapToObj(x -> "" + x).collect(Collectors.joining(", ", "[", "]")));
	}

}
