import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        
        JFrame window = new JFrame("Ball Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(600, 500);
        window.setResizable(false);
        window.setLocationRelativeTo(null);

        BouncingBallPanel panel = new BouncingBallPanel();
        window.add(panel, BorderLayout.CENTER);

        window.setVisible(true);
    }
}
