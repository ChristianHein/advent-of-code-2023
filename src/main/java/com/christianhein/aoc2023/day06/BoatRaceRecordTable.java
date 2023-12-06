package com.christianhein.aoc2023.day06;

import com.christianhein.aoc2023.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BoatRaceRecordTable {
    public final List<BoatRaceRecord> records;

    public BoatRaceRecordTable(String times, String distances) {
        List<Long> timesInts = StringUtils.getPositiveLongsInString(times);
        List<Long> distancesInts = StringUtils.getPositiveLongsInString(distances);
        assert (timesInts.size() == distancesInts.size());

        List<BoatRaceRecord> records = new ArrayList<>();
        for (int i = 0; i < timesInts.size(); i++) {
            records.add(new BoatRaceRecord(timesInts.get(i), distancesInts.get(i)));
        }
        this.records = records;
    }
}
