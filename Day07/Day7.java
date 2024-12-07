package Day07;

import Day04.Day4;

import java.util.Arrays;
import java.util.List;

public class Day7 {

    public static void main(String[] args) {
        List<String> input = Day4.getInput("Day07/Day7Input.txt");
        // part one
        System.out.println(parseInput(input, true));
        // part two
        System.out.println(parseInput(input, false));
    }

    public static boolean evaluatePart2(long testVal, long[] vals, long currVal) {
        if (currVal == testVal && vals.length == 0)
            return true;
        if (vals.length == 0)
            return false;
        long[] newVals = Arrays.copyOfRange(vals, 1, vals.length);
        long tempVal1 = currVal + vals[0];
        long tempVal2 = currVal * vals[0];
        long tempVal3 = Long.parseLong("" + currVal + vals[0]);
        return evaluatePart2(testVal, newVals, tempVal1) || evaluatePart2(testVal, newVals, tempVal2)
                || evaluatePart2(testVal, newVals, tempVal3);
    }

    public static boolean evaluate(long testVal, long[] vals, long currVal) {
        if (currVal == testVal && vals.length == 0)
            return true;
        if (vals.length == 0)
            return false;
        long[] newVals = Arrays.copyOfRange(vals, 1, vals.length);
        long tempVal1 = currVal + vals[0];
        long tempVal2 = currVal * vals[0];
        return evaluate(testVal, newVals, tempVal2) || evaluate(testVal, newVals, tempVal1);
    }

    public static long parseInput(List<String> input, boolean partOne) {
        long count = 0;
        for (String line : input) {
            String[] first = line.split(": ");
            long goal = Long.parseLong(first[0]);
            long[] vals = Arrays.stream(first[1].split(" ")).mapToLong(Long::parseLong).toArray();
            if (partOne && evaluate(goal, vals, 0)) {
                count += goal;
            } else if (evaluatePart2(goal, vals, 0)) {
                count += goal;
            }
        }
        return count;
    }

}
