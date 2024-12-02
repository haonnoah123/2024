package Day1;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Day1 {
    public static void main(String[] args) {
        partOne();
        partTwo();
    }

    public static void partTwo() {
        List<String> input = getInput("Day1/Day1Input.txt");
        int[][] ids = splitInput(input);
        System.out.println(getSimilarityScore(ids));
    }

    public static int getSimilarityScore(int[][] ids) {
        int similarity = 0;
        // loop through every element in ids[j][0]
        for(int j = 0; j < ids.length; j++) {
            int element = ids[j][0];
            // get count of that element in ids[i][1]
            int count = 0;
            for(int i = 0; i < ids.length; i++) {
                if(ids[i][1] == element) count++;
            }
            // similarity += element * count
            similarity += element * count;
        }
        return similarity;
    }

    public static void partOne() {
        List<String> input = getInput("Day1/Day1Input.txt");
        int[][] ids = splitInput(input);
        System.out.println(getDistances(ids));
    }

    public static int getDistances(int[][] ids) {
        // could also sort both lists and just find distance that way
        int totalDistance = 0;
        for (int j = 0; j < ids.length; j++) {
            int minVal1 = ids[0][0];
            int minVal2 = ids[0][1];
            int minIndex1 = 0;
            int minIndex2 = 0;
            for (int i = 0; i < ids.length; i++) {
                if (minVal1 > ids[i][0]) {
                    minVal1 = ids[i][0];
                    minIndex1 = i;
                }
                if (minVal2 > ids[i][1]) {
                    minVal2 = ids[i][1];
                    minIndex2 = i;
                }
            }
            totalDistance += Math.abs(minVal1 - minVal2);
            ids[minIndex1][0] = Integer.MAX_VALUE;
            ids[minIndex2][1] = Integer.MAX_VALUE;
        }
        return totalDistance;
    }

    public static int[][] splitInput(List<String> input) {
        int[][] ids = new int[input.size()][2];
        int index = 0;
        for (String line : input) {
            String[] temp = line.split("   ");
            ids[index][0] = Integer.parseInt(temp[0]);
            ids[index][1] = Integer.parseInt(temp[1]);
            index++;
        }
        return ids;
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