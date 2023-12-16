package com.christianhein.aoc2023.day16;

import com.christianhein.aoc2023.day16.MirrorContraptionGrid.Direction;
import com.christianhein.aoc2023.util.IOUtils;
import com.christianhein.aoc2023.util.Pair;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String inputFilepath = "input-day16.txt";
        String[] input = IOUtils.linesFromResource(inputFilepath);

        System.out.println("Solution to Day 16 puzzle (part 1): " + part1Solution(input));
        System.out.println("Solution to Day 16 puzzle (part 2): " + part2Solution(input));
    }

    private static String part1Solution(String[] input) {
        MirrorContraptionGrid contraption = new MirrorContraptionGrid(input);
        var energizedTiles = contraption.energizedTilesFromBeam(new Pair<>(0, 0), Direction.RIGHT);
        return String.valueOf(energizedTiles.size());
    }

    private static String part2Solution(String[] input) {
        MirrorContraptionGrid contraption = new MirrorContraptionGrid(input);

        int max = 0;
        // Left edge tiles
        for (int row = 0; row < input.length; row++) {
            var energizedTiles = contraption.energizedTilesFromBeam(new Pair<>(row, 0), Direction.RIGHT);
            max = Math.max(max, energizedTiles.size());
        }
        // Right edge tiles
        for (int row = 0; row < input.length; row++) {
            var energizedTiles = contraption.energizedTilesFromBeam(new Pair<>(row, input[0].length() - 1), Direction.LEFT);
            max = Math.max(max, energizedTiles.size());
        }
        // Top edge tiles
        for (int col = 0; col < input.length; col++) {
            var energizedTiles = contraption.energizedTilesFromBeam(new Pair<>(0, col), Direction.DOWN);
            max = Math.max(max, energizedTiles.size());
        }
        // Bottom edge tiles
        for (int col = 0; col < input.length; col++) {
            var energizedTiles = contraption.energizedTilesFromBeam(new Pair<>(input.length - 1, col), Direction.UP);
            max = Math.max(max, energizedTiles.size());
        }
        return String.valueOf(max);
    }
}
