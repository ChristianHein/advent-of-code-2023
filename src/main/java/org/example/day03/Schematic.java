package org.example.day03;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;

public class Schematic {
    public final int width;
    public final int height;
    public final String[] lines;

    public Schematic(String[] schematicLines) {
        this.lines = schematicLines;

        if (schematicLines == null || schematicLines.length == 0) {
            this.width = 0;
            this.height = 0;
        } else {
            this.width = schematicLines[0].length();
            this.height = schematicLines.length;
        }
    }

    public char getSymbol(Point position) {
        assert (0 <= position.x && position.x < width);
        assert (0 <= position.y && position.y < height);

        return lines[position.y].charAt(position.x);
    }

    public boolean isValidPosition(Point position) {
        return 0 <= position.x && position.x < width
                && 0 <= position.y && position.y < height;
    }

    public boolean isGear(Point position) {
        return isValidPosition(position) && getSymbol(position) == '*' && getPartNumbers(position).size() == 2;
    }

    // Assumption made: A part will only be surrounded by unique part numbers.
    public Set<Integer> getPartNumbers(Point part) {
        Set<Integer> partNumbers = new HashSet<>();
        for (Point surroundingPosition : surroundingPositions(part)) {
            OptionalInt number = numberAtPosition(surroundingPosition);
            if (number.isPresent()) {
                partNumbers.add(number.getAsInt());
            }
        }
        return partNumbers;
    }

    public List<Integer> getAllPartNumbers() {
        List<Integer> allPartNumbers = new ArrayList<>();
        getParts()
                .stream()
                .map(this::getPartNumbers)
                .forEach(allPartNumbers::addAll);
        return allPartNumbers;
    }

    public Set<Point> getParts() {
        Set<Point> partPositions = new HashSet<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char symbol = getSymbol(new Point(x, y));
                boolean isPart = symbol != '.' && !Character.isDigit(symbol);
                if (isPart) {
                    partPositions.add(new Point(x, y));
                }
            }
        }
        return partPositions;
    }

    public Set<Point> getGears() {
        return getParts().stream().filter(this::isGear).collect(Collectors.toSet());
    }

    public OptionalInt numberAtPosition(Point position) {
        assert (isValidPosition(position));

        if (!Character.isDigit(getSymbol(position))) {
            return OptionalInt.empty();
        }

        StringBuilder intStr = new StringBuilder();
        intStr.append(getSymbol(position));

        for (int x = position.x + 1; x < this.width; x++) {
            char symbol = getSymbol(new Point(x, position.y));
            if (Character.isDigit(symbol)) {
                intStr.append(symbol);
            } else {
                break;
            }
        }
        for (int x = position.x - 1; x >= 0; x--) {
            char symbol = getSymbol(new Point(x, position.y));
            if (Character.isDigit(symbol)) {
                intStr.insert(0, symbol);
            } else {
                break;
            }
        }

        int result = Integer.parseInt(intStr.toString());
        return OptionalInt.of(result);
    }

    private Set<Point> surroundingPositions(Point position) {
        Set<Point> positions = new HashSet<>();
        positions.add(new Point(position.x - 1, position.y - 1));
        positions.add(new Point(position.x, position.y - 1));
        positions.add(new Point(position.x + 1, position.y - 1));
        positions.add(new Point(position.x - 1, position.y));
        positions.add(new Point(position.x, position.y));
        positions.add(new Point(position.x + 1, position.y));
        positions.add(new Point(position.x - 1, position.y + 1));
        positions.add(new Point(position.x, position.y + 1));
        positions.add(new Point(position.x + 1, position.y + 1));

        return positions
                .stream()
                .filter(this::isValidPosition)
                .collect(Collectors.toSet());
    }
}
