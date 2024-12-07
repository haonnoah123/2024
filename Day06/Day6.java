package Day06;

import Day04.Day4;
import Day04.Point;

import java.util.Arrays;
import java.util.List;

public class Day6 {

    public static void main(String[] args) {
        // partOne();
        // part two
        List<String> input = Day4.getInput("Day06/Day6Input.txt");
        char[][] parsedInput = Day4.parseInput(input);
        Point start = findStart(parsedInput);
        // can optimize by only checking the points on the path from part one
        // since we are only adding one # it will only hit it if it is on part 1 path
        // also could optimize loop detection instead of SOE
        bruteForce(parsedInput, start);
    }

    public static void bruteForce(char[][] input, Point start) {
        int count = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length; j++) {
                if (i == start.y && j == start.x)
                    continue;
                char[][] copy = Arrays.stream(input)
                        .map(char[]::clone)
                        .toArray(char[][]::new);
                copy[i][j] = '#';
                Point copyStart = new Point(start.x, start.y);
                start.x = copyStart.x;
                start.y = copyStart.y;
                try {
                    move(copy, 0, -1, start, copyStart);
                } catch (ArrayIndexOutOfBoundsException e) {
                } catch (StackOverflowError e) {
                    // infinite loop
                    count++;
                }
            }
        }
        System.out.println(count);
    }

    public static void partOne() {
        List<String> input = Day4.getInput("Day06/Day6Input.txt");
        char[][] parsedInput = Day4.parseInput(input);
        Point start = findStart(parsedInput);
        // (0, -1) -> (1, 0) -> (0, 1) -> (-1, 0)
        try {
            move(parsedInput, 0, -1, start, null);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getLocalizedMessage());
        }
        System.out.println(countX(parsedInput));
    }

    public static int countX(char[][] input) {
        int count = 0;
        for (char[] line : input) {
            for (char c : line) {
                if (c == 'X')
                    count++;
            }
        }
        return count;
    }

    public static void print2DArray(char[][] input) {
        for (char[] line : input) {
            for (char c : line) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static void move(char[][] input, int dx, int dy, Point p, Point start) {
        int i = p.x;
        int j = p.y;
        while (input[i][j] != '#') {
            input[i][j] = 'X';
            j += dx;
            i += dy;
            // if(start != null && p.x == start.x && p.y == start.y) return;
        }
        int newdx = 0;
        int newdy = 0;
        if (dx == 0 && dy == -1) {
            newdx = 1;
        } else if (dx == 1 && dy == 0) {
            newdy = 1;
        } else if (dx == 0 && dy == 1) {
            newdx = -1;
        } else if (dx == -1 && dy == 0) {
            newdy = -1;
        }
        move(input, newdx, newdy, new Point(i - dy, j - dx), start);
    }

    public static Point findStart(char[][] input) {
        int i = 0;
        for (char[] line : input) {
            int j = 0;
            for (char c : line) {
                if (c == '^')
                    return new Point(i, j);
                j++;
            }
            i++;
        }
        return null;
    }

}
