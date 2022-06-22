package model.AnomalyDetection.utils;


public class Circle {
    public Point center;
    public float radius;

    public Circle(final Point center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    public Circle(float x, float y, float radius) {
        center = new Point(x, y);
        this.radius = radius;
    }

    public Circle(final Point p1, final Point p2) {
        center = new Point((float) ((p1.x + p2.x) * 0.5), (float) ((p1.y + p2.y) * 0.5));
        radius = center.distance(p1);
    }

    public Circle(final Point p1, final Point p2, final Point p3) {
        final float P2_MINUS_P1_Y = p2.y - p1.y;
        final float P3_MINUS_P2_Y = p3.y - p2.y;

        if (P2_MINUS_P1_Y == 0.0 || P3_MINUS_P2_Y == 0.0) {
            center = new Point((float) 0, (float) 0);
            radius = (float) 0;
        } else {
            final float A = -(p2.x - p1.x) / P2_MINUS_P1_Y;
            final float A_PRIME = -(p3.x - p2.x) / P3_MINUS_P2_Y;
            final float A_PRIME_MINUS_A = A_PRIME - A;

            if (A_PRIME_MINUS_A == 0.0) {
                center = new Point((float) 0, (float) 0);
                radius = (float) 0;
            } else {
                final float P2_SQUARED_X = p2.x * p2.x;
                final float P2_SQUARED_Y = p2.y * p2.y;


                final float B = (float) ((P2_SQUARED_X - p1.x * p1.x + P2_SQUARED_Y - p1.y * p1.y) /
                        (2.0 * P2_MINUS_P1_Y));
                final float B_PRIME = (float) ((p3.x * p3.x - P2_SQUARED_X + p3.y * p3.y - P2_SQUARED_Y) /
                        (2.0 * P3_MINUS_P2_Y));


                final float XC = (B - B_PRIME) / A_PRIME_MINUS_A;
                final float YC = A * XC + B;

                final float DXC = p1.x - XC;
                final float DYC = p1.y - YC;

                center = new Point(XC, YC);
                radius = (float) Math.sqrt(DXC * DXC + DYC * DYC);
            }
        }
    }

    public boolean containsPoint(final Point p) {
        return p.distance(center) <= radius;
    }

    @Override
    public String toString() {
        return center.toString() + ", Radius: " + radius;
    }

    public Float[] getY(float x){
        Float[] y = new Float[2];
        float a=1;
        float b= -(2*center.y);
        float c = (float) (Math.pow(center.y,2)+Math.pow((x-center.x),2)-Math.pow(radius,2));
        float sqr=b*b;
        float delta=-4*a*c;
        float result=sqr+delta;
        if (result<0) {
            y[0] = null;
            y[1] = null;
        }
        if (result==0) {
            result= (float) Math.sqrt(result);
            y[0] = (-b)/(2*a);
            y[1] = null;
        }
        if (result>0) {
            result= (float) Math.sqrt(result);
            y[0] = (-b+result)/(2*a);
            y[1] = (-b-result)/(2*a);
        }
        return y;
    }
    public Float[] getX(float y){
        Float[] x = new Float[2];
        float a=1;
        float b= -(2*center.x);
        float c = (float) (Math.pow(center.x,2)+Math.pow((y-center.y),2)-Math.pow(radius,2));
        float sqr=b*b;
        float delta=-4*a*c;
        float result=sqr+delta;
        if (result<0) {
            x[0] = null;
            x[1] = null;
        }
        if (result==0) {
            result= (float) Math.sqrt(result);
            x[0] = (-b)/(2*a);
            x[1] = null;
        }
        if (result>0) {
            result= (float) Math.sqrt(result);
            x[0] = (-b+result)/(2*a);
            x[1] = (-b-result)/(2*a);
        }
        return x;
    }
}
