package Day14;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Robot {
    public int px;
    public int py;
    public int vx;
    public int vy;


    public Robot(int px, int py, int vx, int vy) {
        this.px = px;
        this.py = py;
        this.vx = vx;
        this.vy = vy;
    }

    public Robot(String input) {
        String regex = "p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        matcher.find();        
        this.px = Integer.parseInt(matcher.group(1));
        this.py = Integer.parseInt(matcher.group(2));
        this.vx = Integer.parseInt(matcher.group(3));
        this.vy = Integer.parseInt(matcher.group(4));

    }

}
