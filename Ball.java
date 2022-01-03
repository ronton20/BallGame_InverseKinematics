import java.awt.*;
import java.awt.geom.*;

public class Ball {

    private double x;
    private double y;
    private double radius;
    private Color color;

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

    public void draw(Graphics2D g) {
        g.setColor(this.color);
        g.fill(ball);
    }
}