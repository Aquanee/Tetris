import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics2D;

public class Block extends Rectangle{
    // drawing blocks

    public int x, y;
    public static final int size = 30;
    public Color c;

    public Block(Color c) {
        this.c = c;
    }

    public void draw(Graphics2D g2){
        int border = 2;
        g2.setColor(c);
        g2.fillRect(x + border, y + border, size - border*2, size - border*2);
    }
}
