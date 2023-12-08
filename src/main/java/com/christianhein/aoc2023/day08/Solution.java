package com.christianhein.aoc2023.day08;

import com.christianhein.aoc2023.util.Pair;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Solution {
    private static final class GraphNode<T> {
        public T val;
        public GraphNode<T> left;
        public GraphNode<T> right;

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var other = (GraphNode<?>) obj;
            return Objects.equals(this.val, other.val);
        }

        @Override
        public int hashCode() {
            return Objects.hash(val);
        }
    }

    Map<String, Pair<String, String>> nodes = new HashMap<>();
    List<GraphNode<String>> startingNodes;

    public String part1(String[] input) {
        String navigation = input[0];
        parseInput(input);

        int numSteps = 0;
        String currentNode = "AAA";
        while (true) {
            for (int i = 0; i < navigation.length(); i++) {
                currentNode = switch (navigation.charAt(i)) {
                    case 'L' -> nodes.get(currentNode).left();
                    case 'R' -> nodes.get(currentNode).right();
                    default -> throw new IllegalArgumentException();
                };
                numSteps++;
                if (currentNode.equals("ZZZ")) {
                    return String.valueOf(numSteps);
                }
            }
        }
    }

    private void parseInput(String[] input) {
        for (int i = 2; i < input.length; i++) {
            String[] a = input[i].split(" = ", 2);
            String val = a[0];
            String[] b = a[1].split(", ", 2);
            String left = b[0].substring(1);
            String right = b[1].substring(0, b[1].length() - 1);
            this.nodes.put(val, new Pair<>(left, right));
        }
    }

    private void constructGraph() {
        Map<String, GraphNode<String>> graphedNodes = new HashMap<>();

        for (var entry : this.nodes.entrySet()) {
            String val = entry.getKey();
            GraphNode<String> currNode = graphedNodes.get(val);
            if (currNode == null) {
                currNode = new GraphNode<>();
                currNode.val = val;
                graphedNodes.put(val, currNode);
            }
            String leftVal = entry.getValue().left();
            currNode.left = graphedNodes.get(leftVal);
            if (currNode.left == null) {
                currNode.left = new GraphNode<>();
                currNode.left.val = leftVal;
                graphedNodes.put(leftVal, currNode.left);
            }
            String rightVal = entry.getValue().right();
            currNode.right = graphedNodes.get(rightVal);
            if (currNode.right == null) {
                currNode.right = new GraphNode<>();
                currNode.right.val = rightVal;
                graphedNodes.put(rightVal, currNode.right);
            }
        }

        this.startingNodes = graphedNodes.values().stream().filter(node -> node.val.endsWith("A"))
                .collect(Collectors.toList());
    }

    public String part2Smart(String[] input) {
        char[] navigation = input[0].toCharArray();
        parseInput(input);
        constructGraph();

        List<List<Integer>> distancesPerNode = new ArrayList<>(startingNodes.size());
        for (int i = 0; i < startingNodes.size(); i++) {
            distancesPerNode.add(i, new ArrayList<>());
        }

        // For each starting node, find all distances to any end node until we start looping
        for (int i = 0; i < startingNodes.size(); i++) {
            GraphNode<String> node = startingNodes.get(i);
            ArrayList<Pair<GraphNode<String>, Integer>> visitedAndCharIndex = new ArrayList<>();
            int numSteps = 0;
            outer:
            while (true) {
                for (int d = 0; d < navigation.length; d++) {
                    if (visitedAndCharIndex.contains(new Pair<>(node, d))) {
                        break outer;
                    }
                    visitedAndCharIndex.add(new Pair<>(node, d));
                    switch (navigation[d]) {
                        case 'L' -> node = node.left;
                        case 'R' -> node = node.right;
                        default -> {
                            assert false;
                        }
                    }
                    numSteps++;
                    if (node.val.endsWith("Z")) {
                        System.out.println("distance: " + numSteps + " (d: " + d + ")");
                        distancesPerNode.get(i).add(numSteps);
                    }
                }
            }
        }

        // The answer is the smallest common distance in all these sets
        // TODO: cleanup, generalize
        List<Integer> values = distancesPerNode.stream().map(List::getFirst).toList();

        BigInteger lcm = values.stream()
                .map(BigInteger::valueOf)
                .reduce(BigInteger.ONE, Solution::lcm);
        return String.valueOf(lcm);
    }

    private static BigInteger lcm(BigInteger a, BigInteger b) {
        return a.multiply(b).divide(a.gcd(b));
    }
}
