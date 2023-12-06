package com.christianhein.aoc2023.day02;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {
    private final int id;
    private final Matcher gameStringMatcher;
    private final BagPull[] bagPulls;

    public Game(String gameString) {
        this.gameStringMatcher = createRegexMatcher(gameString);
        if (!isValidGameString()) {
            throw new IllegalArgumentException("Wrong format for game string: \"" + gameString + "\"");
        }

        this.id = parseId();
        this.bagPulls = parsePulls();
    }

    public int getId() {
        return this.id;
    }

    public boolean isGamePossible(Map<CubeColor, Integer> bagContents) {
        for (CubeColor color : bagContents.keySet()) {
            for (BagPull bagPull : bagPulls) {
                if (bagPull.getCubeColorCount(color) > bagContents.get(color)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Map<CubeColor, Integer> minimumSetOfCubes() {
        Map<CubeColor, Integer> result = new HashMap<>();
        for (BagPull bagPull : this.bagPulls) {
            Map<CubeColor, Integer> cubeCounts = bagPull.getCubeCounts();
            for (CubeColor cubeColor : cubeCounts.keySet()) {
                if (result.containsKey(cubeColor)) {
                    result.put(cubeColor, Math.max(result.get(cubeColor), bagPull.getCubeColorCount(cubeColor)));
                } else {
                    result.put(cubeColor, bagPull.getCubeColorCount(cubeColor));
                }
            }
        }
        return result;
    }

    private BagPull[] parsePulls() {
        String pullsString = gameStringMatcher.group(2);
        String[] pullStrings = pullsString.split("; ");
        return Arrays.stream(pullStrings).map(BagPull::new).toArray(BagPull[]::new);
    }

    private static Matcher createRegexMatcher(String gameString) {
        String regexHeader = "Game (\\d+): ";
        String regexCubeColors = "(red|green|blue)";
        String regexCubeColorAndAmount = "\\d+ " + regexCubeColors;
        String regexPull = regexCubeColorAndAmount + "(, " + regexCubeColorAndAmount + ")*";
        String regexMultiplePulls = regexPull + "(; " + regexPull + ")*";
        String regexGameString = regexHeader + "(" + regexMultiplePulls + ")";

        Pattern p = Pattern.compile(regexGameString);
        return p.matcher(gameString);
    }

    private boolean isValidGameString() {
        return this.gameStringMatcher.matches();
    }

    private int parseId() {
        return Integer.parseInt(this.gameStringMatcher.group(1));
    }
}
