package tooltests.solver;

import tools.solver.Solver;
import tools.tables.Table;

public class SolverTest {

	public static void main(String[] args) {
		double[][] in1 = {
				{ 1, 2, 3, 4, 5 },
				{ 11, 22, 353, 44, 55 },
				{ 20, 30, 44, 50, 61 },
				{ 5, 6, 7, 8, 9 },
				{ 3, 8, 8, 8, 6 }
		};
		double[][] in2 = {
				{ 1, 2, 3, 4, 5 },
				{ 17, 22, 353, 44, 55 },
				{ 12, 30, 44, 50, 61 },
				{ 4, 6, 7, 8, 9 },
				{ 3, 8, 8, 8, 6 }
		};
		double[][] in3 = {
				{ 4, 2, 1 },
				{ 16, 4, 1 },
				{ 36, 6, 1 }
		};
		
		// Singulière
		Table.printArray(Solver.invert(in1));

		System.out.println();
		// Inversible
		Table.printArray(Solver.invert(in2));
		
		System.out.println();
		// AoC 2023-21-2
		Table.printArray(Solver.invert(in3));
		
		System.out.println();
		// AoC 2023-21-2
		System.out.println(Solver.getPolynomialValue(new double[] { 3, 2, 1 }, 5));
	}
}
