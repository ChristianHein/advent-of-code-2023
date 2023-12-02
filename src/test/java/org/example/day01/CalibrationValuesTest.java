package org.example.day01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.example.day01.CalibrationValues.calibrationValueFromAmendedLine;

class CalibrationValuesTest {
    @Test
    void testPuzzle1AmendedLines() {
        Assertions.assertEquals(0, calibrationValueFromAmendedLine(""));
        Assertions.assertEquals(11, calibrationValueFromAmendedLine("1"));
        Assertions.assertEquals(11, calibrationValueFromAmendedLine("a1"));
        Assertions.assertEquals(11, calibrationValueFromAmendedLine("1a"));
        Assertions.assertEquals(11, calibrationValueFromAmendedLine("a1a"));
        Assertions.assertEquals(21, calibrationValueFromAmendedLine("a21c"));
        Assertions.assertEquals(12, calibrationValueFromAmendedLine("a1b2c"));
        Assertions.assertEquals(21, calibrationValueFromAmendedLine("a2b1c"));
        Assertions.assertEquals(23, calibrationValueFromAmendedLine("a2b1c3d"));
    }

    @Test
    void testPuzzle2AmendedLines() {
        Assertions.assertEquals(45, calibrationValueFromAmendedLine("fnxqvsvqbzxgkfour5"));
        Assertions.assertEquals(88, calibrationValueFromAmendedLine("eightjzqzhrllg1oneightfck"));
    }
}