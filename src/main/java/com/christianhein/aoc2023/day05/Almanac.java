package com.christianhein.aoc2023.day05;

import com.christianhein.aoc2023.util.StringUtils;

import java.util.*;

public class Almanac {
    public List<LongRange> seeds;
    public AlmanacMap seedToSoil;
    public AlmanacMap soilToFertilizer;
    public AlmanacMap fertilizerToWater;
    public AlmanacMap waterToLight;
    public AlmanacMap lightToTemperature;
    public AlmanacMap temperatureToHumidity;
    public AlmanacMap humidityToLocation;

    private Almanac() {
    }

    public static Almanac almanacFromInput(String[] input, SeedsParsingMode seedsParsingMode) {
        Almanac almanac = new Almanac();

        List<String[]> inputSections = StringUtils.splitStringArrayOnBlankStrings(input);

        almanac.seeds = parseSeeds(inputSections.get(0)[0], seedsParsingMode);
        almanac.seedToSoil = new AlmanacMap(inputSections.get(1));
        almanac.soilToFertilizer = new AlmanacMap(inputSections.get(2));
        almanac.fertilizerToWater = new AlmanacMap(inputSections.get(3));
        almanac.waterToLight = new AlmanacMap(inputSections.get(4));
        almanac.lightToTemperature = new AlmanacMap(inputSections.get(5));
        almanac.temperatureToHumidity = new AlmanacMap(inputSections.get(6));
        almanac.humidityToLocation = new AlmanacMap(inputSections.get(7));

        return almanac;
    }

    private static List<LongRange> parseSeeds(String input, SeedsParsingMode parsingMode) {
        List<LongRange> seeds = new ArrayList<>();

        List<Long> values = StringUtils.getPositiveLongsInString(input);
        switch (parsingMode) {
            case SeedsParsingMode.Literal:
                for (Long seed : values) {
                    seeds.add(new LongRange(seed, seed + 1));
                }
                break;
            case SeedsParsingMode.Ranges:
                assert (values.size() % 2 == 0);
                for (int i = 0; i < values.size() - 1; i += 2) {
                    long rangeStart = values.get(i);
                    long rangeLength = values.get(i + 1);
                    seeds.add(new LongRange(rangeStart, rangeStart + rangeLength));
                }
                break;
        }
        return seeds;
    }

    public long seedToLocation(long seed) {
        long soil = seedToSoil.getDestinationValue(seed);
        long fertilizer = soilToFertilizer.getDestinationValue(soil);
        long water = fertilizerToWater.getDestinationValue(fertilizer);
        long light = waterToLight.getDestinationValue(water);
        long temperature = lightToTemperature.getDestinationValue(light);
        long humidity = temperatureToHumidity.getDestinationValue(temperature);
        return humidityToLocation.getDestinationValue(humidity);
    }

    public enum SeedsParsingMode {
        Literal,
        Ranges,
    }
}
