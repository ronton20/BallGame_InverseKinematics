import java.awt.*;
import java.awt.geom.*;

public class Ball {

    public static final double G = 0.2;
    public static final double FRICTION = 0.05;

    private double x;
    private double y;
    private double radius;
    private Color color;
    private Dimension dimension;

    double ballXVelocity = 2;
    double ballYVelocity = 0;
    double ballGravity = G;

    private Ellipse2D.Double ball;
    
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
        this.dimension = dim;
        double targetX = x + this.ballXVelocity;
        double targetY = y + ballYVelocity;
        if(targetY + radius >= dim.getHeight() && ballYVelocity > 0) {                      //Bottom Collision
            if(targetY - radius <= 1 && targetY - radius >= -1) { ballYVelocity = 0; return; }
            ballYVelocity *= -1;
            if(ballXVelocity < 0) ballXVelocity += FRICTION;
            if(ballXVelocity > 0) ballXVelocity -= FRICTION;
        }
        if(targetY - radius <= 0 && ballYVelocity < 0) ballYVelocity *= -1;                 //Top Collision
        if(targetX - radius <= 0 && ballXVelocity < 0) ballXVelocity *= -1;                 //Left Collision
        if(targetX + radius >= dim.getWidth() && ballXVelocity > 0) ballXVelocity *= -1;    //Right Collision

        ballYVelocity += ballGravity;

        setLocation(targetX, targetY);
    }

    public void collide(double x, double y, double v2) {
        double dirX = this.x - x;
        double dirY = this.y - y;
        double angle = Math.atan2(dirY, dirX);

        double v1 = Math.sqrt(Math.pow(ballXVelocity, 2) + Math.pow(ballYVelocity, 2));

        double u1 = (v1 + 2 * v2) / 3;

        double xVelocity = u1 * Math.cos(angle);
        double yVelocity = u1 * Math.sin(angle);

        if(this.y + radius >= dimension.getHeight() - 1 && yVelocity > 0) yVelocity = -1;

        this.ballXVelocity = xVelocity;
        this.ballYVelocity = yVelocity;

    }

    public void draw(Graphics2D g) {
        g.setColor(this.color);
        g.fill(ball);
    }
}