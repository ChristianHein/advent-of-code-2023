package com.christianhein.aoc2023.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringUtilsTest {
    @Test
    void testSplitStringIntoRepeatingGroups() {
        Assertions.assertArrayEquals(new String[]{""}, StringUtils.splitStringIntoRepeatingGroups(""));
        Assertions.assertArrayEquals(new String[]{"a"}, StringUtils.splitStringIntoRepeatingGroups("a"));
        Assertions.assertArrayEquals(new String[]{"aa"}, StringUtils.splitStringIntoRepeatingGroups("aa"));
        Assertions.assertArrayEquals(new String[]{"a", "b"}, StringUtils.splitStringIntoRepeatingGroups("ab"));
        Assertions.assertArrayEquals(new String[]{"aa", "bb"}, StringUtils.splitStringIntoRepeatingGroups("aabb"));
        Assertions.assertArrayEquals(new String[]{"aa", "bb"}, StringUtils.splitStringIntoRepeatingGroups("aabb"));
        Assertions.assertArrayEquals(new String[]{"a", "bb", "a"}, StringUtils.splitStringIntoRepeatingGroups("abba"));
        Assertions.assertArrayEquals(new String[]{"a", "bb", "c"}, StringUtils.splitStringIntoRepeatingGroups("abbc"));

    }
}