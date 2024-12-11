from queue import Queue
import functools

def test_recurr(stones: Queue, times: int) -> int:
    count = 0
    while not stones.empty():
        temp_stone = stones.get()
        count += recurr_blink(temp_stone, times)
    return count

@functools.cache
def recurr_blink(stone: int, times: int) -> int:
    if times == 0:
        return 1

    if stone == 0:
        return recurr_blink(stone + 1, times - 1)
    elif len(str(stone)) % 2 == 0:
        temp_stone = str(stone)
        stone1 = int(temp_stone[:len(temp_stone) // 2])
        stone2 = int(temp_stone[len(temp_stone) // 2:])
        return recurr_blink(stone1, times - 1) + recurr_blink(stone2, times - 1)
    else:
        return recurr_blink(stone * 2024, times - 1)
    
if __name__=="__main__":
    f = open('Day11/Day11Input.txt', 'r')
    content = f.read()
    f.close()
    stones = Queue()
    int_list = [int(x) for x in content.split(" ")]
    for x in int_list:
     stones.put(x)
    result = test_recurr(stones, 75)
    print(result)
