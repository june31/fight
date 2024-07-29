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
        double[][] augmentedMatrix = new double[n][2 * n];

        // Create the augmented matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmentedMatrix[i][j] = matrix[i][j];
            }
            augmentedMatrix[i][n + i] = 1;
        }

        // Apply Gauss-Jordan Elimination
        for (int i = 0; i < n; i++) {
            // Find the pivot row
            int pivotRow = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(augmentedMatrix[j][i]) > Math.abs(augmentedMatrix[pivotRow][i])) {
                    pivotRow = j;
                }
            }

            // Check if the matrix is singular
            if (augmentedMatrix[pivotRow][i] == 0) {
                return null; // The matrix is singular
            }

            // Swap rows if needed
            if (pivotRow != i) {
                double[] temp = augmentedMatrix[i];
                augmentedMatrix[i] = augmentedMatrix[pivotRow];
                augmentedMatrix[pivotRow] = temp;
            }

            // Normalize the pivot row
            double pivot = augmentedMatrix[i][i];
            for (int j = 0; j < 2 * n; j++) {
                augmentedMatrix[i][j] /= pivot;
            }

            // Eliminate the other rows
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    double factor = augmentedMatrix[j][i];
                    for (int k = 0; k < 2 * n; k++) {
                        augmentedMatrix[j][k] -= factor * augmentedMatrix[i][k];
                    }
                }
            }
        }

        // Extract the inverse matrix
        double[][] inverse = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverse[i][j] = augmentedMatrix[i][n + j];
            }
        }

        return inverse;
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
