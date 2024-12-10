package Day10;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import Day04.Day4;

public class Day10 {

    public static void main(String[] args) {
        // part one
        List<String> input = Day4.getInput("Day10/Day10Input.txt");
        int[][] map = parseInput(input);
        System.out.println(findZeros(map, true));
        // part two
        System.out.println(findZeros(map, false));
    }

    public static int findPath(int[][] map, HashSet<String> summits, int target, int i, int j) {
        if (!(i >= 0 && i < map.length && j >= 0 && j < map[0].length))
            return 0;
        if (map[i][j] == 9 && target == 9) {
            summits.add(i + " " + j);
            return 1;
        }
        if (map[i][j] != target)
            return 0;
        target++;
        return findPath(map, summits, target, i + 1, j) + findPath(map, summits, target, i, j + 1)
                + findPath(map, summits, target, i - 1, j) + findPath(map, summits, target, i, j - 1);
    }

    public static int findZeros(int[][] map, boolean partOne) {
        int count = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 0) {
                    HashSet<String> summits = new HashSet<>();
                    int temp = findPath(map, summits, 0, i, j);
                    count += (partOne ? summits.size() : temp);
                }
            }
        }
        return count;
    }

    public static void print2DArray(int[][] input) {
        for (int[] line : input) {
            for (int c : line) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static int[][] parseInput(List<String> input) {
        int[][] map = new int[input.size()][input.get(0).length()];
        int i = 0;
        for (String line : input) {
            map[i++] = Arrays.stream(line.split("")).mapToInt(Integer::parseInt).toArray();
        }
        return map;
    }

}
