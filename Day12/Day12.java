package Day12;

import java.util.List;

import Tools.Tools;

public class Day12 {

    public static void main(String[] args) {
        List<String> input = Tools.getInput("Day12/Day12Input.txt");
        char[][] garden = Tools.inputTo2DCharArray(input);
        // part one
        Garden g1 = new Garden(true);
        System.out.println(g1.computeTotalPrice(garden));
        // part two
        Garden g2 = new Garden(false);
        System.out.println(g2.computeTotalPrice(garden));
    }

}
