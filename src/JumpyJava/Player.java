package JumpyJava;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends GameObject implements KeyListener {
    boolean arrowMode = false;
    boolean upPressed, downPressed, spacePressed;
    double y;
    int x = 100;
    double velY = 0;
    double gravity = 0.1;
    double jumpSpeed = -4;
    private BufferedImage image;

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.x  = x;
        this.y = y;
    }

    public void update() {
        if (!arrowMode) {
            // Basic gravity logic
            velY += gravity;
            y += velY;

            // Jumping logic
            if (spacePressed) {
                velY = jumpSpeed;
            }
        }
        else {
            if (upPressed) {
                y += jumpSpeed;
            }
            if (downPressed) {
                y -= jumpSpeed;
            }
        }
    }
    public void draw(Graphics g) {
        imageHandler();

        if (image != null) {
            g.drawImage(image, x, (int) y, this.width, this.height, null);
        } else {
            g.setColor(Color.BLUE); // Change color so you can see it against a background
            g.fillRect(x, (int)y, width, height);
        }
    }

    public void imageHandler() {
        try {
            image = ImageIO.read(new File("res/player.png"));
        } catch(IOException e) {
            System.err.println("Error player loading image");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (arrowMode) {
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
                downPressed = true;
            }
        }
        else {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {spacePressed = true;}
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (arrowMode) {
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
                upPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN  || e.getKeyCode() == KeyEvent.VK_S) {
                downPressed = false;
            }
        }
        else {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {spacePressed = false;}
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public boolean playerOffscreen(int panelHeight) {
        // Check top and bottom boundaries
        boolean offTop    = (this.y + this.height < 0);
        boolean offBottom = (this.y > panelHeight + 100);

        return offTop || offBottom;
    }
}
