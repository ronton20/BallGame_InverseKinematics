import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class BouncingBallPanel extends JPanel {

    Ball ball;
    Trail trail;

    double trailLength = 150;

    double mouseVelocity = -1;

    double mouseOldX;
    double mouseOldY;

    Timer t;
    Listener lis = new Listener();

    MouseAdapter mouseAdapter = new MouseAdapter();
    
    public BouncingBallPanel() {
        ball = new Ball(50, 250, 25, Color.BLUE);
        trail = new Trail(ball.getX(), ball.getY(), ball.getRadius() / 2 * 3, trailLength, 50);

        this.addMouseMotionListener(mouseAdapter);
        t = new Timer(10, lis);
        t.start();
    }

    

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.setBackground(new Color(30, 30, 30));
        Graphics2D g2d = (Graphics2D) g;
        trail.draw(g2d);
        ball.draw(g2d);
    }

    public class Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ball.move(getSize());
            trail.follow(ball.getX(), ball.getY());
            repaint();
        }

    }

    public class MouseAdapter extends MouseMotionAdapter {

        public void mouseMoved(MouseEvent e) {

            if(mouseOldX == -1) {
                mouseOldX = e.getX();
                mouseOldX = e.getX();
            }
            else {
                double newX = e.getX();
                double newY = e.getY();
                
                double dist = Math.sqrt((Math.pow(newX - mouseOldX, 2) + Math.pow(newY - mouseOldY, 2)));
                if(dist > 0) mouseVelocity = dist;

                mouseOldX = newX;
                mouseOldY = newY;
            }

            if(ball.contains(e.getX(), e.getY())) ball.collide(e.getX(), e.getY(), mouseVelocity);
        }
    }
}
