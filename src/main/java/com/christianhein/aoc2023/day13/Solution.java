package com.christianhein.aoc2023.day13;

import com.christianhein.aoc2023.util.Grid2D;
import com.christianhein.aoc2023.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Solution {
    public static int part1(String[] input) {
        List<String[]> splitPatterns = StringUtils.splitStringArrayOnBlankStrings(input);
        Stream<Grid2D<Character>> patterns = splitPatterns.stream()
                .map(a -> Arrays.stream(a).map(String::toCharArray))
                .map(a -> a.toArray(char[][]::new))
                .map(Grid2D::from);
        return patterns
                .map(Solution::patternScore)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private static int patternScore(Grid2D<Character> pattern) {
        var rowsAbove = rowsAboveHorizontalReflection(pattern);
        if (rowsAbove.isPresent()) {
            return 100 * rowsAbove.get();
        }

        var columnsLeft = columnsLeftOfVerticalReflection(pattern);
        //noinspection OptionalIsPresent
        if (columnsLeft.isPresent()) {
            return columnsLeft.get();
        }

        return 0;
    }

    private static Optional<Integer> rowsAboveHorizontalReflection(Grid2D<Character> pattern) {
        for (int rowsAbove = 1; rowsAbove < pattern.getHeight(); rowsAbove++) {
            if (isHorizontalReflection(rowsAbove, pattern)) {
                return Optional.of(rowsAbove);
            }
        }
        return Optional.empty();
    }

    private static boolean isHorizontalReflection(int rowsAbove, Grid2D<Character> pattern) {
        int maxSafeOffset = Math.min(rowsAbove - 1, pattern.getHeight() - 1 - rowsAbove);
        for (int offset = 0; offset <= maxSafeOffset; offset++) {
            if (!pattern.getRow(rowsAbove + offset).equals(pattern.getRow(rowsAbove - 1 - offset))) {
                return false;
            }
        }
        return true;
    }

    private static Optional<Integer> columnsLeftOfVerticalReflection(Grid2D<Character> pattern) {
        for (int colsLeft = 1; colsLeft < pattern.getWidth(); colsLeft++) {
            if (isVerticalReflection(colsLeft, pattern)) {
                return Optional.of(colsLeft);
            }
        }
        return Optional.empty();
    }

    private static boolean isVerticalReflection(int colsLeft, Grid2D<Character> pattern) {
        int maxSafeOffset = Math.min(colsLeft - 1, pattern.getWidth() - 1 - colsLeft);
        for (int offset = 0; offset <= maxSafeOffset; offset++) {
            if (!pattern.getCol(colsLeft + offset).equals(pattern.getCol(colsLeft - 1 - offset))) {
                return false;
            }
        }
        return true;
    }
}
