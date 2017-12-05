package ca.mcgill.ecse420.a3.q4;

import java.util.Arrays;
import java.util.Random;

public class MatrixHelper {

	public static void printMatrix(double[][] matrix) {
		for (int j = 0; j < matrix.length; j++) {
			for (int i = 0; i < matrix[j].length; i++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void printVector(double[] vector) {
		System.out.print(vector[0]);
		for (int i = 1; i < vector.length; i++) {
			System.out.print(" , " + vector[i]);
		}
		System.out.println();
	}

	public static double[][] randomMatrixGenerator(int n, int m) {
		double[][] randomMatrix = new double[n][m];
		for (int i = 0; i < n; i++) {
			randomMatrix[i] = randomVectorGenerator(m);
		}
		return randomMatrix;
	}

	public static double[] randomVectorGenerator(int vectorSize) {
		Random rand = new Random();
		double[] randomVector = new double[vectorSize];
		for (int i = 0; i < vectorSize; i++) {
			randomVector[i] = rand.nextDouble() + 1;
		}
		return randomVector;
	}

	public static boolean validateVectorResult(double[] a, double[] b) {
		return Arrays.equals(a, b);
	}

}
