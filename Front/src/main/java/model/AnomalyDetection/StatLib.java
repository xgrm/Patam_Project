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
    public static float var(float[] x){
        float a = avg(x);
        int len = x.length;
        float sum = 0;
        for(float i:x){
            sum+= (float) Math.pow(i-a,2);
        }

        return (float) sum/len;
    }

    // returns the variance of X until the index i
    public static float var(float[] X,int i){
        float a = avg(X,i);
        float sum = 0;
        for (int j = 0; j <= i ; j++)
            sum+= (float) Math.pow(i-a,2);
        return (float) sum/i;
    }

    // returns the covariance of X and Y
    public static float cov(float[] x, float[] y){
        float[] xy = (float[]) x.clone();
        for (int i = 0; i<xy.length; i++){
            xy[i]*=y[i];
        }
        return avg(xy)-avg(x)*avg(y);
    }


    // returns the Pearson correlation coefficient of X and Y
    public static float pearson(float[] x, float[] y){

        float p = (float) (cov(x, y) / (Math.sqrt(var(x)) * Math.sqrt(var(y))));
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
