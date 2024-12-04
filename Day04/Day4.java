package Day04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day4 {
    public static void main(String[] args) {
        // part one
        List<String> input = getInput("Day04/Day4Input.txt");
        char[][] crossword = parseInput(input);
        System.out.println(findCountOfPhrase("XMAS", crossword));
        // part two
        System.out.println(findCountOfMASX(crossword));
    }

    public static int findCountOfMASX(char[][] crossword) {
        int count = 0;
        List<Point> AOccurs = new ArrayList<>();
        // first find all appearances of first char (A)
        for (int i = 0; i < crossword.length; i++) {
            for (int j = 0; j < crossword[0].length; j++) {
                if (crossword[i][j] == 'A')
                    AOccurs.add(new Point(i, j));
            }
        }
        // for every A we want it to be in the middle of two diagonal MAS so 4 possible
        // options
        int[][] directions = new int[][] { { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } };
        char[] toCheck1 = new char[] { 'M', 'S', 'M', 'S' };
        char[] toCheck2 = new char[] { 'S', 'M', 'S', 'M' };
        char[] toCheck3 = new char[] { 'M', 'M', 'S', 'S' };
        char[] toCheck4 = new char[] { 'S', 'S', 'M', 'M' };
        char[][] toCheckArray = new char[][] { toCheck1, toCheck2, toCheck3, toCheck4 };
        for (Point p : AOccurs) {
            for (char[] toCheck : toCheckArray) {
                int index = 0;
                for (int[] direction : directions) {
                    int newX = p.x + direction[0];
                    int newY = p.y + direction[1];
                    // check bounds
                    if (!(newX < crossword.length && newX >= 0 && newY < crossword[0].length && newY >= 0))
                        continue;
                    if (crossword[newX][newY] != toCheck[index])
                        break;
                    index++;
                }
                if (index == 4)
                    count++;
            }
        }
        return count;
    }

    public static int findCountOfPhrase(String phrase, char[][] crossword) {
        int count = 0;
        List<Point> XOccurs = new ArrayList<>();
        // first find all appearances of first char (X)
        for (int i = 0; i < crossword.length; i++) {
            for (int j = 0; j < crossword[0].length; j++) {
                if (crossword[i][j] == phrase.charAt(0))
                    XOccurs.add(new Point(i, j));
            }
        }
        // for every X search in every direction for second char (M)
        int[][] directions = new int[][] { { 0, 1 }, { 1, 0 }, { 1, 1 }, { 0, -1 }, { -1, 0 }, { -1, -1 }, { -1, 1 },
                { 1, -1 } };
        // what even is never nesting!?
        for (Point p : XOccurs) {
            for (int[] direction : directions) {
                int newX = p.x + direction[0];
                int newY = p.y + direction[1];
                // check bounds
                if (!(newX < crossword.length && newX >= 0 && newY < crossword[0].length && newY >= 0))
                    continue;
                // if M is found continue in that direction for next chars (A)->(S)
                int index = 2;
                if (crossword[newX][newY] == phrase.charAt(1)) {
                    for (char c : phrase.substring(2).toCharArray()) {
                        newX += direction[0];
                        newY += direction[1];
                        if (!(newX < crossword.length && newX >= 0 && newY < crossword[0].length && newY >= 0))
                            continue;
                        if (crossword[newX][newY] != c)
                            continue;
                        index++;
                    }
                }
                if (index == phrase.length())
                    count++;
            }
        }
        return count;
    }

    public static char[][] parseInput(List<String> input) {
        char[][] crossword = new char[input.size()][input.get(0).length()];

        int i = 0;
        for (String line : input) {
            int j = 0;
            for (char c : line.toCharArray()) {
                crossword[i][j] = c;
                // System.out.print(c);
                j++;
            }
            // System.out.println();
            i++;
        }

        return crossword;
    }

    public static List<String> getInput(String file) {
        List<String> input = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(file))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                input.add(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return input;
    }
}
