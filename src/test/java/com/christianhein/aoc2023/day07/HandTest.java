package com.christianhein.aoc2023.day07;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HandTest {
    @Test
    void testHandType() {
        Assertions.assertEquals(Hand.HandType.HighCard, new Hand("A5432", false).getType());
        Assertions.assertEquals(Hand.HandType.OnePair, new Hand("AA432", false).getType());
        Assertions.assertEquals(Hand.HandType.TwoPair, new Hand("KKAA2", false).getType());
        Assertions.assertEquals(Hand.HandType.ThreeOfAKind, new Hand("AAA32", false).getType());
        Assertions.assertEquals(Hand.HandType.FullHouse, new Hand("AAAKK", false).getType());
        Assertions.assertEquals(Hand.HandType.FourOfAKind, new Hand("AAAAK", false).getType());
        Assertions.assertEquals(Hand.HandType.FiveOfAKind, new Hand("AAAAA", false).getType());
    }

    @Test
    void testHandOrder_DifferentTypes() {
        Assertions.assertTrue(new Hand("AKQJT", false).compareTo(new Hand("TT345", false)) < 0);
        Assertions.assertTrue(new Hand("TT345", false).compareTo(new Hand("77745", false)) < 0);
        Assertions.assertTrue(new Hand("77745", false).compareTo(new Hand("55666", false)) < 0);
        Assertions.assertTrue(new Hand("55666", false).compareTo(new Hand("44445", false)) < 0);
        Assertions.assertTrue(new Hand("44445", false).compareTo(new Hand("22222", false)) < 0);
    }

    @Test
    void testHandOrder_SameTypes() {
        // Cases where ordering is same as in poker
        Assertions.assertTrue(new Hand("23456", false).compareTo(new Hand("23457", false)) < 0);
        Assertions.assertTrue(new Hand("23466", false).compareTo(new Hand("23477", false)) < 0);
        Assertions.assertTrue(new Hand("23366", false).compareTo(new Hand("23377", false)) < 0);
        Assertions.assertTrue(new Hand("33366", false).compareTo(new Hand("33377", false)) < 0);
        Assertions.assertTrue(new Hand("33336", false).compareTo(new Hand("33337", false)) < 0);
        Assertions.assertTrue(new Hand("33333", false).compareTo(new Hand("44444", false)) < 0);

        // Cases where ordering differs from regular poker
        Assertions.assertTrue(new Hand("2345A", false).compareTo(new Hand("34567", false)) < 0);
        Assertions.assertTrue(new Hand("234AA", false).compareTo(new Hand("34577", false)) < 0);
        Assertions.assertTrue(new Hand("23AAA", false).compareTo(new Hand("34777", false)) < 0);
        Assertions.assertTrue(new Hand("22AAA", false).compareTo(new Hand("33777", false)) < 0);
        Assertions.assertTrue(new Hand("2AAAA", false).compareTo(new Hand("37777", false)) < 0);
    }
}
