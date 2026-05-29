package JumpyJava;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Random;

import static JumpyJava.GamePanel.blocks;

public class BlockGenerator {
    private static boolean cooldown = true;
    private static final Random rand = new Random();

    public static void cooldownHandler() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            cooldown = false;
        }, 0, 2, TimeUnit.SECONDS);
    }

    private static void blockTypes(boolean isOnBottom, int blockSize) {
        int xPos = 900;

        if (isOnBottom) {
            switch (blockSize) {
                case 1:
                    blocks.add(new Block(xPos, 475 - 250, 50, 300));
                case 2:
                    blocks.add(new Block(xPos, 510 - 250, 50, 300));
                case 3:
                    blocks.add(new Block(xPos, 575 - 250, 50, 300));
                case 4:
                    blocks.add(new Block(xPos, 610 - 250, 50, 300));
            }
        }
        if (!isOnBottom) {
            switch (blockSize) {
                case 1:
                    blocks.add(new Block(xPos, 0, 50, 200));
                case 2:
                    blocks.add(new Block(xPos, 0, 50, 100));
                case 3:
                    blocks.add(new Block(xPos, 0, 50, 300));
                case 4:
                    blocks.add(new Block(xPos, 0, 50, 350));
            }
        }
    }

    public static void blockGen() {
        if (!cooldown) {
            if (blocks.size() <= 5) {
                cooldown = true;
                //int xPos = rand.nextInt((positionMax - positionMin) + 1) + positionMin;


                int xPos = 900;
                boolean yPos = rand.nextBoolean();
                if (yPos) {
                    blockTypes(true, rand.nextInt(1,4));
                } else {
                    blockTypes(false, 2);
                }
            }
        }
    }
}
