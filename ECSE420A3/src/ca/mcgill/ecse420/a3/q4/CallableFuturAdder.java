package ca.mcgill.ecse420.a3.q4;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CallableFuturAdder implements Callable<Integer> {

    Future<Integer> subResult1;
    Future<Integer> subResult2;

    CallableFuturAdder(Future<Integer> subResult1, Future<Integer> subResult2) {
        this.subResult1 = subResult1;
        this.subResult2 = subResult2;
    }

    public Integer call() throws InterruptedException, ExecutionException {
        return this.subResult1.get() + this.subResult2.get();
    }

}
