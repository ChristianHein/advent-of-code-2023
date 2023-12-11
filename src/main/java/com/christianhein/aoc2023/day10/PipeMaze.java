package com.christianhein.aoc2023.day10;

import com.christianhein.aoc2023.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class PipeMaze {
    public final char[][] grid;
    public final int height;
    public final int width;

    public PipeMaze(String[] input) {
        this.grid = Arrays.stream(input)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        this.height = grid.length;
        this.width = this.height == 0 ? 0 : grid[0].length;
    }

    public Pair<Integer, Integer> getFirstAnimalPosition() {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < height; column++) {
                Pair<Integer, Integer> pos = new Pair<>(column, row);
                if (isAnimal(charAt(pos))) {
                    return pos;
                }
            }
        }
        throw new RuntimeException();
    }

    public boolean isPipe(Pair<Integer, Integer> coords) {
        return isPipe(charAt(coords));
    }

    private boolean isPipe(char symbol) {
        return String.valueOf(symbol).matches(".*[-LJ7F|S].*");
    }

    public boolean isAnimal(Pair<Integer, Integer> coords) {
        return isAnimal(charAt(coords));
    }

    private boolean isAnimal(char symbol) {
        return symbol == 'S';
    }

    public List<Pair<Integer, Integer>> getMainLoopPipes() {
        Pair<Integer, Integer> start = getFirstAnimalPosition();
        List<Pair<Integer, Integer>> mainPipes = new ArrayList<>();
        mainPipes.add(start);

        Optional<Pair<Integer, Integer>> nextPipe = neighboringValidPositions(start).stream()
                .filter(pos -> connectedPipes(pos).contains(start))
                .findAny();
        mainPipes.add(nextPipe.orElseThrow());

        while (nextPipe.isPresent()) {
            mainPipes.add(nextPipe.get());
            nextPipe = connectedPipes(nextPipe.get()).stream()
                    .filter(pipe -> !mainPipes.contains(pipe))
                    .findAny();
        }
        return mainPipes;
    }

    // Doesn't require BFS - just count the length of the circuit
    public int calculateMainPipeLength() {
        //Pair<Integer, Integer> startingPos = getFirstAnimalPosition();
        //Queue<Pair<Integer, Integer>> queue = new ArrayDeque<>(connectedPipes(startingPos));
        //Set<Pair<Integer, Integer>> explored = new HashSet<>();
        //int count = 0;
        //while (!queue.isEmpty()) {
        //    Pair<Integer, Integer> pos = queue.remove();
        //    if (pos == startingPos) {
        //        break;
        //    }
        //    explored.add(pos);
        //    queue.addAll(connectedPipes(pos));
        //    queue.removeAll(explored);
        //    count++;
        //}
        //System.out.println(queue);
        //return count;
        return getMainLoopPipes().size();
    }

    public List<Pair<Integer, Integer>> connectedPipes(Pair<Integer, Integer> coords) {
        List<Pair<Integer, Integer>> possiblePositions;
        char symbol = charAt(coords);
        possiblePositions = switch (symbol) {
            case '|' -> neighboringValidPositions(coords, List.of(Direction.NORTH, Direction.SOUTH));
            case '-' -> neighboringValidPositions(coords, List.of(Direction.EAST, Direction.WEST));
            case 'L' -> neighboringValidPositions(coords, List.of(Direction.NORTH, Direction.EAST));
            case 'J' -> neighboringValidPositions(coords, List.of(Direction.NORTH, Direction.WEST));
            case '7' -> neighboringValidPositions(coords, List.of(Direction.SOUTH, Direction.WEST));
            case 'F' -> neighboringValidPositions(coords, List.of(Direction.SOUTH, Direction.EAST));
            case 'S' -> neighboringValidPositions(coords);
            default -> List.of();
        };
        return possiblePositions.stream()
                .filter(pos -> this.isPipe(pos) || this.isAnimal(pos))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Pair<Integer, Integer>> tilesEnclosedByMainLoop() {
        return List.of();
    }

    private enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST,
    }

    private List<Pair<Integer, Integer>> neighboringValidPositions(Pair<Integer, Integer> coords) {
        return neighboringValidPositions(
                coords,
                List.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST));
    }

    private List<Pair<Integer, Integer>> neighboringValidPositions(Pair<Integer, Integer> coords, List<Direction> directions) {
        List<Pair<Integer, Integer>> possiblePositions = new ArrayList<>();
        if (directions.contains(Direction.NORTH)) {
            possiblePositions.add(new Pair<>(coords.left(), coords.right() - 1));
        }
        if (directions.contains(Direction.EAST)) {
            possiblePositions.add(new Pair<>(coords.left() + 1, coords.right()));
        }
        if (directions.contains(Direction.SOUTH)) {
            possiblePositions.add(new Pair<>(coords.left(), coords.right() + 1));
        }
        if (directions.contains(Direction.WEST)) {
            possiblePositions.add(new Pair<>(coords.left() - 1, coords.right()));
        }

        return possiblePositions.stream()
                .filter(this::isValidPosition)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean isValidPosition(Pair<Integer, Integer> coords) {
        return 0 <= coords.left() && coords.left() < height
                && 0 <= coords.right() && coords.right() < width;
    }

    private char charAt(Pair<Integer, Integer> coords) {
        return grid[coords.right()][coords.left()];
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (char[] row : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                result.append(row[j]);
            }
            result.append('\n');
        }
        return result.toString();
    }
}
