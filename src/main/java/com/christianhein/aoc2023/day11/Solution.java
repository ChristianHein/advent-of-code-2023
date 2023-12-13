package com.christianhein.aoc2023.day11;

import com.christianhein.aoc2023.util.Grid2D;
import com.christianhein.aoc2023.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    private final Grid2D<Character> universe;
    private final Grid2D<Character> expandedUniverse;

    public Solution(String[] input) {
        this.universe = new Grid2D<>(Arrays.stream(input)
                .map(String::chars)
                .map(s -> s.mapToObj(c -> (char) c).toArray(Character[]::new))
                .toArray(Character[][]::new));

        var expandedUniverse = new Grid2D<>(this.universe.to2DList());
        expandUniverse(expandedUniverse);
        this.expandedUniverse = expandedUniverse;
    }

    public int part1() {
        return getSumOfGalaxyDistances(expandedUniverse);
    }

    public long part2() {
        long distanceSum = getSumOfGalaxyDistances(universe);
        long expandedDistanceSum = getSumOfGalaxyDistances(expandedUniverse);
        System.out.println("distanceSum: " + distanceSum);
        System.out.println("expandedDistanceSum: " + expandedDistanceSum);
        return distanceSum + 999_999 * (expandedDistanceSum - distanceSum);
    }

    private void expandUniverse(Grid2D<Character> universe) {
        for (int rowIndex = universe.getWidth() - 1; rowIndex >= 0; --rowIndex) {
            if (!universe.getRow(rowIndex).contains('#')) {
                universe.insertRow(rowIndex, universe.getRow(rowIndex));
            }
        }
        for (int colIndex = universe.getWidth() - 1; colIndex >= 0; --colIndex) {
            if (!universe.getCol(colIndex).contains('#')) {
                universe.insertCol(colIndex, universe.getCol(colIndex));
            }
        }
    }

    private int getSumOfGalaxyDistances(Grid2D<Character> universe) {
        List<Pair<Integer, Integer>> galaxies = findGalaxies(universe);
        int sumOfDistances = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i; j < galaxies.size(); j++) {
                var galaxyI = galaxies.get(i);
                var galaxyJ = galaxies.get(j);
                int yDelta = Math.abs(galaxyI.left() - galaxyJ.left());
                int xDelta = Math.abs(galaxyI.right() - galaxyJ.right());
                sumOfDistances += yDelta + xDelta;
            }
        }
        return sumOfDistances;
    }

    private List<Pair<Integer, Integer>> findGalaxies(Grid2D<Character> universe) {
        List<Pair<Integer, Integer>> galaxies = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < universe.getHeight(); rowIndex++) {
            for (int colIndex = 0; colIndex < universe.getWidth(); colIndex++) {
                if (universe.get(rowIndex, colIndex) == '#') {
                    galaxies.add(new Pair<>(rowIndex, colIndex));
                }
            }
        }
        return galaxies;
    }
}
