package ca.mcgill.ecse420.a3.q4;

import java.util.concurrent.Callable;

public class CallableMultiplication implements Callable<Integer> {

    Integer subResult1;
    Integer subResult2;

    CallableMultiplication(Integer subResult1, Integer subResult2) {
        this.subResult1 = subResult1;
        this.subResult2 = subResult2;
    }

    public Integer call() {
        return this.subResult1 * this.subResult2;
    }

}
