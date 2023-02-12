package archives;

import java.util.LinkedList;

import tools.scanner.Scan;
import tools.tables.T;

public class _Tri2sur3 {

	public static void main(String[] args) {
		int n = Scan.readInt();
		int[] t = new int[n];
		int[] u = new int[n];
		int[] v = new int[n];
		for (int i = 0; i < n; i++) {
			t[i] = Scan.readInt();
			u[i] = Scan.readInt();
			v[i] = Scan.readInt();
		}
		
		int[] r = T.sortAndCreateIndexes(t);
		int[] t1 = T.posIndex(T.sortAndCreateIndexes(u));
		int[] t2 = T.posIndex(T.sortAndCreateIndexes(v));
		
		var list = new LinkedList<Integer>();
		var l = list.listIterator();
		for (int i = 0; i < n; i++) {
			int p = i;
			while (p != 0) {
				int x = l.previous(); 
				if (t1[x] <= t1[r[i]] || t2[x] <= t2[r[i]]) {
					l.next();
					break;
				}
				p--;
			}
			l.add(r[i]);
			for (int j = 0; j < i-p; j++) l.next();
		}
		//Collections.reverse(list);
		System.out.println(T.toString(list.stream().mapToInt(x -> x + 1).toArray(), " "));
	}
	
	record C(int id, int a, int b, int c) {}
}