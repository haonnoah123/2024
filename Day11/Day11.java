package Day11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import Day04.Day4;

public class Day11 {

    public static void main(String[] args) {
        List<String> input = Day4.getInput("Day11/Day11Input.txt");
        Queue<Long> stones = getStones(input);
        System.out.println(blinkITimes(stones, 25));
        stones = getStones(input);
        System.out.println(testRecurr(stones, 75));
    }

    public static int testRecurr(Queue<Long> stones, int times) {
        int count = 0;
        while(!stones.isEmpty()) {
            Long tempStone = stones.poll();
            count += recurrBlink(tempStone, times);
        }
        return count;
    }

    public static int recurrBlink(Long stone, int times) {
        if (times == 0)
            return 1;
            
        if (stone == 0) {
            return recurrBlink(stone + 1, times - 1);
        } else if ((stone + "").length() % 2 == 0) {
            String tempStones = stone + "";
            long stone1 = Long.parseLong(tempStones.substring(0, tempStones.length() / 2));
            long stone2 = Long.parseLong(tempStones.substring(tempStones.length() / 2));
            return recurrBlink(stone1, times - 1) + recurrBlink(stone2, times - 1);
        } else {
            return recurrBlink(stone * 2024, times - 1);
        }
    }

    public static int blinkITimes(Queue<Long> stones, int times) {
        for (int i = 0; i < times; i++) {
            stones = blink(stones);
        }
        return stones.size();
    }

    public static Queue<Long> getStones(List<String> input) {
        List<Long> stones = Arrays.stream(input.get(0).split(" ")).map(Long::parseLong).toList();
        Queue<Long> temp = new LinkedList<>();
        temp.addAll(stones);
        return temp;
    }

    public static Queue<Long> blink(Queue<Long> stones) {
        Queue<Long> newStones = new LinkedList<>();
        while (!stones.isEmpty()) {
            long stone = stones.poll();
            if (stone == 0) {
                newStones.add((long) 1);
            } else if ((stone + "").length() % 2 == 0) {
                String tempStones = stone + "";
                long stone1 = Long.parseLong(tempStones.substring(0, tempStones.length() / 2));
                long stone2 = Long.parseLong(tempStones.substring(tempStones.length() / 2));
                newStones.add(stone1);
                newStones.add(stone2);
            } else {
                newStones.add(stone * 2024);
            }
        }
        return newStones;
    }

}
