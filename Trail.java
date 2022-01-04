import java.awt.*;

public class Trail {

    Segment head;

    private double x;
    private double y;
    private double width;
    private double basicLen;
    private double len;
    private int segments;

    public Trail(double x, double y, double width_, double len_, int segs_) {
        this.x = x;
        this.y = y;
        this.width = width_;
        this.basicLen = len_;
        this.len = len_;
        this.segments = segs_;

        head = new Segment(x, y, len / segments, 0);
        Segment temp = head;
        for(int i = 1; i <= segments; i++) {
            if(i != segments) {
                temp.child = new Segment(0, 0, len / segments, i);
                temp.child.follow(temp.a.getX(), temp.a.getY());
                temp.child.parent = temp;
            }
            temp.setSW((width / segments) * (segments + 2 - i));
            temp = temp.child;
        }
    }

    private void updateSegmentLength() {
        Segment temp = head;
        while(temp != null) {
            temp.setLength(len / segments);
            temp = temp.child;
        }
    }

    public void setLength(double len_) { this.len = this.basicLen * len_; updateSegmentLength(); }

    public void follow(double x, double y) {
        head.follow(x, y);
        Segment temp = head.child;

        while(temp != null) {
            temp.follow(temp.parent.a.getX(), temp.parent.a.getY());
            temp = temp.child;
        }
    }

    public void draw(Graphics2D g) {
        Segment temp = head;
        while(temp != null) {
            temp.draw(g);
            temp = temp.child;
        }
    }
}
