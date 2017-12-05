package ca.mcgill.ecse420.a3.q4;

public class MatrixTester {
	public static int MATRIX_SIZE = 2;

	public static void main(String[] args) {
		double[][] A = MatrixHelper.randomMatrixGenerator(MATRIX_SIZE, MATRIX_SIZE);
		double[] B = MatrixHelper.randomVectorGenerator(MATRIX_SIZE);
		double[] C = new double[MATRIX_SIZE];
		sequentialMultiplyMatrixVector(A, B, C);
		MatrixHelper.printVector(C);

	}

	public static void sequentialMultiplyMatrixVector(double[][] matrix, double[] vector, double[] result) {
		for (int i = 0; i < result.length; i++) {// compute each entry in result
			double entry = 0;
			for (int j = 0; j < vector.length; j++) {
				entry += vector[j] * matrix[j][i];// compute each entry
			}
			result[i] = entry; // put it in result
		}
	}

	public static void parrallelMultiplyMatrix(double[][] a, double[][] b, double[][] result) {
		// TODO
	}

}
