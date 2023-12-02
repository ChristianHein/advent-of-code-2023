package org.example.day02;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {
    private final int id;
    private final Matcher gameStringMatcher;
    private final Pull[] pulls;

    public Game(String gameString) {
        this.gameStringMatcher = createRegexMatcher(gameString);
        if (!isValidGameString()) {
            throw new IllegalArgumentException("Wrong format for game string: \"" + gameString + "\"");
        }

        this.id = parseId();
        this.pulls = parsePulls();
    }

    public int getId() {
        return this.id;
    }

    public boolean isGamePossible(Map<BallColor, Integer> bagContents) {
        for (BallColor color : bagContents.keySet()) {
            for (Pull pull : pulls) {
                if (pull.getBallColorCount(color) > bagContents.get(color)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Map<BallColor, Integer> minimumSetOfCubes() {
        Map<BallColor, Integer> result = new HashMap<>();
        for (Pull pull : this.pulls) {
            Map<BallColor, Integer> ballCounts = pull.getBallCounts();
            for (BallColor ballColor : ballCounts.keySet()) {
                if (result.containsKey(ballColor)) {
                    result.put(ballColor, Math.max(result.get(ballColor), pull.getBallColorCount(ballColor)));
                } else {
                    result.put(ballColor, pull.getBallColorCount(ballColor));
                }
            }
        }
        return result;
    }

    private Pull[] parsePulls() {
        String pullsString = gameStringMatcher.group(2);
        String[] pullStrings = pullsString.split("; ");
        return Arrays.stream(pullStrings).map(Pull::new).toArray(Pull[]::new);
    }

    private static Matcher createRegexMatcher(String gameString) {
        String regexHeader = "Game (\\d+): ";
        String regexBallColors = "(red|green|blue)";
        String regexBallColorAndAmount = "\\d+ " + regexBallColors;
        String regexPull = regexBallColorAndAmount + "(, " + regexBallColorAndAmount + ")*";
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
