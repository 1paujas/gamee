package JumpyJava;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    static int lives = 5;
    static boolean liveCooldown = false;

    // Create an instance of your Player
    Player player;
    public static ArrayList<Block> blocks = new ArrayList<Block>();
    Thread gameThread;


    public GamePanel() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
        this.addKeyListener(this); // Listen for keys on this panel

        // Initialize player: x, y, width, height
        player = new Player(100, 300, 50, 50);


        // Start the game thread
        gameThread = new Thread(this);
        gameThread.start();
    }

    public static void liveCooldown() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            liveCooldown = false;
        }, 0, 2, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        BlockGenerator.cooldownHandler();
        liveCooldown();
        lives = 5;

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
            boolean intersects = block.intersects(player);
            if (intersects) {
                if (!liveCooldown) {
                    liveCooldown = true;
                    lives--;
                }
            }
        }

        if (player.playerOffscreen(this.getHeight())) {
            player.y = 300;
            if (!liveCooldown) {
                liveCooldown = true;
                lives--;
            }
        }

        BlockGenerator.blockGen();

        for (Block block : blocks) {
            block.update();
        }
        blocks.removeIf(block -> block.x < -60);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clears the screen
        draw(g);
    }



    private void draw(Graphics g) {
        player.draw(g);

        g.setColor(Color.BLACK);
        g.drawString(("Lives: "+String.valueOf(lives)), 20, 20);
        for (Block block : blocks) {
            block.draw(g);
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