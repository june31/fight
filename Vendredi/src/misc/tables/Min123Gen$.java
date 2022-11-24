package misc.tables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import tools.output.Out;

public class Min123Gen$ {
	static final int N = 20;
	static final int M = 10;
	public static void main(String[] args) {
		Out.SYSTEM_OUT = false;
		Out.reset();
		
		Random rnd = new Random(0);
		Out.bufln(N);
		List<Integer> list = new ArrayList<>(N);
		for (int i = 0; i < N; i++) {
			list.add(i);
		}
		Collections.shuffle(list, rnd);
		for (int i = 0; i < N; i++) {
			if (i > 0) Out.buf(' ');
			Out.buf(list.get(i));
		}
		Out.bufln();
		
		Out.bufln(M);
		for (int i = 0; i < M - 5; i++) {
			int a = rnd.nextInt(N - 3);
			int b = a + 3 + rnd.nextInt(N - 3 - a);
			Out.bufln(a + " " + b);
		}
		Out.bufln(0 + " " + 2);
		Out.bufln(1 + " " + 3);
		Out.bufln(0 + " " + (N-1));
		Out.bufln(1 + " " + (N-2));
		Out.bufln((N-3) + " " + (N-1));
		
		Out.flush();
	}
}
