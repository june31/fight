package tools.tables;

public class _T {
	public static void main(String[] args) {
		int[] t = new int[] { 5, 8, 15, 2, 8, 3 };
		System.out.println(Table.toString(t, ", "));
		int[] u = TableInProgress.retrieveIndexes(t);
		int[] v = TableInProgress.posIndex(u);
		
		System.out.println(Table.toString(t, ", "));
		System.out.println(Table.toString(u, ", "));
		System.out.println(Table.toString(v, ", "));
	}
}
