package org.example.day05;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static List<Long> getPositiveLongsInString(String str) {
        Pattern regexInt = Pattern.compile("\\d+");
        Matcher matcher = regexInt.matcher(str);

        List<Long> ints = new ArrayList<>();
        while (matcher.find()) {
            ints.add(Long.parseLong(matcher.group()));
        }
        return ints;
    }
}
