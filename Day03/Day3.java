package Day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    public static void main(String[] args) {
        // part one
        List<String> input = getInput("Day03/Day3Input.txt");
        System.out.println(parseInput(input));
        // part two
        System.out.println(countEnabled(input));
    }

    public static int countEnabled(List<String> input) {
        int count = 0;
        boolean enabled = true;
        String line = String.join("", input);
        String regex = "(do\\(\\))|(don't\\(\\))|mul\\((\\d+),(\\d+)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                // Matched a do()
                enabled = true;
            } else if (matcher.group(2) != null) {
                // Matched a don't()
                enabled = false;
            } else if (matcher.group(3) != null && matcher.group(4) != null) {
                // Matched a mul(x, y)
                if (enabled) {
                    // Only process if mul is enabled
                    String group1 = matcher.group(3);
                    String group2 = matcher.group(4);
                    count += Integer.parseInt(group1) * Integer.parseInt(group2);
                }
            }
        }
        return count;
    }

    public static int parseInput(List<String> input) {
        int total = 0;
        for (String line : input) {
            String regex = "mul\\((\\d+),(\\d+)\\)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                // Extract the groups
                String group1 = matcher.group(1);
                String group2 = matcher.group(2);
                total += Integer.parseInt(group1) * Integer.parseInt(group2);
                AtomicInteger temp = new AtomicInteger(0);
                matcher.results()
                        .forEach(i -> temp.addAndGet(Integer.parseInt(i.group(1)) * Integer.parseInt(i.group(2))));
                total += temp.get();
            } else {
                System.out.println("No match found!");
            }
        }
        return total;
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
