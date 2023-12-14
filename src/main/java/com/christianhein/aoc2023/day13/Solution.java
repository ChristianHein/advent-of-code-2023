package com.christianhein.aoc2023.day13;

import com.christianhein.aoc2023.util.Grid2D;
import com.christianhein.aoc2023.util.Pair;
import com.christianhein.aoc2023.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Solution {
    private final ArrayList<Grid2D<Character>> patterns;

    public Solution(String[] input) {
        List<String[]> splitPatterns = StringUtils.splitStringArrayOnBlankStrings(input);
        this.patterns = splitPatterns.stream()
                .map(a -> Arrays.stream(a).map(String::toCharArray))
                .map(a -> a.toArray(char[][]::new))
                .map(Grid2D::from)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public int part1() {
        return this.patterns.stream()
                .map(Solution::patternScore)
                .reduce(Integer::sum)
                .orElse(0);
    }

    public int part2() {
        return this.patterns.stream()
                .map(Solution::patternScoreWithSmudges)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private static int patternScore(Grid2D<Character> pattern) {
        var rowsAbove = getHorizontalReflectionLines(pattern);
        if (!rowsAbove.isEmpty()) {
            return patternScoreFormula(rowsAbove.getFirst(), 0);
        }

        var columnsLeft = getVerticalReflectionLines(pattern);
        if (!columnsLeft.isEmpty()) {
            return patternScoreFormula(0, columnsLeft.getFirst());
        }

        return patternScoreFormula(0, 0);
    }

    private static int patternScoreFormula(int rowsAbove, int colsLeft) {
        return 100 * rowsAbove + colsLeft;
    }

    private static int patternScoreWithSmudges(Grid2D<Character> pattern) {
        Pair<List<Integer>, List<Integer>> initialReflections
                = new Pair<>(getHorizontalReflectionLines(pattern), getVerticalReflectionLines(pattern));

        for (int rowIndex = 0; rowIndex < pattern.getHeight(); rowIndex++) {
            for (int colIndex = 0; colIndex < pattern.getWidth(); colIndex++) {
                flip(rowIndex, colIndex, pattern);
                Pair<List<Integer>, List<Integer>> newReflections
                        = new Pair<>(getHorizontalReflectionLines(pattern), getVerticalReflectionLines(pattern));

                if (!newReflections.equals(new Pair<>(List.of(), List.of()))) {
                    if (!initialReflections.left().equals(newReflections.left())) {
                        int newHorizontalReflection = newReflections.left().stream()
                                .filter(refLine -> !initialReflections.left().contains(refLine))
                                .findAny()
                                .orElseThrow();
                        flip(rowIndex, colIndex, pattern);
                        return patternScoreFormula(newHorizontalReflection, 0);
                    }
                    if (!initialReflections.right().equals(newReflections.right())) {
                        int newVerticalReflection = newReflections.right().stream()
                                .filter(refLine -> !initialReflections.right().contains(refLine))
                                .findAny()
                                .orElseThrow();
                        flip(rowIndex, colIndex, pattern);
                        return patternScoreFormula(0, newVerticalReflection);
                    }
                }
                flip(rowIndex, colIndex, pattern);
            }
        }
        return patternScore(pattern);
    }

    private static void flip(int row, int col, Grid2D<Character> pattern) {
        if (pattern.get(row, col) == '.') {
            pattern.set(row, col, '#');
        } else {
            pattern.set(row, col, '.');
        }
    }

    private static List<Integer> getHorizontalReflectionLines(Grid2D<Character> pattern) {
        List<Integer> horizontalReflections = new ArrayList<>();
        for (int rowsAbove = 1; rowsAbove < pattern.getHeight(); rowsAbove++) {
            if (isHorizontalReflectionLine(rowsAbove, pattern)) {
                horizontalReflections.add(rowsAbove);
            }
        }
        return horizontalReflections;
    }

    private static boolean isHorizontalReflectionLine(int rowsAbove, Grid2D<Character> pattern) {
        int maxSafeOffset = Math.min(rowsAbove - 1, pattern.getHeight() - 1 - rowsAbove);
        for (int offset = 0; offset <= maxSafeOffset; offset++) {
            int topIndex = rowsAbove - 1 - offset;
            int bottomIndex = rowsAbove + offset;
            if (!pattern.getRow(topIndex).equals(pattern.getRow(bottomIndex))) {
                return false;
            }
        }
        return true;
    }

    private static List<Integer> getVerticalReflectionLines(Grid2D<Character> pattern) {
        List<Integer> verticalReflections = new ArrayList<>();
        for (int colsLeft = 1; colsLeft < pattern.getWidth(); colsLeft++) {
            if (isVerticalReflectionLine(colsLeft, pattern)) {
                verticalReflections.add(colsLeft);
            }
        }
        return verticalReflections;
    }

    private static boolean isVerticalReflectionLine(int colsLeft, Grid2D<Character> pattern) {
        int maxSafeOffset = Math.min(colsLeft - 1, pattern.getWidth() - 1 - colsLeft);
        for (int offset = 0; offset <= maxSafeOffset; offset++) {
            int leftIndex = colsLeft - 1 - offset;
            int rightIndex = colsLeft + offset;
            if (!pattern.getCol(leftIndex).equals(pattern.getCol(rightIndex))) {
                return false;
            }
        }
        return true;
    }
}
