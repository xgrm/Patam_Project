package model.AnomalyDetection;

public class Point {
    float x,y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // get
    public float getX() {return x;}
    public float getY() {return y;}
// set
    public void setX(float x) {this.x = x;}
    public void setY(float y) {this.y = y;}
}
