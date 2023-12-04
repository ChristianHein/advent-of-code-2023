package org.example.day04;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScratchCard {
    public final int id;
    public final Set<Integer> winningNumbers;
    public final Set<Integer> numbers;

    public ScratchCard(String scratchCardString) {
        String[] sections = scratchCardString.split("[:|]");
        if (sections.length != 3) {
            throw new IllegalArgumentException("scratch card string has wrong format (\"" + scratchCardString + "\")");
        }
        String cardHeader = sections[0];
        String winningNumbers = sections[1];
        String numbers = sections[2];

        this.id = getUniqueIntsInString(cardHeader).stream().toList().getFirst();
        this.winningNumbers = getUniqueIntsInString(winningNumbers);
        this.numbers = getUniqueIntsInString(numbers);
    }

    public Set<Integer> matchingNumbers() {
        Set<Integer> matchingNumbers = new HashSet<>(this.winningNumbers);
        matchingNumbers.retainAll(this.numbers);
        return matchingNumbers;
    }

    public int pointValue() {
        int numberOfMatchingNumbers = matchingNumbers().size();
        if (numberOfMatchingNumbers == 0) {
            return 0;
        }
        return BigInteger.TWO.pow(numberOfMatchingNumbers - 1).intValue();
    }

    private Set<Integer> getUniqueIntsInString(String str) {
        Pattern regexInt = Pattern.compile("-?\\d+");
        Matcher matcher = regexInt.matcher(str);

        Set<Integer> ints = new HashSet<>();
        while (matcher.find()) {
            ints.add(Integer.parseInt(matcher.group()));
        }
        return ints;
    }
}
