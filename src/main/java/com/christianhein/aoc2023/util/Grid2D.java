package com.christianhein.aoc2023.util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Grid2D<T> {
    private ArrayList<T> flatGrid;

    private int rowCount;
    private int colCount;

    public Grid2D() {
        initialize(List.of());
    }

    public Grid2D(List<List<T>> data) {
        initialize(data);
    }

    public Grid2D(T[][] data) {
        initialize(Arrays.stream(data)
                .map(List::of)
                .toList());
    }

    public Grid2D(int height, int width, T defaultValue) {
        if (height < 0 || width < 0 || (height != width && (height == 0 || width == 0))) {
            throw new IllegalArgumentException("Invalid height and width values " + height + " and " + width);
        }
        this.flatGrid = new ArrayList<>(Collections.nCopies(height * width, defaultValue));
        this.rowCount = height;
        this.colCount = width;
    }

    public static Grid2D<Byte> from(byte[][] data) {
        return new Grid2D<>(Arrays.stream(data)
                .map(row ->
                        IntStream.range(0, row.length)
                                .mapToObj(i -> row[i])
                                .toList())
                .toList());
    }

    public static Grid2D<Short> from(short[][] data) {
        return new Grid2D<>(Arrays.stream(data)
                .map(row ->
                        IntStream.range(0, row.length)
                                .mapToObj(i -> row[i])
                                .toList())
                .toList());
    }

    public static Grid2D<Integer> from(int[][] data) {
        return new Grid2D<>(Arrays.stream(data)
                .map(row -> Arrays.stream(row).boxed().toList())
                .toList());
    }

    public static Grid2D<Long> from(long[][] data) {
        return new Grid2D<>(Arrays.stream(data)
                .map(row -> Arrays.stream(row).boxed().toList())
                .toList());
    }

    public static Grid2D<Float> from(float[][] data) {
        return new Grid2D<>(Arrays.stream(data)
                .map(row ->
                        IntStream.range(0, row.length)
                                .mapToObj(i -> row[i])
                                .toList())
                .toList());
    }

    public static Grid2D<Double> from(double[][] data) {
        return new Grid2D<>(Arrays.stream(data)
                .map(row -> Arrays.stream(row).boxed().toList())
                .toList());
    }

    public static Grid2D<Boolean> from(boolean[][] data) {
        return new Grid2D<>(Arrays.stream(data)
                .map(row ->
                        IntStream.range(0, row.length)
                                .mapToObj(i -> row[i])
                                .toList())
                .toList());
    }

    public static Grid2D<Character> from(char[][] data) {
        return new Grid2D<>(Arrays.stream(data)
                .map(row ->
                        IntStream.range(0, row.length)
                                .mapToObj(i -> row[i])
                                .toList())
                .toList());
    }

    public int getHeight() {
        return this.rowCount;
    }

    public int getWidth() {
        return this.colCount;
    }

    public boolean isEmpty() {
        return this.rowCount == 0 || this.colCount == 0;
    }

    public T get(int row, int col) {
        validateRowIndex(row);
        validateColIndex(col);
        return flatGrid.get(coordinatesToIndex(row, col));
    }

    public void set(int row, int col, T value) {
        validateRowIndex(row);
        validateColIndex(col);
        flatGrid.set(coordinatesToIndex(row, col), value);
    }

    public List<T> getRow(int rowIndex) {
        validateRowIndex(rowIndex);
        return IntStream.range(0, colCount)
                .map(colIndex -> coordinatesToIndex(rowIndex, colIndex))
                .mapToObj(i -> flatGrid.get(i))
                .toList();
    }

    public List<T> getCol(int colIndex) {
        validateColIndex(colIndex);
        return IntStream.range(0, rowCount)
                .map(rowIndex -> coordinatesToIndex(rowIndex, colIndex))
                .mapToObj(i -> flatGrid.get(i))
                .toList();
    }

    public void removeRow(int rowIndex) {
        validateRowIndex(rowIndex);
        IntStream.range(0, this.colCount)
                .forEach(i -> this.flatGrid.remove(coordinatesToIndex(rowIndex, 0)));
        --this.rowCount;
        if (this.rowCount == 0) {
            this.colCount = 0;
        }
    }

    public void removeCol(int colIndex) {
        validateColIndex(colIndex);
        this.flatGrid = IntStream.range(0, this.rowCount)
                .mapToObj(rowIndex -> {
                    var row = this.getRow(rowIndex);
                    return Stream.concat(IntStream.range(0, colIndex).mapToObj(row::get),
                            IntStream.range(colIndex + 1, row.size()).mapToObj(row::get));
                })
                .flatMap(Function.identity())
                .collect(Collectors.toCollection(ArrayList::new));
        --this.colCount;
        if (this.colCount == 0) {
            this.rowCount = 0;
        }
    }

    public void insertRow(int rowIndex, List<T> row) {
        if (rowIndex == 0 && this.rowCount == 0 && this.colCount == 0) {
            this.flatGrid = new ArrayList<>(row);
            this.rowCount = row.size();
            this.colCount = 1;
            return;
        }

        if (row.size() != this.colCount) {
            throw new IllegalArgumentException("Cannot insert row of length " + row.size());
        }

        if (rowIndex != this.rowCount) {
            validateRowIndex(rowIndex);
        }

        this.flatGrid.addAll(coordinatesToIndex(rowIndex, 0), row);
        ++this.rowCount;
    }

    public void insertCol(int colIndex, List<T> col) {
        if (colIndex == 0 && this.rowCount == 0 && this.colCount == 0) {
            this.flatGrid = new ArrayList<>(col);
            this.colCount = col.size();
            this.rowCount = 1;
            return;
        }

        if (col.size() != this.rowCount) {
            throw new IllegalArgumentException("Cannot insert column of length " + col.size());
        }

        if (colIndex != this.colCount) {
            validateColIndex(colIndex);
        }

        this.flatGrid = IntStream.range(0, this.rowCount)
                .mapToObj(rowIndex -> {
                    var row = this.getRow(rowIndex);
                    return Stream.concat(Stream.concat(IntStream.range(0, colIndex).mapToObj(row::get),
                                    Stream.of(col.get(rowIndex))),
                            IntStream.range(colIndex, row.size()).mapToObj(row::get));
                })
                .flatMap(Function.identity())
                .collect(Collectors.toCollection(ArrayList::new));
        ++this.colCount;
    }

    public List<List<T>> to2DList() {
        return IntStream.range(0, this.rowCount)
                .mapToObj(rowIndex ->
                        this.flatGrid.subList(coordinatesToIndex(rowIndex, 0), coordinatesToIndex(rowIndex + 1, 0)))
                .toList();
    }

    private void initialize(List<List<T>> data) {
        this.flatGrid = data.stream()
                .flatMap(List::stream)
                .collect(Collectors.toCollection(ArrayList::new));
        this.rowCount = data.size();
        this.colCount = data.isEmpty() ? 0 : data.getFirst().size();
        if (data.stream().anyMatch(row -> row.size() != this.colCount)) {
            throw new IllegalArgumentException("Cannot create 2D grid with rows of different sizes");
        }
    }

    private void validateRowIndex(int row) {
        if (row < 0 || this.rowCount <= row) {
            throw new IndexOutOfBoundsException("Invalid row index " + row);
        }
    }

    private void validateColIndex(int col) {
        if (col < 0 || this.colCount <= col) {
            throw new IndexOutOfBoundsException("Invalid column index " + col);
        }
    }

    private int coordinatesToIndex(int row, int col) {
        return row * this.colCount + col;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Grid2D<?> grid2D = (Grid2D<?>) other;
        return rowCount == grid2D.rowCount && colCount == grid2D.colCount
                && Objects.equals(flatGrid, grid2D.flatGrid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flatGrid, rowCount, colCount);
    }
}
