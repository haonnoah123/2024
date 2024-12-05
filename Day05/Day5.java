package Day05;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

import Day04.Day4;

public class Day5 {

    public static void main(String[] args) {
        // part one
        List<String> input = Day4.getInput("Day05/Day5Input.txt");
        List<String> updates = getUpdates(input);
        List<String> orderingRules = getPageOrderingRules(input);
        System.out.println(findMidValid(updates, orderingRules));
        // part two
        System.out.println(findMidNotValid(updates, orderingRules));
    }

    public static int updateOrdering(String update, List<String> orderingRules) {
        int[] updateOrder = Arrays.stream(update.split(",")).mapToInt(Integer::parseInt).toArray();
        for (int i = 0; i < updateOrder.length; i++) {
            int first = updateOrder[i];
            for (int j = i + 1; j < updateOrder.length; j++) {
                int second = updateOrder[j];
                String toFind = first + "|" + second;
                if (!orderingRules.contains(toFind)) {
                    int temp = updateOrder[i];
                    updateOrder[i] = updateOrder[j];
                    updateOrder[j] = temp;
                }
            }
        }
        String updatedUpdate = Arrays.stream(updateOrder).mapToObj(String::valueOf).collect(Collectors.joining(","));
        if (!isValidUpdate(updatedUpdate, orderingRules))
            return updateOrdering(updatedUpdate, orderingRules);
        return updateOrder[updateOrder.length / 2];
    }

    public static int findMidNotValid(List<String> updates, List<String> orderingRules) {
        int total = 0;
        for (String update : updates) {
            if (!isValidUpdate(update, orderingRules))
                total += updateOrdering(update, orderingRules);
        }
        return total;
    }

    public static int findMidValid(List<String> updates, List<String> orderingRules) {
        int total = 0;
        for (String update : updates) {
            if (isValidUpdate(update, orderingRules))
                total += findMid(update);
        }
        return total;
    }

    public static int findMid(String update) {
        int[] updateOrder = Arrays.stream(update.split(",")).mapToInt(Integer::parseInt).toArray();
        return updateOrder[updateOrder.length / 2];
    }

    public static boolean isValidUpdate(String update, List<String> orderingRules) {
        int[] updateOrder = Arrays.stream(update.split(",")).mapToInt(Integer::parseInt).toArray();
        for (int i = 0; i < updateOrder.length; i++) {
            int first = updateOrder[i];
            for (int j = i + 1; j < updateOrder.length; j++) {
                int second = updateOrder[j];
                String toFind = first + "|" + second;
                if (!orderingRules.contains(toFind))
                    return false;
            }
        }
        return true;
    }

    public static List<String> getPageOrderingRules(List<String> input) {
        List<String> orderingRules = new ArrayList<>();
        int currIndex = 0;
        while (!input.get(currIndex).isBlank())
            orderingRules.add(input.get(currIndex++));
        return orderingRules;
    }

    public static List<String> getUpdates(List<String> input) {
        List<String> updates = new ArrayList<>();
        int updateIndex = 0;
        while (!input.get(updateIndex).isBlank()) {
            updateIndex++;
        }
        for (int i = updateIndex + 1; i < input.size(); i++) {
            updates.add(input.get(i));
        }

        return updates;
    }

}
