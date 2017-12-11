package ca.mcgill.ecse420.a3.q4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MatrixTester {
    public static int MATRIX_SIZE;
    public static ExecutorService executor;

    public static void main(String[] args) {
        if (args.length == 1) { // take input parameters
            MATRIX_SIZE = Integer.parseInt(args[0]);
        } else { // put default parameters, 2000X2000 matrix
            MATRIX_SIZE = 2000;
        }
        System.out.println("For a " +MATRIX_SIZE+ "X" +MATRIX_SIZE+ " matrix" );
        int[][] A = MatrixHelper.randomMatrixGenerator(MATRIX_SIZE, MATRIX_SIZE);
        int[] B = MatrixHelper.randomVectorGenerator(MATRIX_SIZE);
        int[] sequentialResult = new int[MATRIX_SIZE];
        int[] parallelResult = new int[MATRIX_SIZE];
        // Execute and compute average sequential time
        long averageSequentialduration = computeAverageTimeSequential(A, B, sequentialResult);
        // Execute and compute average parallel time
        long averageParallelDuration = computeAverageTimeParallel(A, B, parallelResult);

        System.out.print("Sequential result obtained in " + averageSequentialduration + "ms. ->");
        MatrixHelper.printVector(sequentialResult);
        System.out.print("Parallel result obtained in " + averageParallelDuration + "ms. ->");
        MatrixHelper.printVector(parallelResult);

    }

    public static long computeAverageTimeParallel(int[][] matrix, int[] vector, int[] result) {
        long totalTime = 0;
        for (int i = 0; i < 3; i++) {
            long startTime = System.nanoTime();
            parallelMultiplyMatrix(matrix, vector, result);
            long endTime = System.nanoTime();
            long Sequentialduration = (endTime - startTime) / 1000000;
            totalTime += Sequentialduration;
        }
        return totalTime / 3;
    }

    public static long computeAverageTimeSequential(int[][] matrix, int[] vector, int[] result) {
        long totalTime = 0;
        for (int i = 0; i < 3; i++) {
            long startTime = System.nanoTime();
            sequentialMultiplyMatrixVector(matrix, vector, result);
            long endTime = System.nanoTime();
            long Sequentialduration = (endTime - startTime) / 1000000;
            totalTime += Sequentialduration;
        }
        return totalTime / 3;
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
        executor = Executors.newFixedThreadPool(4);
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

        // put results in result array
        int taskDoneCounter = 0; // keep track of the tasks that are done
        boolean[] taskDoneFlag = new boolean[result.length];
        while (taskDoneCounter < result.length) { // stop when everything is done
            for (int i = 0; i < result.length; i++) {
                Future<Integer> entryFutur = resultEntries.get(i);
                if (entryFutur.isDone() && !taskDoneFlag[i]) { // test to see if the result is ready
                    taskDoneCounter++;
                    taskDoneFlag[i] = true;
                    try {
                        result[i] = entryFutur.get(); // shouldn't be blocking at this point

                    } catch (InterruptedException | ExecutionException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(10000, TimeUnit.MICROSECONDS)) {
                System.out.println("Took too long to exit...");
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
            List<Future<Integer>> lastpart = list.subList(middle, list.size());
            Future<Integer> future = executor
                    .submit(new CallableFuturAdder(recursiveAddFutur(firstPart), recursiveAddFutur(lastpart)));
            return future;
        }
    }

}
