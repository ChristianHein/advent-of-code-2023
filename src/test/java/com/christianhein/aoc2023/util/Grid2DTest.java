package com.christianhein.aoc2023.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Grid2DTest {
    @Test
    void testDefaultConstructor() {
        Grid2D<Character> emptyGrid = new Grid2D<>();

        assertTrue(emptyGrid.isEmpty());
        assertEquals(0, emptyGrid.getHeight());
        assertEquals(0, emptyGrid.getWidth());
        assertThrows(IndexOutOfBoundsException.class, () -> emptyGrid.get(0, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> emptyGrid.getRow(0));
        assertThrows(IndexOutOfBoundsException.class, () -> emptyGrid.getCol(0));
        assertThrows(IndexOutOfBoundsException.class, () -> emptyGrid.removeRow(0));
        assertThrows(IndexOutOfBoundsException.class, () -> emptyGrid.removeCol(0));
        assertThrows(IndexOutOfBoundsException.class, () -> emptyGrid.set(0, 0, 'X'));
        assertArrayEquals(new Character[][]{}, emptyGrid.to2DList().toArray());
    }

    @Test
    void testPrimitiveConstructors() {
        Grid2D<Byte> byteGrid = Grid2D.from(new byte[][]{{10, 20}, {30, 40}});
        assertEquals((byte) 10, byteGrid.get(0, 0));
        byteGrid.set(0, 0, (byte) 50);
        assertEquals((byte) 50, byteGrid.get(0, 0));

        Grid2D<Short> shortGrid = Grid2D.from(new short[][]{{10, 20}, {30, 40}});
        assertEquals((short) 10, shortGrid.get(0, 0));
        shortGrid.set(0, 0, (short) 50);
        assertEquals((short) 50, shortGrid.get(0, 0));

        Grid2D<Integer> intGrid = Grid2D.from(new int[][]{{10, 20}, {30, 40}});
        assertEquals(10, intGrid.get(0, 0));
        intGrid.set(0, 0, 50);
        assertEquals(50, intGrid.get(0, 0));

        Grid2D<Long> longGrid = Grid2D.from(new long[][]{{10L, 20L}, {30L, 40L}});
        assertEquals(10L, longGrid.get(0, 0));
        longGrid.set(0, 0, 50L);
        assertEquals(50L, longGrid.get(0, 0));

        Grid2D<Float> floatGrid = Grid2D.from(new float[][]{{10.1f, 20.2f}, {30.3f, 40.4f}});
        assertEquals(10.1f, floatGrid.get(0, 0));
        floatGrid.set(0, 0, 50.5f);
        assertEquals(50.5f, floatGrid.get(0, 0));

        Grid2D<Double> doubleGrid = Grid2D.from(new double[][]{{10.1, 20.2}, {30.3, 40.4}});
        assertEquals(10.1, doubleGrid.get(0, 0));
        doubleGrid.set(0, 0, 50.5);
        assertEquals(50.5, doubleGrid.get(0, 0));

        Grid2D<Character> charGrid = Grid2D.from(new char[][]{{'a', 'b'}, {'c', 'd'}});
        assertEquals('a', charGrid.get(0, 0));
        charGrid.set(0, 0, 'A');
        assertEquals('A', charGrid.get(0, 0));

        Grid2D<Boolean> booleanGrid = Grid2D.from(new boolean[][]{{true, false}, {false, true}});
        assertEquals(Boolean.TRUE, booleanGrid.get(0, 0));
        booleanGrid.set(0, 0, false);
        assertEquals(Boolean.FALSE, booleanGrid.get(0, 0));
    }

    @Test
    void testFillConstructor() {
        Grid2D<Character> emptyGrid = new Grid2D<>(0, 0, 'X');
        assertTrue(emptyGrid.isEmpty());
        assertThrows(IndexOutOfBoundsException.class, () -> emptyGrid.get(0, 0));

        Grid2D<Character> oneByOneGrid = new Grid2D<>(1, 1, 'X');
        assertEquals(1, oneByOneGrid.getHeight());
        assertEquals(1, oneByOneGrid.getWidth());
        assertEquals('X', oneByOneGrid.get(0, 0));

        Grid2D<Character> twoByOneGrid = new Grid2D<>(2, 1, 'X');
        assertEquals(2, twoByOneGrid.getHeight());
        assertEquals(1, twoByOneGrid.getWidth());
        assertEquals('X', twoByOneGrid.get(0, 0));
        assertEquals('X', twoByOneGrid.get(1, 0));

        assertThrows(IllegalArgumentException.class, () -> new Grid2D<>(0, 1, 'X'));
    }

    @Test
    void testFillConstructorWithNull() {
        Grid2D<Character> twoByOneGrid = new Grid2D<>(1, 1, null);
        assertEquals(1, twoByOneGrid.getHeight());
        assertEquals(1, twoByOneGrid.getWidth());
        assertNull(twoByOneGrid.get(0, 0));
    }

    @Test
    void testInvalidGridConstruction() {
        Integer[][] array2DWithDifferentSizedRows = {
                {1, 2, 3},
                {1, 2},
        };
        assertThrows(IllegalArgumentException.class, () -> new Grid2D<>(array2DWithDifferentSizedRows));

        List<List<Integer>> list2DWithDifferentSizedRows = List.of(
                List.of(1, 2, 3),
                List.of(1, 2)
        );
        assertThrows(IllegalArgumentException.class, () -> new Grid2D<>(list2DWithDifferentSizedRows));
    }

    @Test
    void testEmptyGridFromEmptyArrayPropertiesAndExceptions() {
        Grid2D<Character> emptyGrid = new Grid2D<>(new Character[][]{});

        assertTrue(emptyGrid.isEmpty());
        assertEquals(0, emptyGrid.getHeight());
        assertEquals(0, emptyGrid.getWidth());
        assertThrows(IndexOutOfBoundsException.class, () -> emptyGrid.get(0, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> emptyGrid.getRow(0));
        assertThrows(IndexOutOfBoundsException.class, () -> emptyGrid.getCol(0));
        assertThrows(IndexOutOfBoundsException.class, () -> emptyGrid.removeRow(0));
        assertThrows(IndexOutOfBoundsException.class, () -> emptyGrid.removeCol(0));
        assertThrows(IndexOutOfBoundsException.class, () -> emptyGrid.set(0, 0, 'X'));
        assertArrayEquals(new Character[][]{}, emptyGrid.to2DList().toArray());
    }

    @Test
    void testEmptyGridFromEmptyArrayInsertionAndDeletion() {
        Integer[][] emptyGridArray = {};
        Grid2D<Integer> grid = new Grid2D<>(emptyGridArray);

        // Insertion

        grid.insertCol(0, List.of(42));
        assertFalse(grid.isEmpty());
        assertEquals(1, grid.getWidth());
        assertEquals(1, grid.getHeight());
        assertEquals(42, grid.get(0, 0));
        assertArrayEquals(new Integer[]{42}, grid.getRow(0).toArray());
        assertArrayEquals(new Integer[]{42}, grid.getCol(0).toArray());
        assertArrayEquals(new Integer[][]{{42}},
                grid.to2DList().stream()
                        .map(List::toArray)
                        .toArray());

        grid.insertRow(1, List.of(13));
        assertFalse(grid.isEmpty());
        assertEquals(1, grid.getWidth());
        assertEquals(2, grid.getHeight());
        assertEquals(42, grid.get(0, 0));
        assertEquals(13, grid.get(1, 0));
        assertArrayEquals(new Integer[]{42}, grid.getRow(0).toArray());
        assertArrayEquals(new Integer[]{13}, grid.getRow(1).toArray());
        assertArrayEquals(new Integer[]{42, 13}, grid.getCol(0).toArray());
        assertArrayEquals(new Integer[][]{{42}, {13}},
                grid.to2DList().stream()
                        .map(List::toArray)
                        .toArray());

        grid.insertCol(0, List.of(10, 20));
        assertFalse(grid.isEmpty());
        assertEquals(2, grid.getWidth());
        assertEquals(2, grid.getHeight());
        assertEquals(10, grid.get(0, 0));
        assertEquals(20, grid.get(1, 0));
        assertEquals(42, grid.get(0, 1));
        assertEquals(13, grid.get(1, 1));
        assertArrayEquals(new Integer[][]{{10, 42}, {20, 13}},
                grid.to2DList().stream()
                        .map(List::toArray)
                        .toArray());

        assertThrows(IllegalArgumentException.class, () -> grid.insertCol(0, List.of(0)));
        assertThrows(IllegalArgumentException.class, () -> grid.insertCol(0, List.of(0, 1, 2)));
        assertThrows(IllegalArgumentException.class, () -> grid.insertRow(0, List.of(0)));
        assertThrows(IllegalArgumentException.class, () -> grid.insertRow(0, List.of(0, 1, 2)));

        // Deletion

        grid.removeCol(0);
        assertFalse(grid.isEmpty());
        assertEquals(1, grid.getWidth());
        assertEquals(2, grid.getHeight());
        assertEquals(42, grid.get(0, 0));
        assertEquals(13, grid.get(1, 0));

        grid.removeRow(1);
        assertFalse(grid.isEmpty());
        assertEquals(1, grid.getWidth());
        assertEquals(1, grid.getHeight());
        assertEquals(42, grid.get(0, 0));

        grid.removeRow(0);
        assertTrue(grid.isEmpty());
        assertEquals(0, grid.getWidth());
        assertEquals(0, grid.getHeight());
        assertThrows(IndexOutOfBoundsException.class, () -> grid.get(0, 0));

        grid.insertRow(0, List.of(1024));
        grid.insertRow(0, List.of(2048));
        grid.removeCol(0);
        assertTrue(grid.isEmpty());
        assertEquals(0, grid.getWidth());
        assertEquals(0, grid.getHeight());
        assertThrows(IndexOutOfBoundsException.class, () -> grid.get(0, 0));
    }

    @Test
    void testGetterAndSetter() {
        Grid2D<Character> grid = new Grid2D<>(new Character[][]{
                {'a', 'b', 'c'},
                {'d', 'e', 'f'},
        });

        assertEquals('a', grid.get(0, 0));
        assertEquals('b', grid.get(0, 1));
        assertEquals('c', grid.get(0, 2));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.get(0, 3));
        assertEquals('d', grid.get(1, 0));
        assertEquals('e', grid.get(1, 1));
        assertEquals('f', grid.get(1, 2));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.get(1, 3));
        assertThrows(IndexOutOfBoundsException.class, () -> grid.get(2, 0));

        grid.set(0, 0, 'X');
        assertEquals('X', grid.get(0, 0));
        assertEquals(List.of('X', 'b', 'c'), grid.getRow(0));
        grid.set(0, 1, 'Y');
        assertEquals('Y', grid.get(0, 1));
        assertEquals(List.of('X', 'Y', 'c'), grid.getRow(0));
        grid.set(0, 2, 'Z');
        assertEquals('Z', grid.get(0, 2));
        assertEquals(List.of('X', 'Y', 'Z'), grid.getRow(0));
    }

    @Test
    void testInsertionOfRowOrColumnWithWrongSize() {
        Character[][] charGridArray = {
                {'a', 'b', 'c'},
                {'d', 'e', 'f'},
        };
        Grid2D<Character> grid = new Grid2D<>(charGridArray);
        assertEquals(3, grid.getWidth());
        assertEquals(2, grid.getHeight());

        assertThrows(IllegalArgumentException.class, () -> grid.insertRow(1, List.of('1', '2')));
        assertThrows(IllegalArgumentException.class, () -> grid.insertRow(1, List.of('1', '2', '3', '4')));
        assertThrows(IllegalArgumentException.class, () -> grid.insertCol(1, List.of('1')));
        assertThrows(IllegalArgumentException.class, () -> grid.insertCol(1, List.of('1', '2', '3')));
    }
}
