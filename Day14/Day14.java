package Day14;

import java.util.ArrayList;
import java.util.List;

import Day12.Garden;
import Tools.Tools;

public class Day14 {

    public static void main(String[] args) {
        List<String> input = Tools.getInput("Day14/Day14Input.txt");
        List<Robot> robots = parseInput(input);
        int[][] bathroom = new int[103][101];
        // part one
        runRobots(robots, bathroom, 100, true);
        System.out.println(getSafetyFactor(bathroom));
        // part two
        bathroom = new int[103][101];
        robots = parseInput(input);
        runRobots(robots, bathroom, 10000, false);
    }

    /**
     * definitely a cleaner way of doing this but whatever
     * 
     * @param bathroom
     * @return
     */
    public static int getSafetyFactor(int[][] bathroom) {
        // Q1
        int Q1 = 0;
        for (int j = 0; j < bathroom.length / 2; j++) {
            for (int i = 0; i < bathroom[0].length / 2; i++) {
                Q1 += bathroom[j][i];
            }
        }

        int Q2 = 0;
        for (int j = 0; j < bathroom.length / 2; j++) {
            for (int i = bathroom[0].length / 2 + 1; i < bathroom[0].length; i++) {
                Q2 += bathroom[j][i];
            }
        }

        int Q3 = 0;
        for (int j = bathroom.length / 2 + 1; j < bathroom.length; j++) {
            for (int i = 0; i < bathroom[0].length / 2; i++) {
                Q3 += bathroom[j][i];
            }
        }

        int Q4 = 0;
        for (int j = bathroom.length / 2 + 1; j < bathroom.length; j++) {
            for (int i = bathroom[0].length / 2 + 1; i < bathroom[0].length; i++) {
                Q4 += bathroom[j][i];
            }
        }

        return Q1 * Q2 * Q3 * Q4;
    }

    /**
     * runs robots for given seconds
     * 
     * @param robots
     * @param bathroom
     * @param seconds
     * @param partOne
     */
    public static void runRobots(List<Robot> robots, int[][] bathroom, int seconds, boolean partOne) {
        initRobots(robots, bathroom);
        for (int i = 0; i < seconds; i++) {
            if (!partOne && checkForTree(bathroom)) {
                // Tools.print2DArray(bathroom);
                System.out.println(i);
                return;
            }
            for (Robot r : robots) {
                updateRobot(r, bathroom);
            }
        }
    }

    /**
     * checks for box surrounding tree
     * 
     * @param bathroom
     * @return
     */
    public static boolean checkForTree(int[][] bathroom) {
        for (int i = 0; i < bathroom.length; i++) {
            int consecutiveNums = 0;
            for (int j = 0; j < bathroom[0].length; j++) {
                if (consecutiveNums >= 20)
                    return true;
                if (bathroom[i][j] != 0)
                    consecutiveNums++;
                else
                    consecutiveNums = 0;
            }

        }
        return false;
    }

    public static void initRobots(List<Robot> robots, int[][] bathroom) {
        for (Robot r : robots) {
            bathroom[r.py][r.px]++;
        }
    }

    /**
     * negative mod function
     */
    public static int mod(int num, int mod) {
        return (((num % mod) + mod) % mod);
    }

    public static void updateRobot(Robot r, int[][] bathroom) {
        bathroom[r.py][r.px]--;
        r.px += r.vx;
        r.py += r.vy;
        r.px = mod(r.px, bathroom[0].length);
        r.py = mod(r.py, bathroom.length);
        bathroom[r.py][r.px]++;
    }

    public static List<Robot> parseInput(List<String> input) {
        List<Robot> robots = new ArrayList<>();
        for (String line : input) {
            robots.add(new Robot(line));
        }
        return robots;
    }

}
