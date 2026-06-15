package JumpyJava;

import javax.swing.*;
import java.awt.*;

public class PregamePanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Font largeFont = new Font("Arial", Font.BOLD, 48);
        g.setFont(largeFont);
        g.setColor(Color.BLACK);
        g.drawString("Jumpy Java", 250, 200);
    }

    public PregamePanel(JFrame frame) {

        this.setLayout(null);

        JButton button = new JButton("Start");
        button.setBounds(330, 250, 120, 40);
        button.addActionListener(e -> {
            GamePanel gp =  new GamePanel();
            frame.add(gp);
            frame.setVisible(true);
            frame.remove(this);
        });

        this.add(button);
    }
}
