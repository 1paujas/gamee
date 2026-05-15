package JumpyJava;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    // Create an instance of your Player
    Player player;
    ArrayList<Block> blocks = new ArrayList<Block>();
    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.WHITE);
        this.setFocusable(true); // REQUIRED to receive key presses
        this.addKeyListener(this); // Listen for keys on this panel

        // Initialize player: x, y, width, height
        player = new Player(100, 300, 50, 50);

        // Block
        for (int i = 0; i < 5; i++) {
            blocks.add(new Block(600, 400, 50, 50));
        }

        // Start the game thread
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (true) {
            update();
            repaint();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        player.update();
        for (Block block : blocks) {
            block.update();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clears the screen
        draw(g);
    }

    private void draw(Graphics g) {
        player.draw(g);
        for (Block block : blocks) {
            block.update();
        }
    }

    // --- KeyListener Bridge ---
    // This sends the panel's key events directly to the player object
    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}