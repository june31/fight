package tooltests.knapsack;

import tools.knapsack.SacADos;

// https://fr.wikipedia.org/wiki/Probl%C3%A8me_du_sac_%C3%A0_dos
public class Wiki_SacADos {
	public static void main(String[] args) {
		int[] poids = { 2, 5, 7, 12, 9 };
		int[] valeurs = { 1, 2, 3, 7, 10 };
		SacADos sac = new SacADos(poids, valeurs);
		System.out.println(sac.solve(15));
		System.out.println(sac.max);
	}
}
