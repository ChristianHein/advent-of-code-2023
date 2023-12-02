package org.example.day02;

import java.util.HashMap;
import java.util.Map;

class Pull {
    HashMap<BallColor, Integer> ballCounts = new HashMap<>();

    Pull(String pullString) {
        for (String ballColorAndAmount : pullString.split(", ")) {
            String[] split = ballColorAndAmount.split(" ", 2);
            int amount = Integer.parseInt(split[0]);
            BallColor color = BallColor.valueOf(split[1].toUpperCase());

            ballCounts.put(color, amount);
        }
    }

    Map<BallColor, Integer> getBallCounts() {
        return this.ballCounts;
    }

    public int getBallColorCount(BallColor color) {
        if (!ballCounts.containsKey(color)) {
            return 0;
        }

        return ballCounts.get(color);
    }
}
