package Day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Arrays;

public class Day2 {
    public static void main(String[] args) {
        // part one
        List<String> input = getInput("Day2/Day2Input.txt");
        List<int[]> parsedInput = parseInput(input);
        System.out.println(findSafe(parsedInput));
        // part two
        System.out.println(findSafe(parsedInput, 0));
    }

    public static int findSafe(List<int[]> input, int tolerance) {
        int countSafe = 0;
        for (int[] temp : input) {
            for (int i = 0; i < temp.length; i++) {
                List<Integer> attempt = Arrays.stream(temp).boxed().collect(Collectors.toList());
                attempt.remove(i);
                int[] levels = attempt.stream().mapToInt(Integer::intValue).toArray();
                List<int[]> tempList = new ArrayList<>();
                tempList.add(levels);
                boolean didPass = findSafe(tempList) == 1;
                // if it checks out add to count
                if (didPass) {
                    countSafe++;
                    break;
                }
            }
        }
        return countSafe;
    }

    public static int findSafe(List<int[]> input) {
        int countSafe = 0;
        for (int[] levels : input) {
            // -1 if dec 1 if inc
            int signDiff = levels[0] - levels[1];
            if (signDiff == 0)
                continue;
            int sign = (signDiff) / Math.abs(signDiff);
            // loop through and for every two check if same as sign and 1 <= abs <= 3
            boolean didPass = false;
            for (int level = 0; level < levels.length - 1; level++) {
                didPass = false;
                int difference = levels[level] - levels[level + 1];
                if ((sign * difference) != Math.abs(difference))
                    break;
                if (!(Math.abs(difference) >= 1 && Math.abs(difference) <= 3))
                    break;
                didPass = true;
            }
            // if it checks out add to count
            if (didPass)
                countSafe++;
        }
        return countSafe;
    }

    public static List<int[]> parseInput(List<String> input) {
        List<int[]> parsedInput = new ArrayList<>();
        for (String line : input) {
            parsedInput.add(Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray());
        }
        return parsedInput;
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
