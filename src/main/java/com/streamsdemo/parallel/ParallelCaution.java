package com.streamsdemo.parallel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Parallel streams: powerful, but only when used correctly.
 *
 * WHEN IT HELPS: large datasets, CPU-bound work, no shared mutable state,
 * order of results doesn't matter (or you use an ordered collector).
 *
 * WHEN IT HURTS: small lists (thread overhead > gains), blocking I/O,
 * mutating shared state (race conditions!), or when encounter order matters
 * and you used forEach instead of forEachOrdered.
 */
public final class ParallelCaution {

    private ParallelCaution() {}

    /** Sum of squares 1..n, sequential. */
    public static long sumOfSquaresSequential(long n) {
        return LongStream.rangeClosed(1, n).map(x -> x * x).sum();
    }

    /** Sum of squares 1..n, parallel. Same result, less wall-clock time on big n. */
    public static long sumOfSquaresParallel(long n) {
        return LongStream.rangeClosed(1, n).parallel().map(x -> x * x).sum();
    }

    /**
     * PITFALL DEMO: parallel forEach does NOT respect encounter order.
     * Appending to a (thread-safe) list from parallel forEach scrambles order.
     * forEachOrdered keeps the original order even in parallel.
     */
    public static List<Integer> parallelForEachOrder(List<Integer> input) {
        List<Integer> out = Collections.synchronizedList(new ArrayList<>());
        input.parallelStream().forEach(out::add);
        return out;
    }

    public static List<Integer> parallelForEachOrderedOrder(List<Integer> input) {
        List<Integer> out = Collections.synchronizedList(new ArrayList<>());
        input.parallelStream().forEachOrdered(out::add);
        return out;
    }

    /** The safe way to gather parallel results: collect() handles ordering for you. */
    public static List<Integer> parallelCollectSafe(List<Integer> input) {
        return input.parallelStream()
                .map(x -> x * 10)
                .collect(Collectors.toList()); // ordered collector: encounter order kept
    }
}
