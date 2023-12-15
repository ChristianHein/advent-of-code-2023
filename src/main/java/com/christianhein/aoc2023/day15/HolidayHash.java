package com.christianhein.aoc2023.day15;

public class HolidayHash {
    public static int hash(String input) {
        int result = 0;
        for (int codePoint : input.codePoints().toArray()) {
            result += codePoint;
            result *= 17;
            result %= 256;
        }
        return result;
    }
}
