package com.christianhein.aoc2023.day16;

import com.christianhein.aoc2023.util.Grid2D;
import com.christianhein.aoc2023.util.Pair;

import java.util.*;

public class MirrorContraptionGrid {
    private final Grid2D<Character> grid;

    public MirrorContraptionGrid(String[] input) {
        Character[][] inputCharArray = Arrays.stream(input)
                .map(String::chars)
                .map(s -> s.mapToObj(c -> (char) c).toArray(Character[]::new))
                .toArray(Character[][]::new);
        this.grid = new Grid2D<>(inputCharArray);
    }

    public enum Direction {UP, DOWN, LEFT, RIGHT}

    public Set<Pair<Integer, Integer>> energizedTilesFromBeam(Pair<Integer, Integer> startPos, Direction beamDirection) {
        HashMap<Pair<Integer, Integer>, Set<Direction>> visited = new HashMap<>();

        List<Pair<Pair<Integer, Integer>, Direction>> backlog = new ArrayList<>();
        backlog.add(new Pair<>(startPos, beamDirection));

        while (!backlog.isEmpty()) {
            var currentTile = backlog.removeLast();
            int currentRow = currentTile.left().left();
            int currentCol = currentTile.left().right();
            Direction currentDirection = currentTile.right();
            if (currentRow < 0 || currentRow >= grid.getHeight() || currentCol < 0 || currentCol >= grid.getWidth()) {
                continue;
            }

            if (visited.containsKey(currentTile.left())) {
                Set<Direction> directions = visited.get(currentTile.left());
                if (directions.contains(currentDirection)) {
                    continue;
                }
                visited.get(currentTile.left()).add(currentDirection);
            } else {
                Set<Direction> directions = new HashSet<>();
                directions.add(currentDirection);
                visited.put(new Pair<>(currentRow, currentCol), directions);
            }

            var tileUp = new Pair<>(new Pair<>(currentRow - 1, currentCol), Direction.UP);
            var tileDown = new Pair<>(new Pair<>(currentRow + 1, currentCol), Direction.DOWN);
            var tileLeft = new Pair<>(new Pair<>(currentRow, currentCol - 1), Direction.LEFT);
            var tileRight = new Pair<>(new Pair<>(currentRow, currentCol + 1), Direction.RIGHT);

            switch (grid.get(currentRow, currentCol)) {
                case '.' -> {
                    switch (currentDirection) {
                        case UP -> backlog.add(tileUp);
                        case DOWN -> backlog.add(tileDown);
                        case LEFT -> backlog.add(tileLeft);
                        case RIGHT -> backlog.add(tileRight);
                    }
                }
                case '/' -> {
                    switch (currentDirection) {
                        case UP -> backlog.add(tileRight);
                        case DOWN -> backlog.add(tileLeft);
                        case LEFT -> backlog.add(tileDown);
                        case RIGHT -> backlog.add(tileUp);
                    }
                }
                case '\\' -> {
                    switch (currentDirection) {
                        case UP -> backlog.add(tileLeft);
                        case DOWN -> backlog.add(tileRight);
                        case LEFT -> backlog.add(tileUp);
                        case RIGHT -> backlog.add(tileDown);
                    }
                }
                case '-' -> {
                    switch (currentDirection) {
                        case UP, DOWN -> {
                            backlog.add(tileLeft);
                            backlog.add(tileRight);
                        }
                        case LEFT -> backlog.add(tileLeft);
                        case RIGHT -> backlog.add(tileRight);
                    }
                }
                case '|' -> {
                    switch (currentDirection) {
                        case UP -> backlog.add(tileUp);
                        case DOWN -> backlog.add(tileDown);
                        case LEFT, RIGHT -> {
                            backlog.add(tileUp);
                            backlog.add(tileDown);
                        }
                    }
                }
                default -> throw new RuntimeException("Unknown symbol encountered on mirror contraption: '"
                        + grid.get(currentRow, currentCol) + "'");
            }
        }

        return visited.keySet();
    }
}
