package org.example.day01;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalibrationValues {
    private static final HashMap<String, Integer> stringToDigit = new HashMap<>();

    static {
        stringToDigit.put("1", 1);
        stringToDigit.put("2", 2);
        stringToDigit.put("3", 3);
        stringToDigit.put("4", 4);
        stringToDigit.put("5", 5);
        stringToDigit.put("6", 6);
        stringToDigit.put("7", 7);
        stringToDigit.put("8", 8);
        stringToDigit.put("9", 9);
        stringToDigit.put("one", 1);
        stringToDigit.put("two", 2);
        stringToDigit.put("three", 3);
        stringToDigit.put("four", 4);
        stringToDigit.put("five", 5);
        stringToDigit.put("six", 6);
        stringToDigit.put("seven", 7);
        stringToDigit.put("eight", 8);
        stringToDigit.put("nine", 9);
    }

    public static int sumOfCalibrationValuesFromAmendedLines(String[] amendedStrings) {
        return Arrays.stream(amendedStrings)
                .mapToInt(CalibrationValues::calibrationValueFromAmendedLine)
                .reduce(0, Integer::sum);
    }

    public static int calibrationValueFromAmendedLine(String amendedString) {
        List<Integer> digits = getDigits(amendedString);

        if (digits.isEmpty()) {
            return 0;
        }

        int firstDigit = digits.get(0);
        int lastDigit = digits.get(digits.size() - 1);
        return 10 * firstDigit + lastDigit;
    }

    private static List<Integer> getDigits(String str) {
        String numberExpressionsRegex = String.join("|", stringToDigit.keySet());
        Pattern p = Pattern.compile(numberExpressionsRegex);
        Matcher m = p.matcher(str);

        // By default, the next regex find() call starts at the character after the last found group. This prevents
        // finding overlapping group results, which is why we use find(int) instead to specify the starting position.
        ArrayList<Integer> digits = new ArrayList<>();
        if (m.find()) {
            do {
                digits.add(stringToDigit.get(m.group()));
            } while (m.find(m.start() + 1));
        }
        return digits;
    }
}
