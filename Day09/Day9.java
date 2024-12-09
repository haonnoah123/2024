package Day09;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Day04.Day4;

public class Day9 {

    public static void main(String[] args) {
        // part one
        List<String> input = Day4.getInput("Day09/Day9Input.txt");
        List<Integer> storage = parseInput(input);
        storage = badPlacement(storage);
        System.out.println(checkSum(storage));
        // part two
        storage = parseInput(input);
        storage = firstFit(storage);
        // printStorage(storage);
        System.out.println(checkSum(storage));
    }

    public static List<Integer> firstFit(List<Integer> storage) {
        HashMap<Integer, Integer> counts = getCountsOfInts(storage);
        for (int i = storage.size() - 1; i >= 0; i--) {
            int curr = storage.get(i);
            if (curr == Integer.MAX_VALUE)
                continue;
            int count = counts.get(curr);
            int gapCount = 0;
            for (int j = 0; j < storage.size(); j++) {
                if (storage.get(j) == Integer.MAX_VALUE) {
                    gapCount++;
                } else {
                    if (gapCount >= count) {
                        int startIndex = j - gapCount;
                        if (startIndex >= i)
                            break;
                        for (int k = startIndex; k < startIndex + count; k++) {
                            storage.set(k, curr);
                        }
                        for (int k = i; k > i - count; k--) {
                            storage.set(k, Integer.MAX_VALUE);
                        }
                        break;
                    }
                    gapCount = 0;
                }
            }
        }
        return storage;
    }

    public static HashMap<Integer, Integer> getCountsOfInts(List<Integer> storage) {
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (int i = 0; i < storage.size(); i++) {
            if (counts.containsKey(storage.get(i))) {
                counts.put(storage.get(i), counts.get(storage.get(i)) + 1);
            } else {
                counts.put(storage.get(i), 1);
            }
        }
        return counts;
    }

    public static long checkSum(List<Integer> storage) {
        long sum = 0;
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i) == Integer.MAX_VALUE)
                continue;
            sum += (i * storage.get(i));
        }
        return sum;
    }

    public static List<Integer> badPlacement(List<Integer> storage) {
        int r = storage.size() - 1;
        for (int i = 0; i < storage.size() && i < r; i++) {
            while (storage.get(r) == Integer.MAX_VALUE)
                r--;
            if (storage.get(i) == Integer.MAX_VALUE) {
                int temp = storage.get(i);
                storage.set(i, storage.get(r));
                storage.set(r, temp);
            }
        }
        return storage;
    }

    public static void printStorage(List<Integer> storage) {
        for (int num : storage) {
            if (num == Integer.MAX_VALUE)
                System.out.print('.');
            else
                System.out.print(num);
        }
        System.out.println();
    }

    public static List<Integer> parseInput(List<String> input) {
        List<Integer> storageLayout = new ArrayList<>();
        int currentFile = 0;
        boolean freeSpace = false;
        for (char c : input.get(0).toCharArray()) {
            int disk = Character.getNumericValue(c);
            for (int i = 0; i < disk; i++) {
                if (!freeSpace)
                    storageLayout.add(currentFile);
                if (freeSpace)
                    storageLayout.add(Integer.MAX_VALUE);
            }
            currentFile += (freeSpace ? 1 : 0);
            freeSpace = !freeSpace;
        }

        return storageLayout;
    }

}
