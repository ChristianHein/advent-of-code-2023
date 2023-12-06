package com.christianhein.aoc2023.day06;

public record BoatRaceRecord(long time, long distance) {
    public long numberOfWaysToBeatRecord() {
        int count = 0;
        for (long holdTime = 0; holdTime <= this.time; holdTime++) {
            if (holdTimeBeatsRecord(holdTime)) {
                count++;
            }
        }
        return count;
    }

    public boolean holdTimeBeatsRecord(long holdTime) {
        long remainingTime = this.time - holdTime;
        long distance = holdTime * remainingTime;
        return distance > this.distance;
    }
}
