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

    Random rand = new Random();
    int positionMin = 900;
    int positionMax = 3800;

    // Create an instance of your Player
    Player player;
    public ArrayList<Block> blocks = new ArrayList<Block>();
    Thread gameThread;
    boolean cooldown = true;

    public void cooldownHandler() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            cooldown = false;
        }, 0, 2, TimeUnit.SECONDS);
    }

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

    @Override
    public void run() {
        cooldownHandler();

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

    private void blockGen() {
        if (!cooldown) {
            if (blocks.size() <= 5) {
                cooldown = true;
                //int xPos = rand.nextInt((positionMax - positionMin) + 1) + positionMin;
                int xPos = 900;
                int size = rand.nextInt((100 - 50) + 50) + positionMin; // Temporary random size


                boolean yPos = rand.nextBoolean();
                if (yPos) {
                    blocks.add(new Block(xPos, 510 - 250, 50, 300)); // Lower Block
                } else {
                    blocks.add(new Block(xPos, 0, 50, 300)); // Higher Block
                }
            }
        }
    }

    private void update() {
        player.update();

        System.out.println(cooldown); //debug

        blockGen();

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