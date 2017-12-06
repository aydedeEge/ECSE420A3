package ca.mcgill.ecse420.a3.q4;

import java.util.Arrays;
import java.util.Random;

public class MatrixHelper {

	public static void printMatrix(int[][] matrix) {
		for (int j = 0; j < matrix.length; j++) {
			for (int i = 0; i < matrix[j].length; i++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void printVector(int[] vector) {
		System.out.print(vector[0]);
		for (int i = 1; i < vector.length; i++) {
			System.out.print(" , " + vector[i]);
		}
		System.out.println();
	}

	public static int[][] randomMatrixGenerator(int n, int m) {
		int[][] randomMatrix = new int[n][m];
		for (int i = 0; i < n; i++) {
			randomMatrix[i] = randomVectorGenerator(m);
		}
		return randomMatrix;
	}

	public static int[] randomVectorGenerator(int vectorSize) {
		Random rand = new Random();
		int[] randomVector = new int[vectorSize];
		for (int i = 0; i < vectorSize; i++) {
			randomVector[i] = rand.nextInt() + 1;
		}
		return randomVector;
	}

	public static boolean validateVectorResult(int[] a, int[] b) {
		return Arrays.equals(a, b);
	}

}
