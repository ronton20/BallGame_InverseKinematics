import java.awt.*;

public class Segment {
    
    Point a;
    double len;
    double angle;
    Point b = new Point();

    public Segment(double x, double y, double len_, double angle_) {
        a = new Point(x, y);
        this.len = len_;
        this.angle = angle_;
        
        findB();
    }

    private void findB() {
        double dx = len * Math.cos(Math.toRadians(this.angle));
        double dy = len * Math.sin(Math.toRadians(this.angle));
        b.set(a.getX() + dx, a.getY() + dy);
    }

    public void follow(double targetX, double targetY) {
        double dirX = targetX - a.getX();
        double dirY = targetY - a.getY();
        this.angle = Math.toDegrees(Math.atan2(dirY, dirX));

        double dx = this.len * Math.cos(Math.toRadians(this.angle));
        double dy = this.len * Math.sin(Math.toRadians(this.angle));

        this.a.set(targetX - dx, targetY - dy);

        findB();
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(4));
        g.drawLine((int)a.getX(), (int)a.getY(), (int)b.getX(), (int)b.getY());
    }
}
