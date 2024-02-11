package tooltests.knapsack;

import java.util.Arrays;

import tools.knapsack.SacADos;
import tools.scanner.Scan;

// isograd-testingservices.com/FR/solutions-challenges-de-code?cts_id=93&reg_typ_id=2&que_str_id=&cli_id=45alrk6jpdnaguf3oa3gto2875&rtn_pag=https%3A%2F%2Fwww.isograd-testingservices.com%2F%2FFR%2Fsolutions-challenges-de-code%3Fcts_id%3D109#
// Exo 2
public class ISO_SacADos_Mercenaries {
	public static void main(String[] args) {
		int n = Scan.readInt();
		int[] t = Scan.readInts();
		int sum = Arrays.stream(t).sum();
		SacADos sac = new SacADos(t);
		sac.solve((sum + n - 1) / 2);
		System.out.println(sum - sac.max);
	}
}
