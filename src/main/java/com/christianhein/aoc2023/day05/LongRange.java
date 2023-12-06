package com.christianhein.aoc2023.day05;

import java.util.Iterator;

// Inclusive low, exclusive high.
public record LongRange(long low, long high) implements Iterable<Long> {
    public boolean contains(long value) {
        return low <= value && value < high;
    }

    @Override
    public Iterator<Long> iterator() {
        return new Iterator<Long>() {
            private long value = low;

            @Override
            public boolean hasNext() {
                return value < high;
            }

            @Override
            public Long next() {
                return value++;
            }
        };
    }
}
