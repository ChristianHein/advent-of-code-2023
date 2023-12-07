package com.christianhein.aoc2023.day07;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HandTest {
    @Test
    void testHandType() {
        Assertions.assertEquals(Hand.Type.HighCard, new Hand("A5432").getType());
        Assertions.assertEquals(Hand.Type.OnePair, new Hand("AA432").getType());
        Assertions.assertEquals(Hand.Type.TwoPair, new Hand("KKAA2").getType());
        Assertions.assertEquals(Hand.Type.ThreeOfAKind, new Hand("AAA32").getType());
        Assertions.assertEquals(Hand.Type.FullHouse, new Hand("AAAKK").getType());
        Assertions.assertEquals(Hand.Type.FourOfAKind, new Hand("AAAAK").getType());
        Assertions.assertEquals(Hand.Type.FiveOfAKind, new Hand("AAAAA").getType());
    }

    @Test
    void testHandOrder_DifferentTypes() {
        Assertions.assertTrue(new Hand("AKQJT").compareTo(new Hand("TT345")) < 0);
        Assertions.assertTrue(new Hand("TT345").compareTo(new Hand("77745")) < 0);
        Assertions.assertTrue(new Hand("77745").compareTo(new Hand("55666")) < 0);
        Assertions.assertTrue(new Hand("55666").compareTo(new Hand("44445")) < 0);
        Assertions.assertTrue(new Hand("44445").compareTo(new Hand("22222")) < 0);
    }

    @Test
    void testHandOrder_SameTypes() {
        // Cases where ordering is same as in poker
        Assertions.assertTrue(new Hand("23456").compareTo(new Hand("23457")) < 0);
        Assertions.assertTrue(new Hand("23466").compareTo(new Hand("23477")) < 0);
        Assertions.assertTrue(new Hand("23366").compareTo(new Hand("23377")) < 0);
        Assertions.assertTrue(new Hand("33366").compareTo(new Hand("33377")) < 0);
        Assertions.assertTrue(new Hand("33336").compareTo(new Hand("33337")) < 0);
        Assertions.assertTrue(new Hand("33333").compareTo(new Hand("44444")) < 0);

        // Cases where ordering differs from regular poker
        Assertions.assertTrue(new Hand("2345A").compareTo(new Hand("34567")) < 0);
        Assertions.assertTrue(new Hand("234AA").compareTo(new Hand("34577")) < 0);
        Assertions.assertTrue(new Hand("23AAA").compareTo(new Hand("34777")) < 0);
        Assertions.assertTrue(new Hand("22AAA").compareTo(new Hand("33777")) < 0);
        Assertions.assertTrue(new Hand("2AAAA").compareTo(new Hand("37777")) < 0);
    }
}