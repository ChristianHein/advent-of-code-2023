package com.christianhein.aoc2023.day08;

import com.christianhein.aoc2023.util.Pair;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Solution {
    private final char[] navigationInstructions;
    // Assumption: Every node is uniquely identified by its value
    private final Map<String, Pair<String, String>> nodes;

    public Solution(String[] input) {
        this.navigationInstructions = input[0].toCharArray();
        this.nodes = parseNodes(Arrays.copyOfRange(input, 2, input.length));
    }

    public String part1() {
        final String START_NODE = "AAA";
        final String END_NODE = "ZZZ";

        int steps = 0;
        String currentNode = START_NODE;
        while (true) {
            for (char instruction : navigationInstructions) {
                currentNode = switch (instruction) {
                    case 'L' -> nodes.get(currentNode).left();
                    case 'R' -> nodes.get(currentNode).right();
                    default -> throw new IllegalArgumentException();
                };

                steps++;
                if (currentNode.equals(END_NODE)) {
                    return String.valueOf(steps);
                }
            }
        }
    }

    public String part2() {
        final Predicate<String> isStartNode = s -> s.endsWith("A");
        final Predicate<String> isEndNode = s -> s.endsWith("Z");

        List<String> startNodes = nodes.keySet().stream()
                .filter(isStartNode)
                .collect(Collectors.toCollection(ArrayList::new));

        // Assumption made: From every start node, only one end node can be
        // reached. Therefore, we only have to record a single start->end
        // distance per start node.
        List<Integer> startToEndDistances = new ArrayList<>(startNodes.size());

        for (int startNodeIndex = 0; startNodeIndex < startNodes.size(); startNodeIndex++) {
            String node = startNodes.get(startNodeIndex);
            ArrayList<Pair<String, Integer>> visitedNodesAtInstructionIndex = new ArrayList<>();

            int steps = 0;
            outer:
            while (true) {
                for (int i = 0; i < navigationInstructions.length; i++) {
                    Pair<String, Integer> nodeAndInstrIndex = new Pair<>(node, i);
                    if (visitedNodesAtInstructionIndex.contains(nodeAndInstrIndex)) {
                        break outer;
                    }
                    visitedNodesAtInstructionIndex.add(nodeAndInstrIndex);

                    Pair<String, String> nodeDests = nodes.get(node);
                    switch (navigationInstructions[i]) {
                        case 'L' -> node = nodeDests.left();
                        case 'R' -> node = nodeDests.right();
                        default -> {
                            assert false;
                        }
                    }

                    steps++;
                    if (isEndNode.test(node)) {
                        startToEndDistances.add(startNodeIndex, steps);
                    }
                }
            }
        }

        // The number of steps after which all start nodes simultaneously reach
        // an end node is equal to the lowest common multiple of the distances
        // of each start node to their own end node.
        BigInteger lowestCommonMultiple = startToEndDistances.stream()
                .map(BigInteger::valueOf)
                .reduce(BigInteger.ONE, Solution::leastCommonMultiple);
        return String.valueOf(lowestCommonMultiple);
    }

    private static Map<String, Pair<String, String>> parseNodes(String[] input) {
        Map<String, Pair<String, String>> nodes = new HashMap<>();

        for (String nodeLine : input) {
            Pattern linePattern = Pattern.compile("(?<node>.*) = \\((?<left>.*), (?<right>.*)\\)");
            Matcher matcher = linePattern.matcher(nodeLine);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Bad node lines format.");
            }

            String node = matcher.group("node");
            String leftDestination = matcher.group("left");
            String rightDestination = matcher.group("right");
            nodes.put(node, new Pair<>(leftDestination, rightDestination));
        }

        return nodes;
    }

    private static BigInteger leastCommonMultiple(BigInteger a, BigInteger b) {
        return a.multiply(b).abs().divide(a.gcd(b));
    }
}
