package JumpyJava;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    static int lives = 3;
    static int score = 0;
    static int highScore = 0;
    static boolean liveCooldown = false;
    static boolean alive = true;

    // Create an instance of your Player
    Player player;
    public static ArrayList<Block> blocks = new ArrayList<Block>();
    Thread gameThread;
    private JButton tryAgainButton;


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

        this.setLayout(null);

        tryAgainButton = new JButton("Try Again");
        tryAgainButton.setBounds(330, 250, 120, 40);
        tryAgainButton.setVisible(false); // Hidden at the start of the game

        tryAgainButton.addActionListener(e -> {
            // When clicked, hide the button and restart the game
            tryAgainButton.setVisible(false);
            alive = true;
            resetGame();

            this.repaint(); // Redraw to clear the "Game Over" text
        });

        this.add(tryAgainButton);

    }

    public static void liveCooldown() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            liveCooldown = false;
        }, 0, 2, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        if (alive) {
            BlockGenerator.cooldownHandler();
            liveCooldown();
            lives = 3;

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
    }



    private void update() {
        if (alive) {
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

            if (lives <= 0) {
                alive = false;
                tryAgainButton.setVisible(true);
            }

            if (score > highScore) {
                highScore = score;
            }

            BlockGenerator.blockGen();

            for (Block block : blocks) {
                block.update();
            }
            blocks.removeIf(block -> block.x < -60);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clears the screen

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        draw(g);
    }




    private void draw(Graphics g) {
        if (alive) {
            player.draw(g);

            g.setColor(Color.BLACK);
            g.drawString(("Lives: " + String.valueOf(lives)), 20, 20);
            g.drawString("Score: " + String.valueOf(score) + "   High Score: " + highScore, 20, 40);
            for (Block block : blocks) {
                block.draw(g);
            }
        } else {
            Font largeFont = new Font("Arial", Font.BOLD, 48);
            Font smallFont = new Font("Arial", Font.BOLD, 12);
            g.setFont(smallFont);
            g.setColor(Color.BLACK);
            g.drawString("Score: " + score, 365, 220);
            g.drawString("High Score: " + highScore, 355, 240);
            g.setFont(largeFont);
            g.setColor(Color.RED);
            g.drawString("Game Over!", 250, 200);
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

    public void resetGame() {
        blocks.clear();
        player.y = 300;
        player.velY = 0;
        lives = 3;
        score = 0;
        liveCooldown = false;
        alive = true;
    }
}