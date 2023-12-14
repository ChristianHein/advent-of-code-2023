package com.christianhein.aoc2023.day14;

import com.christianhein.aoc2023.util.Grid2D;

import java.util.*;

public class DishPlatform {
    private final Grid2D<Character> platform;
    private static final char ROUND_ROCK_SYMBOL = 'O';
    @SuppressWarnings("unused")
    private static final char CUBE_ROCK_SYMBOL = '#';
    private static final char EMPTY_SPACE_SYMBOL = '.';

    public enum Direction {North, East, South, West}

    public DishPlatform(String[] input) {
        Character[][] inputCharArray = Arrays.stream(input)
                .map(String::chars)
                .map(s -> s.mapToObj(c -> (char) c).toArray(Character[]::new))
                .toArray(Character[][]::new);
        this.platform = new Grid2D<>(inputCharArray);
    }

    public void cyclePlatform() {
        tiltPlatform(Direction.North);
        tiltPlatform(Direction.West);
        tiltPlatform(Direction.South);
        tiltPlatform(Direction.East);
    }

    public void cyclePlatform(int count) {
        Map<Grid2D<Character>, Integer> seenRockConfigurations = new HashMap<>();

        for (int i = 0; i < count; i++) {
            cyclePlatform();

            if (seenRockConfigurations.containsKey(platform)) {
                int lastSeenIndex = seenRockConfigurations.get(platform);
                int loopLength = i - lastSeenIndex;
                i = count - 1 - ((count - 1 - i) % loopLength);
            }
            seenRockConfigurations.put(platform, i);
        }
    }

    public void tiltPlatform(Direction direction) {
        switch (direction) {
            case North -> {
                tiltPlatformNorth(platform);
            }
            case East -> {
                tiltPlatformEast(platform);
            }
            case South -> {
                tiltPlatformSouth(platform);
            }
            case West -> {
                tiltPlatformWest(platform);
            }
        }
    }

    public int totalLoadOnNorthSupportBeams() {
        int totalLoad = 0;
        for (int rowIndex = 0; rowIndex < platform.getHeight(); rowIndex++) {
            for (int colIndex = 0; colIndex < platform.getWidth(); colIndex++) {
                if (platform.get(rowIndex, colIndex) == ROUND_ROCK_SYMBOL) {
                    totalLoad += platform.getHeight() - rowIndex;
                }
            }
        }
        return totalLoad;
    }

    private static void tiltPlatformNorth(Grid2D<Character> platform) {
        for (int rowIndex = 0; rowIndex < platform.getHeight(); rowIndex++) {
            for (int colIndex = 0; colIndex < platform.getWidth(); colIndex++) {
                if (platform.get(rowIndex, colIndex) == ROUND_ROCK_SYMBOL) {
                    int newRockRowIndex = positionOfRockAfterSlidingLeft(platform.getCol(colIndex), rowIndex);
                    platform.set(rowIndex, colIndex, EMPTY_SPACE_SYMBOL);
                    platform.set(newRockRowIndex, colIndex, ROUND_ROCK_SYMBOL);
                }
            }
        }
    }

    private static void tiltPlatformEast(Grid2D<Character> platform) {
        for (int colIndex = platform.getWidth() - 1; colIndex >= 0; --colIndex) {
            for (int rowIndex = 0; rowIndex < platform.getHeight(); rowIndex++) {
                if (platform.get(rowIndex, colIndex) == ROUND_ROCK_SYMBOL) {
                    int newRockColIndex = positionOfRockAfterSlidingRight(platform.getRow(rowIndex), colIndex);
                    platform.set(rowIndex, colIndex, EMPTY_SPACE_SYMBOL);
                    platform.set(rowIndex, newRockColIndex, ROUND_ROCK_SYMBOL);
                }
            }
        }
    }

    private static void tiltPlatformSouth(Grid2D<Character> platform) {
        for (int rowIndex = platform.getHeight() - 1; rowIndex >= 0; --rowIndex) {
            for (int colIndex = 0; colIndex < platform.getWidth(); colIndex++) {
                if (platform.get(rowIndex, colIndex) == ROUND_ROCK_SYMBOL) {
                    int newRockRowIndex = positionOfRockAfterSlidingRight(platform.getCol(colIndex), rowIndex);
                    platform.set(rowIndex, colIndex, EMPTY_SPACE_SYMBOL);
                    platform.set(newRockRowIndex, colIndex, ROUND_ROCK_SYMBOL);
                }
            }
        }
    }

    private static void tiltPlatformWest(Grid2D<Character> platform) {
        for (int colIndex = 0; colIndex < platform.getWidth(); colIndex++) {
            for (int rowIndex = 0; rowIndex < platform.getHeight(); rowIndex++) {
                if (platform.get(rowIndex, colIndex) == ROUND_ROCK_SYMBOL) {
                    int newRockColIndex = positionOfRockAfterSlidingLeft(platform.getRow(rowIndex), colIndex);
                    platform.set(rowIndex, colIndex, EMPTY_SPACE_SYMBOL);
                    platform.set(rowIndex, newRockColIndex, ROUND_ROCK_SYMBOL);
                }
            }
        }
    }

    private static int positionOfRockAfterSlidingLeft(List<Character> pattern, int rockPosition) {
        int i = rockPosition - 1;
        while (i >= 0 && pattern.get(i) == EMPTY_SPACE_SYMBOL) {
            --i;
        }
        return i + 1;
    }

    private static int positionOfRockAfterSlidingRight(List<Character> pattern, int rockPosition) {
        int i = rockPosition + 1;
        while (i < pattern.size() && pattern.get(i) == EMPTY_SPACE_SYMBOL) {
            i++;
        }
        return i - 1;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(platform.to2DList().toArray())
                .replace("], [", "\n")
                .replace("[", "")
                .replace("]", "")
                .replace(", ", "");
    }
}
