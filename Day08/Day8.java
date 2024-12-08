package Day08;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import Day04.Day4;
import Day04.Point;

public class Day8 {
    public static void main(String[] args) {
        // part one
        List<String> input = Day4.getInput("Day08/Day8Input.txt");
        char[][] city = Day4.parseInput(input);
        List<Point> overflow = findAntenae(city, true);
        System.out.println(countAntinodes(city, overflow, true));
        // part two
        city = Day4.parseInput(input);
        overflow = findAntenae(city, false);
        System.out.println(countAntinodes(city, overflow, false));
    }

    public static List<Point> findAntinodes2(char[][] city, int startX, int startY, char frequency) {
        // don't care about overflow for part 2
        int savedStartX = startX - 1;
        for (int i = startY; i < city.length; i++) {
            for (int j = startX; j < city[0].length; j++) {
                if (city[i][j] == frequency) {
                    int dx = j - savedStartX;
                    int dy = i - startY;
                    int newX = j + dx;
                    int newY = i + dy;
                    while (newY < city.length && newY >= 0 && newX < city[0].length && newX >= 0) {
                        if (city[newY][newX] == '.' || city[newY][newX] == '#') {
                            city[newY][newX] = '#';
                        }
                        newX += dx;
                        newY += dy;
                    }
                    newY = startY - dy;
                    newX = savedStartX - dx;
                    while (newY < city.length && newY >= 0 && newX < city[0].length && newX >= 0) {
                        if (city[newY][newX] == '.' || city[newY][newX] == '#') {
                            city[newY][newX] = '#';
                        }
                        newX -= dx;
                        newY -= dy;
                    }
                }
            }
            startX = 0;
        }
        return new ArrayList<Point>();
    }

    public static int countAntinodes(char[][] city, List<Point> overflow, boolean partOne) {
        // if part one we only want to count # and add overflow
        // if part two we only care about not . and don't care about overflow
        HashSet<String> temp = new HashSet<>();
        int count = 0;
        for (char[] street : city) {
            for (char building : street) {
                if (partOne && building == '#')
                    count++;
                else if (!partOne && building != '.')
                    count++;
            }
        }
        for (Point p : overflow) {
            temp.add(p.x + " " + p.y);
        }
        if (partOne)
            count += temp.size();
        return count;
    }

    public static List<Point> findAntinodes(char[][] city, int startX, int startY, char frequency) {
        // overflow represents the points where an antenae exists but so should an
        // antinode
        List<Point> overflow = new ArrayList<>();
        int savedStartX = startX - 1;
        for (int i = startY; i < city.length; i++) {
            for (int j = startX; j < city[0].length; j++) {
                if (city[i][j] == frequency) {
                    int dx = j - savedStartX;
                    int dy = i - startY;
                    if (i + dy < city.length && i + dy >= 0 && j + dx < city[0].length && j + dx >= 0) {
                        if (city[i + dy][j + dx] == '.' || city[i + dy][j + dx] == '#') {
                            city[i + dy][j + dx] = '#';
                        } else {
                            overflow.add(new Point(i + dy, j + dx));
                        }
                    }
                    if (startY - dy < city.length && startY - dy >= 0 && savedStartX - dx < city[0].length
                            && savedStartX - dx >= 0) {
                        if (city[startY - dy][savedStartX - dx] == '.' || city[startY - dy][savedStartX - dx] == '#') {
                            city[startY - dy][savedStartX - dx] = '#';
                        } else {
                            overflow.add(new Point(startY - dy, savedStartX - dx));
                        }
                    }
                }
            }
            startX = 0;
        }
        return overflow;
    }

    public static List<Point> findAntenae(char[][] city, boolean partOne) {
        // loop through everything and find where ~= . v #
        List<Point> overflow = new ArrayList<>();
        for (int i = 0; i < city.length; i++) {
            for (int j = 0; j < city[0].length; j++) {
                if (partOne && city[i][j] != '.' && city[i][j] != '#') {
                    overflow.addAll(findAntinodes(city, j + 1, i, city[i][j]));
                } else if (!partOne && city[i][j] != '.' && city[i][j] != '#') {
                    overflow.addAll(findAntinodes2(city, j + 1, i, city[i][j]));
                }
            }
        }
        return overflow;
    }
}
