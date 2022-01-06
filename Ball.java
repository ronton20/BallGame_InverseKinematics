import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public class Ball {

    public static final double G = 0.2;
    public static final double FRICTION = 0.05;

    private double x;
    private double y;
    private double radius;
    private Color color;
    private Dimension dimension;

    double totalVelocity;
    double ballXVelocity = 2;
    double ballYVelocity = 0;
    double ballGravity = G;

    int mouseCounter = 0;

    private Ellipse2D.Double ball;

    private boolean canCollide = true;
    
    public Ball(double x, double y, double radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;

        ball = new Ellipse2D.Double(this.x - this.radius, this.y - this.radius, this.radius * 2, this.radius * 2);
    }

    public double getX() { return this.x; }
    public double getY() { return this.y; }
    public double getRadius() { return this.radius; }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
        ball.x = this.x - this.radius;
        ball.y = this.y - this.radius;
    }

    public boolean contains(double x, double y) {
        return ball.contains(new Point2D.Double(x, y));
    }

    public void setGravity(double gravity_) {
        this.ballGravity = gravity_;
    }

    public void move(Dimension dim) {
        boolean rolling = false;
        if(this.y + radius >= dim.getHeight() - 1 && this.ballYVelocity <= 0.1 && this.ballYVelocity >= -0.1) {
            ballYVelocity = 0;
            rolling = true;
        }
        this.dimension = dim;
        double targetX = x + this.ballXVelocity;
        double targetY = y + ballYVelocity;
        if(targetY + radius >= dim.getHeight() && ballYVelocity > 0) {                      //Bottom Collision
            ballYVelocity *= -1;
            if(ballXVelocity < 0) ballXVelocity += FRICTION;
            if(ballXVelocity > 0) ballXVelocity -= FRICTION;
        }
        if(targetY - radius <= 0 && ballYVelocity < 0) ballYVelocity *= -1;                 //Top Collision
        if(targetX - radius <= 0 && ballXVelocity < 0) ballXVelocity *= -1;                 //Left Collision
        if(targetX + radius >= dim.getWidth() && ballXVelocity > 0) ballXVelocity *= -1;    //Right Collision

        if(!rolling) ballYVelocity += ballGravity;
        else if(ballXVelocity >= 0.1 || ballXVelocity <= -0.1){
            if(ballXVelocity < 0) ballXVelocity += FRICTION;
            if(ballXVelocity > 0) ballXVelocity -= FRICTION;
        } else ballXVelocity = 0;

        totalVelocity = Math.sqrt(Math.pow(ballXVelocity, 2) + Math.pow(ballYVelocity, 2));

        setLocation(targetX, targetY);
    }

    public boolean canCollide() { return canCollide; }
    public double getVelocity() {return totalVelocity; }

    public void collide(double x, double y, double v2) {
        if(!canCollide) return;
        canCollide = false;
        double dirX = this.x - x;
        double dirY = this.y - y;
        double angle = Math.atan2(dirY, dirX);

        double v2x = v2 * Math.cos(angle);
        double v2y = v2 * Math.sin(angle);

        double ballMass = 1;
        double mouseMass = 2;

        double u1x = (((ballMass * ballXVelocity + mouseMass * v2x) / mouseMass) + v2x - ballXVelocity) / (1 + ballMass / mouseMass);
        double u1y = (((ballMass * ballYVelocity + mouseMass * v2y) / mouseMass) + v2y - ballYVelocity) / (1 + ballMass / mouseMass);

        this.ballXVelocity = u1x;
        this.ballYVelocity = u1y;

        double u1 = Math.sqrt(Math.pow(u1x, 2) + Math.pow(u1y, 2));
        this.totalVelocity = u1;

        if(this.y + radius >= dimension.getHeight() - 1 && ballYVelocity > 0) ballYVelocity = -1;


        Timer t = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(mouseCounter == 6) {
                    canCollide = true;
                    mouseCounter = 0;
                    ((Timer)e.getSource()).stop();
                }
                mouseCounter++;
            }
        });
        t.start();

    }

    public void draw(Graphics2D g) {
        g.setColor(this.color);
        g.fill(ball);
    }
}
