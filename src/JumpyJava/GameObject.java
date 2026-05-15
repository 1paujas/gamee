package JumpyJava;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class GameObject {
    int x,y;
    int width,height;
    BufferedImage img;

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update(Graphics g) {

    }
}
