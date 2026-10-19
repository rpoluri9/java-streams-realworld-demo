package com.streamsdemo.parallel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Runnable demo: timing sequential vs parallel + the ordering pitfall. */
public class ParallelDemo {
    public static void main(String[] args) {
        long n = 5_000_000L;

        System.out.println("=== Parallel streams: timing (n=" + n + ") ===");
        long t0 = System.currentTimeMillis();
        long seq = ParallelCaution.sumOfSquaresSequential(n);
        long tSeq = System.currentTimeMillis() - t0;

        t0 = System.currentTimeMillis();
        long par = ParallelCaution.sumOfSquaresParallel(n);
        long tPar = System.currentTimeMillis() - t0;

        System.out.println("Sequential: " + tSeq + " ms, result=" + seq);
        System.out.println("Parallel:   " + tPar + " ms, result=" + par);
        System.out.println("Same result? " + (seq == par));

        System.out.println("\n=== Ordering pitfall ===");
        List<Integer> input = IntStream.rangeClosed(1, 20).boxed().collect(Collectors.toList());
        System.out.println("Input order:            " + input);
        System.out.println("parallel forEach:       " + ParallelCaution.parallelForEachOrder(input));
        System.out.println("parallel forEachOrdered:" + ParallelCaution.parallelForEachOrderedOrder(input));
        System.out.println("\nLesson: forEach on a parallel stream ignores order;");
        System.out.println("use forEachOrdered (or collect) when order matters.");
        System.out.println("And NEVER mutate shared non-thread-safe state from a parallel stream.");
    }
}
