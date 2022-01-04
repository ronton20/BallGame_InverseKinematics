import java.awt.*;
import java.awt.geom.*;

public class Segment {
    
    Point a;
    double len;
    double angle;
    Point b = new Point();

    int index;
    double sw = 0;

    Segment parent;
    Segment child;

    public Segment(double x, double y, double len_, int i) {
        a = new Point(x, y);
        this.len = len_;
        this.angle = 0;
        this.index = i;
        this.sw = 50;
        
        findB();

        parent = null;
        child = null;
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

    public void setSW(double num) { this.sw = num; }
    public void setLength(double len_) { this.len = len_; }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        // g.setStroke(new BasicStroke(sw));
        // g.drawLine((int)a.getX(), (int)a.getY(), (int)b.getX(), (int)b.getY());
        g.fill(new Ellipse2D.Double(b.getX() - sw / 2, b.getY() - sw/2, sw, sw));
    }
}
