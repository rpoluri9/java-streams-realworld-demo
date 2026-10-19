package com.streamsdemo;

import com.streamsdemo.parallel.ParallelCaution;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ParallelCautionTest {

    @Test
    void parallelSumMatchesSequential() {
        long n = 100_000L;
        assertEquals(ParallelCaution.sumOfSquaresSequential(n),
                ParallelCaution.sumOfSquaresParallel(n));
    }

    @Test
    void forEachOrderedPreservesEncounterOrder() {
        List<Integer> input = IntStream.rangeClosed(1, 50).boxed().collect(Collectors.toList());
        assertEquals(input, ParallelCaution.parallelForEachOrderedOrder(input));
    }

    @Test
    void parallelForEachKeepsAllElementsButOrderIsUnreliable() {
        List<Integer> input = IntStream.rangeClosed(1, 50).boxed().collect(Collectors.toList());
        // Same elements present (nothing lost), but order is not guaranteed.
        assertEquals(new HashSet<>(input),
                new HashSet<>(ParallelCaution.parallelForEachOrder(input)));
    }

    @Test
    void parallelCollectKeepsOrderAndTransforms() {
        List<Integer> input = List.of(1, 2, 3, 4, 5);
        assertEquals(List.of(10, 20, 30, 40, 50),
                ParallelCaution.parallelCollectSafe(input));
    }
}
