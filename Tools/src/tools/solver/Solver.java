package tools.solver;

import java.util.function.IntToLongFunction;

public class Solver {
	public static double[] linearSolve(double[][] coefs, double[] vals) {
		int n = vals.length;
		double[][] inverseA = invert(coefs);
		double[] x = new double[n];
		for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) x[i] += inverseA[i][j] * vals[j];
		return x;
	}

	public static double[][] invert(double[][] matrix) {
		int n = matrix.length;
		double[][] inverse = new double[n][n];
		double[][] augmented = new double[n][2 * n];

		// Créer une matrice augmentée en combinant la matrice et la matrice identité
		for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) {
			augmented[i][j] = matrix[i][j];
			if (i == j) augmented[i][j + n] = 1;
		}

		// Application de la méthode de Gauss-Jordan sur la matrice augmentée
		for (int i = 0; i < n; i++) {
			// Normaliser la ligne i
			double pivot = augmented[i][i];
			for (int j = 0; j < 2 * n; j++) augmented[i][j] /= pivot;

			// Rendre les autres lignes zéro dans la colonne i
			for (int k = 0; k < n; k++) if (k != i) {
				double factor = augmented[k][i];
				for (int j = 0; j < 2 * n; j++) augmented[k][j] -= factor * augmented[i][j];
			}
		}

		for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) inverse[i][j] = augmented[i][j + n];
		return Double.isNaN(inverse[0][0]) ? null : inverse;
	}

	public static double[] findPolynomial(IntToLongFunction f) { return findPolynomial(f, 1); }
	public static double[] findPolynomial(IntToLongFunction f, int step) {
		degree: for (int n = 1; n <= 6; n++) {
			double[][] coefs = new double[n][n];
			double[] vals = new double[n];
			int x = step;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) coefs[i][j] = Math.pow(x, n-1-j);
				vals[i] = f.applyAsLong(x);
				x += step;
			}
			double[] polynom = linearSolve(coefs, vals);
			// Vérification
			for (int i = 0; i < 3; i++) {
				if (getPolynomialValue(polynom, x) != f.applyAsLong(x)) continue degree;
				x += step;
			}
			return polynom;
		}
		return null;
	}
	
	public static long getPolynomialValue(double[] polynom, int x) {
		long r = 0;
		for (int i = 0; i < polynom.length; i++) r += polynom[i] * Math.pow(x, polynom.length - 1 - i);
		return r;
	}
}
