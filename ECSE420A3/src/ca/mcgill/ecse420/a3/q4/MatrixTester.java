package ca.mcgill.ecse420.a3.q4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MatrixTester {
	public static int MATRIX_SIZE = 2;
	public static ExecutorService executor;

	public static void main(String[] args) {
		int[][] A = MatrixHelper.randomMatrixGenerator(MATRIX_SIZE, MATRIX_SIZE);
		int[] B = MatrixHelper.randomVectorGenerator(MATRIX_SIZE);
		int[] C = new int[MATRIX_SIZE];
		int[] D = new int[MATRIX_SIZE];
		sequentialMultiplyMatrixVector(A, B, C);
		parallelMultiplyMatrix(A, B, D);
		System.out.print("Sequential result : ");
		MatrixHelper.printVector(C);
		System.out.print("Parallel result : ");
		MatrixHelper.printVector(D);

	}

	public static void sequentialMultiplyMatrixVector(int[][] matrix, int[] vector, int[] result) {
		for (int i = 0; i < result.length; i++) {// compute each entry in result
			int entry = 0;
			for (int j = 0; j < vector.length; j++) {
				entry += vector[j] * matrix[j][i];// compute each entry
			}
			result[i] = entry; // put it in result
		}
	}

	public static void parallelMultiplyMatrix(int[][] a, int[] b, int[] result) {
		List<Future<Integer>> resultEntries = new ArrayList<Future<Integer>>();
		executor = Executors.newFixedThreadPool(10);
		for (int i = 0; i < result.length; i++) {
			List<Future<Integer>> listOfNumbersToAddTogether = new ArrayList<Future<Integer>>(); // list of
																									// multiplications
																									// result
			for (int j = 0; j < b.length; j++) {
				Future<Integer> future = executor.submit(new CallableMultiplication(b[j], a[j][i]));
				listOfNumbersToAddTogether.add(future);
			}
			resultEntries.add(recursiveAddFutur(listOfNumbersToAddTogether)); // build the tree of additions.
		}
		// obtain result, TODO add parallelization for multiple entries
		for (int i = 0; i < result.length; i++) {
			try {
				result[i] = resultEntries.get(i).get(); // put it in result. Blocking
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// build an addition tree.
	public static Future<Integer> recursiveAddFutur(List<Future<Integer>> list) {
		if (list.size() == 1) { // top of recursion, can return the result to be used in future additions
			return list.get(0);
		} else if (list.size() == 2) {
			List<Future<Integer>> firstPart = list.subList(0, 1);
			List<Future<Integer>> lastpart = list.subList(1, 2);
			Future<Integer> future = executor
					.submit(new CallableFuturAdder(recursiveAddFutur(firstPart), recursiveAddFutur(lastpart)));
			return future;
		} else {
			// split the list in 2
			int middle = list.size() / 2;
			List<Future<Integer>> firstPart = list.subList(0, middle);
			List<Future<Integer>> lastpart = list.subList(middle, list.size() - 1);
			Future<Integer> future = executor
					.submit(new CallableFuturAdder(recursiveAddFutur(firstPart), recursiveAddFutur(lastpart)));
			return future;
		}
	}

}
