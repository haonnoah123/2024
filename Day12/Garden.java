package Day12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Garden {

    private boolean partOne;
    private HashSet<String> areaCache = new HashSet<>();
    private HashSet<String> perimeterCache = new HashSet<>();

    public Garden(boolean partOne) {
        this.partOne = partOne;
    }

    /**
     * hash function for keeping track of what nodes we have already visited
     * 
     * @param x
     * @param y
     * @return string hash "{x} {y}"
     */
    public String hashFunc(int x, int y) {
        return x + " " + y;
    }

    /**
     * given the garden computes price in part one (no discounts)
     * price = perimeter * area added of each section
     * 
     * @param garden
     * @return total price
     */
    public int computeTotalPrice(char[][] garden) {
        int total = 0;
        HashMap<Character, List<Integer>> areas = this.calcTotalArea(garden);
        HashMap<Character, List<Integer>> perimeters = this.calcTotalPerimeter(garden);
        for (char key : areas.keySet()) {
            List<Integer> subAreas = areas.get(key);
            List<Integer> subPerimeters = perimeters.get(key);
            for (int i = 0; i < subAreas.size(); i++) {
                total += subAreas.get(i) * subPerimeters.get(i);
            }
        }
        return total;
    }

    /**
     * calculates perimeter of each section depending on part
     * 
     * @param garden
     * @return hashmap containing the perimmeters of each section
     */
    public HashMap<Character, List<Integer>> calcTotalPerimeter(char[][] garden) {
        HashMap<Character, List<Integer>> perimeters = new HashMap<>();
        for (int i = 0; i < garden.length; i++) {
            for (int j = 0; j < garden.length; j++) {
                char key = garden[i][j];
                int perimeter = 0;
                if (this.partOne) {
                    perimeter = findPerimeter(garden, i, j, key);
                } else {
                    perimeter = findCorners(garden, i, j, key);
                }
                if (perimeter == 0)
                    continue;
                if (perimeters.containsKey(key)) {
                    perimeters.get(key).add(perimeter);
                } else {
                    List<Integer> temp = new ArrayList<>();
                    temp.add(perimeter);
                    perimeters.put(key, temp);
                }
            }
        }
        return perimeters;
    }

    /**
     * area is same for part one and two
     * 
     * @param garden
     * @return
     */
    public HashMap<Character, List<Integer>> calcTotalArea(char[][] garden) {
        HashMap<Character, List<Integer>> areas = new HashMap<>();
        for (int i = 0; i < garden.length; i++) {
            for (int j = 0; j < garden.length; j++) {
                char key = garden[i][j];
                int area = findArea(garden, i, j, key);
                if (area == 0)
                    continue;
                if (areas.containsKey(key)) {
                    areas.get(key).add(area);
                } else {
                    List<Integer> temp = new ArrayList<>();
                    temp.add(area);
                    areas.put(key, temp);
                }
            }
        }
        return areas;
    }

    /**
     * finds perimeter in part 2
     * 
     * @param garden
     * @param startX
     * @param startY
     * @param plot
     * @return number of corners of certain section or 0 if section already explored
     */
    public int findCorners(char[][] garden, int startX, int startY, char plot) {
        String hash = hashFunc(startX, startY);
        if (perimeterCache.contains(hash) || checkSquare(garden, startX, startY, plot))
            return 0;
        // directions of a + around startX, startY
        int[][] directions = new int[][] { { 0, -1, -1, 0 }, { 0, -1, 1, 0 }, { 0, 1, -1, 0 }, { 0, 1, 1, 0 } };
        int corners = 0;
        for (int i = 0; i < directions.length; i++) {
            int x1 = startX + directions[i][0];
            int y1 = startY + directions[i][1];
            int x2 = startX + directions[i][2];
            int y2 = startY + directions[i][3];
            // exterior corners || interior corners
            if ((checkSquare(garden, x1, y1, plot) && checkSquare(garden, x2, y2, plot))
                    || (checkInteriorCorner(garden, x1, y1, plot) && (checkInteriorCorner(garden, x2, y2, plot)
                            && checkSquare(garden, x1 + x2 - startX, y1 + y2 - startY, plot)))) {
                corners++;
            }
        }
        perimeterCache.add(hash);
        return corners + findCorners(garden, startX + 1, startY, plot)
                + findCorners(garden, startX - 1, startY, plot) + findCorners(garden, startX, startY + 1, plot)
                + findCorners(garden, startX, startY - 1, plot);
    }

    private boolean checkInteriorCorner(char[][] garden, int x, int y, char plot) {
        return !checkSquare(garden, x, y, plot);
    }

    private boolean checkSquare(char[][] garden, int x, int y, char plot) {
        return (x < 0 || x >= garden.length || y < 0
                || y >= garden[0].length || garden[x][y] != plot);
    }

    /**
     * computes perimeter for part one
     * 
     * @param garden
     * @param startX
     * @param startY
     * @param plot
     * @return perimeter of section if not already computed
     */
    public int findPerimeter(char[][] garden, int startX, int startY, char plot) {
        String hash = hashFunc(startX, startY);
        if (perimeterCache.contains(hash) || checkSquare(garden, startX, startY, plot))
            return 0;
        int[][] directions = new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
        int perimeter = 0;
        for (int i = 0; i < directions.length; i++) {
            int x = startX + directions[i][0];
            int y = startY + directions[i][1];
            if (checkSquare(garden, x, y, plot))
                perimeter++;
        }
        perimeterCache.add(hash);
        return perimeter + findPerimeter(garden, startX + 1, startY, plot)
                + findPerimeter(garden, startX - 1, startY, plot) + findPerimeter(garden, startX, startY + 1, plot)
                + findPerimeter(garden, startX, startY - 1, plot);
    }

    /**
     * DFS ish area finding
     * 
     * @param garden
     * @param startX
     * @param startY
     * @param plot
     * @return area of section if not already computed
     */
    public int findArea(char[][] garden, int startX, int startY, char plot) {
        String hash = hashFunc(startX, startY);
        if (areaCache.contains(hash) || checkSquare(garden, startX, startY, plot))
            return 0;

        areaCache.add(hash);
        return 1 + findArea(garden, startX + 1, startY, plot) + findArea(garden, startX - 1, startY, plot)
                + findArea(garden, startX, startY + 1, plot) + findArea(garden, startX, startY - 1, plot);
    }

}