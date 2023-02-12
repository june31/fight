package tools.tables;

public class _T {
	public static void main(String[] args) {
		int[] t = new int[] { 5, 8, 15, 2, 8, 3 };
		System.out.println(T.toString(t, ", "));
		int[] u = T.sortAndCreateIndexes(t);
		int[] v = T.posIndex(u);
		
		System.out.println(T.toString(t, ", "));
		System.out.println(T.toString(u, ", "));
		System.out.println(T.toString(v, ", "));
	}
}
