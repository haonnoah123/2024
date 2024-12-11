package Day11;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class Stone {

    private Map<String, Long> cache = new HashMap<>();

    public String hashFunc(Long stone, int times) {
        return stone + " " + times;
    }

    public long recurrITimes(Queue<Long> stones, int times) {
        long count = 0;
        while (!stones.isEmpty()) {
            Long tempStone = stones.poll();
            count += recurrBlink(tempStone, times);
        }
        return count;
    }

    public long recurrBlink(Long stone, int times) {
        String hash = hashFunc(stone, times);

        long toReturn = 0;
        if (times == 0)
            return 1;

        if (cache.containsKey(hash)) {
            return cache.get(hash);
        }

        if (stone == 0) {
            toReturn = recurrBlink(stone + 1, times - 1);
        } else if ((stone + "").length() % 2 == 0) {
            String tempStones = stone + "";
            long stone1 = Long.parseLong(tempStones.substring(0, tempStones.length() / 2));
            long stone2 = Long.parseLong(tempStones.substring(tempStones.length() / 2));
            toReturn = recurrBlink(stone1, times - 1) + recurrBlink(stone2, times - 1);
        } else {
            toReturn = recurrBlink(stone * 2024, times - 1);
        }
        cache.put(hash, toReturn);
        return toReturn;
    }

}
