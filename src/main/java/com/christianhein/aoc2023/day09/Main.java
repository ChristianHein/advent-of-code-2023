package com.christianhein.aoc2023.day09;

import com.christianhein.aoc2023.util.IOUtils;
import com.christianhein.aoc2023.util.StringUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String inputFilepath = "input-day09.txt";
        String[] input = IOUtils.linesFromResource(inputFilepath);

        System.out.println("Solution to Day 9 puzzle (part 1): " + part1Solution(input));
        System.out.println("Solution to Day 9 puzzle (part 2): " + part2Solution(input));
    }

    private static String part1Solution(String[] input) {
        int extrapolatedNextValuesSum = Arrays.stream(input)
                .map(StringUtils::getIntsInString)
                .map(Main::extrapolateNextValue)
                .reduce(Integer::sum)
                .orElse(0);
        return String.valueOf(extrapolatedNextValuesSum);
    }

    private static String part2Solution(String[] input) {
        int extrapolatedPreviousValuesSum = Arrays.stream(input)
                .map(StringUtils::getIntsInString)
                .map(Main::extrapolatePreviousValue)
                .reduce(Integer::sum)
                .orElse(0);
        return String.valueOf(extrapolatedPreviousValuesSum);
    }

    private static int extrapolateNextValue(List<Integer> history) {
        return buildDifferenceSequences(history).stream()
                .map(List::getLast)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private static int extrapolatePreviousValue(List<Integer> history) {
        List<List<Integer>> sequences = buildDifferenceSequences(history);
        int extrapolatedValue = 0;
        for (int i = sequences.size() - 2; i >= 0; i--) {
            extrapolatedValue = sequences.get(i).getFirst() - extrapolatedValue;
        }
        return extrapolatedValue;
    }

    private static List<List<Integer>> buildDifferenceSequences(List<Integer> initialSequence) {
        List<List<Integer>> sequences = new ArrayList<>();
        sequences.add(initialSequence);
        while (isNotAllZeros(sequences.getLast())) {
            sequences.add(nextSequence(sequences.getLast()));
        }
        return sequences;
    }

    private static boolean isNotAllZeros(List<Integer> list) {
        return !list.stream().allMatch(n -> n == 0);
    }

    private static List<Integer> nextSequence(List<Integer> sequence) {
        List<Integer> differences = new ArrayList<>(sequence.size() - 1);
        for (int i = 0; i < sequence.size() - 1; i++) {
            differences.add(sequence.get(i + 1) - sequence.get(i));
        }
        return differences;
    }
}
