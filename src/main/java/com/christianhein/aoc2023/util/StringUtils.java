package com.christianhein.aoc2023.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static List<Integer> getIntsInString(String str) {
        Pattern regexInt = Pattern.compile("-?\\d+");
        Matcher matcher = regexInt.matcher(str);

        List<Integer> ints = new ArrayList<>();
        while (matcher.find()) {
            ints.add(Integer.parseInt(matcher.group()));
        }
        return ints;
    }

    public static List<Integer> getPositiveIntsInString(String str) {
        Pattern regexInt = Pattern.compile("\\d+");
        Matcher matcher = regexInt.matcher(str);

        List<Integer> ints = new ArrayList<>();
        while (matcher.find()) {
            ints.add(Integer.parseInt(matcher.group()));
        }
        return ints;
    }

    public static List<Long> getPositiveLongsInString(String str) {
        Pattern regexInt = Pattern.compile("\\d+");
        Matcher matcher = regexInt.matcher(str);

        List<Long> ints = new ArrayList<>();
        while (matcher.find()) {
            ints.add(Long.parseLong(matcher.group()));
        }
        return ints;
    }

    public static String[] splitStringIntoRepeatingGroups(String input) {
        //noinspection RegExpSuspiciousBackref
        return input.split("(?<=(.))(?!\\1)");
    }

    public static List<String[]> splitStringArrayOnBlankStrings(String[] input) {
        List<String[]> result = new ArrayList<>();

        int currentBlockStartIndex = 0;
        for (int i = 0; i < input.length; i++) {
            if (input[i].isBlank() || i == input.length - 1) {
                result.add(Arrays.copyOfRange(input, currentBlockStartIndex, i));
                currentBlockStartIndex = i + 1;
            }
        }
        return result;
    }
}
