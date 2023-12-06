package org.example.day05;

import org.example.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlmanacMap {
    public final Map<LongRange, LongRange> rangeMap;

    public AlmanacMap(String[] input) {
        Map<LongRange, LongRange> rangeMap = new HashMap<>();
        // Skips first line
        for (int i = 1; i < input.length; i++) {
            List<Long> values = StringUtils.getPositiveLongsInString(input[i]);
            long destRangeStart = values.get(0);
            long sourceRangeStart = values.get(1);
            long rangeLength = values.get(2);

            LongRange destRange = new LongRange(destRangeStart, destRangeStart + rangeLength);
            LongRange sourceRange = new LongRange(sourceRangeStart, sourceRangeStart + rangeLength);
            rangeMap.put(sourceRange, destRange);
        }
        this.rangeMap = rangeMap;
    }

    public long getDestinationValue(long sourceValue) {
        for (LongRange sourceRange : rangeMap.keySet()) {
            if (sourceRange.contains(sourceValue)) {
                LongRange destRange = rangeMap.get(sourceRange);
                long offset = sourceValue - sourceRange.low();
                return destRange.low() + offset;
            }
        }
        return sourceValue;
    }
}
