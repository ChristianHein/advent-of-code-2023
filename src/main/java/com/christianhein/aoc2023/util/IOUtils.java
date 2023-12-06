package com.christianhein.aoc2023.util;

import com.christianhein.aoc2023.day01.Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtils {
    public static String[] linesFromResource(String resourceName) throws FileNotFoundException {
        InputStream fileInputStream = Main.class.getClassLoader().getResourceAsStream(resourceName);
        if (fileInputStream == null) {
            throw new FileNotFoundException();
        }
        return new BufferedReader(new InputStreamReader(fileInputStream))
                .lines()
                .toArray(String[]::new);
    }
}
