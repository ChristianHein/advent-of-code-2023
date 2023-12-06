package com.christianhein.aoc2023.day03;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;

class SchematicTest {
    Schematic simpleSchematic;
    Schematic schematicWithRepeats;

    @BeforeEach
    void setUp() {
        String[] simpleSchematicString = {
                "467..114..",
                "...*......",
                "..35..633.",
                "......#...",
                "617*......",
                ".....+.58.",
                "..592.....",
                "......755.",
                "...$.*....",
                ".664.598..",
                "........42",
        };
        simpleSchematic = new Schematic(simpleSchematicString);

        String[] schematicWithRepeatsString = {
                "467..114..",
                "...*......",
                "..35..467.",
                "......#...",
                "467*......",
        };
        schematicWithRepeats = new Schematic(schematicWithRepeatsString);
    }

    @Test
    void testNumberAtPosition() {
        Assertions.assertEquals(OptionalInt.of(467), simpleSchematic.numberAtPosition(new Point(0, 0)));
        Assertions.assertEquals(OptionalInt.of(467), simpleSchematic.numberAtPosition(new Point(1, 0)));
        Assertions.assertEquals(OptionalInt.of(467), simpleSchematic.numberAtPosition(new Point(2, 0)));

        Assertions.assertEquals(OptionalInt.empty(), simpleSchematic.numberAtPosition(new Point(3, 0)));
        Assertions.assertEquals(OptionalInt.empty(), simpleSchematic.numberAtPosition(new Point(4, 0)));

        Assertions.assertEquals(OptionalInt.of(114), simpleSchematic.numberAtPosition(new Point(5, 0)));
        Assertions.assertEquals(OptionalInt.of(114), simpleSchematic.numberAtPosition(new Point(6, 0)));
        Assertions.assertEquals(OptionalInt.of(114), simpleSchematic.numberAtPosition(new Point(7, 0)));

        Assertions.assertEquals(OptionalInt.empty(), simpleSchematic.numberAtPosition(new Point(8, 0)));
        Assertions.assertEquals(OptionalInt.empty(), simpleSchematic.numberAtPosition(new Point(9, 0)));

        Assertions.assertEquals(OptionalInt.of(42), simpleSchematic.numberAtPosition(new Point(8, 10)));
        Assertions.assertEquals(OptionalInt.of(42), simpleSchematic.numberAtPosition(new Point(9, 10)));
    }

    @Test
    void testGetParts() {
        Set<Point> expectedPartPositions = Set.of(
                new Point(3, 1),
                new Point(6, 3),
                new Point(3, 4),
                new Point(5, 5),
                new Point(3, 8),
                new Point(5, 8)
        );
        Set<Point> actualPartPositions = simpleSchematic.getParts();

        Assertions.assertEquals(expectedPartPositions, actualPartPositions);
    }

    @Test
    void testGetAllPartNumbers() {
        List<Integer> expectedPartNumbers = List.of(
                467,
                35,
                633,
                617,
                592,
                755,
                664,
                598
        );
        List<Integer> actualPartNumbers = simpleSchematic.getAllPartNumbers();

        Assertions.assertArrayEquals(
                expectedPartNumbers.stream().sorted().toArray(),
                actualPartNumbers.stream().sorted().toArray());
    }

    @Test
    void testGetAllPartNumbersWithRepeats() {
        List<Integer> expectedPartNumbers = List.of(
                467,
                35,
                467,
                467
        );
        List<Integer> actualPartNumbers = schematicWithRepeats.getAllPartNumbers();

        Assertions.assertArrayEquals(
                expectedPartNumbers.stream().sorted().toArray(),
                actualPartNumbers.stream().sorted().toArray());
    }
}