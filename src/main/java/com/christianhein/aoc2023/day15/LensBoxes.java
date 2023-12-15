package com.christianhein.aoc2023.day15;

import com.christianhein.aoc2023.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class LensBoxes {
    // Every index [0..256) in hashMap corresponds to a box. A box is
    // represented by an ordered list of lenses (label and focal length pairs)
    // whose labels all hash to that box index.
    private final List<List<Pair<String, Integer>>> hashMap;

    public LensBoxes() {
        this.hashMap = new ArrayList<>(256);
        for (int i = 0; i < 256; i++) {
            this.hashMap.add(new ArrayList<>());
        }
    }

    public void executeInstruction(String instruction) {
        if (instruction.contains("=")) {
            String label = instruction.split("=", 2)[0];
            int focalLength = Integer.parseInt(instruction.split("=", 2)[1]);
            if (focalLength < 0 || focalLength > 9) {
                throw new IllegalArgumentException("Focal length of lens must be between 0 and 9");
            }
            addLens(label, focalLength);
        } else if (instruction.contains("-")) {
            String label = instruction.substring(0, instruction.length() - 1);
            removeLens(label);
        } else {
            throw new IllegalArgumentException("Unrecognized instruction format in input \"" + instruction + "\"");
        }
    }

    private void addLens(String label, int focalLength) {
        int labelHash = HolidayHash.hash(label);
        List<Pair<String, Integer>> box = this.hashMap.get(labelHash);

        // Search lens in box and replace focal length if present
        for (int lensIndex = 0; lensIndex < box.size(); lensIndex++) {
            var lens = box.get(lensIndex);
            if (lens.left().equals(label)) {
                box.set(lensIndex, new Pair<>(label, focalLength));
                return;
            }
        }

        // Lens not found; add it
        box.add(new Pair<>(label, focalLength));
    }

    private void removeLens(String label) {
        int labelHash = HolidayHash.hash(label);
        List<Pair<String, Integer>> box = this.hashMap.get(labelHash);

        // Search lens in box and remove if present
        for (int lensIndex = 0; lensIndex < box.size(); lensIndex++) {
            var lens = box.get(lensIndex);
            if (lens.left().equals(label)) {
                box.remove(lensIndex);
                return;
            }
        }
    }

    public int getTotalFocusingPower() {
        return IntStream.range(0, 256)
                .map(this::boxFocusingPower)
                .sum();
    }

    private int boxFocusingPower(int boxIndex) {
        int totalPower = 0;
        var box = hashMap.get(boxIndex);
        for (int lensIndex = 0; lensIndex < box.size(); lensIndex++) {
            int lensFocalLength = box.get(lensIndex).right();

            int lensPower = boxIndex + 1;
            lensPower *= lensIndex + 1;
            lensPower *= lensFocalLength;

            totalPower += lensPower;
        }
        return totalPower;
    }
}
