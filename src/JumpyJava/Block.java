package JumpyJava;

import javax.swing.*;
import java.awt.*;

public class Block extends GameObject {
    int speed = 5;
    double x,y;

    public Block(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED); // Change color so you can see it against a background
        g.fillRect((int) x, (int)y, width, height);
    }

    public void update() {
        x -= speed;
    }
}
