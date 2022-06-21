package model.AnomalyDetection;

import model.AnomalyDetection.utils.Line;
import model.AnomalyDetection.utils.Point;

public class StatLib {
    // simple average
    public static float avg(float[] x){
        float sum = 0;
        for(float f : x)
            sum+=f;
        return (float) sum/x.length;
    }
    // simple average until index i
    public static float avg(float[] X, int i){
        float sum = 0;
        for (int j = 0; j <= i; j++) {
            sum+=X[j];
        }
        return (float) sum/i;
    }
    // returns the variance of X
    public static float var(float[] X){
        float avg = avg(X);
        int len = X.length;
        float sum = 0;
        for(float x:X){
            sum += (float) Math.pow(x-avg,2);
        }

        return sum/len;
    }

    // returns the variance of X until the index i
    public static float var(float[] X,int i){
        float a = avg(X,i);
        float sum = 0;
        for (int j = 0; j <= i ; j++)
            sum+= (float) Math.pow(X[i]-a,2);
        return sum/(i+1);
    }

    // returns the covariance of X and Y
    public static float cov(float[] x, float[] y){
        float sum = 0;
        float avgX = avg(x);
        float avgY = avg(y);
        for (int i = 0; i < x.length; i++) {
            sum+=(x[i]-avgX)*(y[i]-avgY);
        }
        return (sum/ x.length);
    }


    // returns the Pearson correlation coefficient of X and Y
    public static float pearson(float[] x, float[] y){
        float p = (float) (cov(x, y) / ( Math.sqrt(var(x)) * Math.sqrt(var(y)) ));
        return p;
    }

    // performs a linear regression and returns the line equation
    public static Line linear_reg(Point[] points){
        float a=0;
        float b=0;
        int len = points.length;
        float[] x = new float[len];
        float[] y = new float[len];
        for(int i=0; i<len; i++)
        {
            x[i]=points[i].x;
            y[i]=points[i].y;
        }
        a = cov(x,y)/var(x);
        b= avg(y)-(a*avg(x));
        return new Line(a,b);
    }

    // returns the deviation between point p and the line equation of the points
    public static float dev(Point p,Point[] points){
        Line l = linear_reg(points);
        return dev(p,l);
    }

    // returns the deviation between point p and the line
    public static float dev(Point p,Line l){
        return Math.abs(l.f(p.x)-p.y);
    }

    public static Point[] CreatePointArr(float[] x, float[] y) {
        Point[] Temp = new Point[x.length];
        for (int i = 0;i<x.length; i++){
            Temp[i]= new Point(x[i],y[i]);
        }
        return Temp;
    }
}
