package model.AnomalyDetection.utils;

public class Point {
    public float x;
    public float y;
    public Point(float x, float y) {
        this.x=x;
        this.y=y;
    }
    public float distance(final Point p) {
        final float DX = x - p.x;
        final float DY = y - p.y;

        return (float) Math.sqrt(Math.pow(DX,2)+Math.pow(DY,2));
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
