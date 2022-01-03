import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

public class BouncingBallPanel extends JPanel {

    Ball ball;
    Trail trail;

    Segment seg;

    MouseAdapter mouseAdapter = new MouseAdapter();
    
    public BouncingBallPanel() {
        ball = new Ball(300, 250, 25, Color.BLUE);
        seg = new Segment(300, 250, 100, 0);

        this.addMouseMotionListener(mouseAdapter);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setBackground(new Color(30, 30, 30));
        Graphics2D g2d = (Graphics2D) g;
        seg.draw(g2d);
        ball.draw(g2d);
    }

    public class MouseAdapter extends MouseMotionAdapter {

        public void mouseMoved(MouseEvent e) {
            ball.setLocation(e.getX(), e.getY());
            seg.follow(ball.getX(), ball.getY());
            repaint();
        }
    }
}
