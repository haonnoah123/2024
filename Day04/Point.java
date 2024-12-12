package Day04;

public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Point) {
            Point temp = (Point) obj;
            return temp.x == this.x && temp.y == this.y;
        }
        return false;
    }
}
