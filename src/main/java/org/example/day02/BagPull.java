package org.example.day02;

import java.util.HashMap;
import java.util.Map;

class BagPull {
    HashMap<CubeColor, Integer> cubeCounts = new HashMap<>();

    BagPull(String pullString) {
        for (String cubeColorAndAmount : pullString.split(", ")) {
            String[] split = cubeColorAndAmount.split(" ", 2);
            int amount = Integer.parseInt(split[0]);
            CubeColor color = CubeColor.valueOf(split[1].toUpperCase());

            cubeCounts.put(color, amount);
        }
    }

    Map<CubeColor, Integer> getCubeCounts() {
        return this.cubeCounts;
    }

    public int getCubeColorCount(CubeColor color) {
        if (!cubeCounts.containsKey(color)) {
            return 0;
        }

        return cubeCounts.get(color);
    }
}
