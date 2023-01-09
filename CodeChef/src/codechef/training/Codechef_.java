package codechef.training;

import tools.scanner.Scan;

class Codechef_ {
	
	public static void main(String[] args) {
		int T = Scan.readInt();
		for (int turn = 0; turn < T; turn++) {
			int N = Scan.readInt();
			int K = Scan.readInt();
			byte[] data = Scan.readString().getBytes();
			
			int[] left = new int[N + 1];
			for (int i = 1; i < N+1; i++) left[i] = data[i-1] == '0' ? 0 : left[i-1] + 1;

			int[] right = new int[N + 1];
			for (int i = N - 1; i >= 0; i--) right[i] = data[i] == '0' ? 0 : right[i+1] + 1;
			
			int max = 0;
			for (int i = 0; i <= N-K; i++) {
				int s = left[i] + right[i + K];
				if (s > max) max = s;
			}

			System.out.println(K + max);
		}
	}
}
