package model.AnomalyDetection.algorithms;

import model.AnomalyDetection.AnomalyReport;
import model.AnomalyDetection.CorrelatedFeatures;
import model.AnomalyDetection.StatLib;
import model.AnomalyDetection.TimeSeries;
import model.AnomalyDetection.utils.Circle;
import model.AnomalyDetection.utils.Point;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Hybrid implements TimeSeriesAnomalyDetector{

    TimeSeries timeSeries;
    private List<CorrelatedFeatures> correlatedFeatures;
    private HashMap<CorrelatedFeatures,Circle> circlesHashMap;
    @Override
    public void learnNormal(TimeSeries ts) {
        this.timeSeries = ts;
        this.correlatedFeatures = ts.getCorrelatedFeatures();
        this.circlesHashMap = new HashMap<>();
        WelzlAlgorithm wAlgo = new WelzlAlgorithm();
        for (CorrelatedFeatures c : correlatedFeatures) {
            if (Math.abs(c.correlation) >= 0.5 && Math.abs(c.correlation) <= 0.95) {
                Circle minCircle = wAlgo.calculateMiniCircle(new ArrayList<>(List.of(StatLib.CreatePointArr(ts.GetValueByProp(c.feature1), ts.GetValueByProp(c.feature2)))));
                circlesHashMap.put(c,minCircle );
                c.setCircle(minCircle);
            }
        }
    }

    @Override
    public List<AnomalyReport> detect(TimeSeries ts) {
        List<AnomalyReport> detected = new ArrayList<>();

        circlesHashMap.forEach((k,v)->{
            Point[] points = StatLib.CreatePointArr(ts.GetValueByProp(k.feature1), ts.GetValueByProp(k.feature2));
            for (int i = 0; i < points.length ; i++) {
                if(!v.containsPoint(points[i]))
                    detected.add(new AnomalyReport(k.feature1+"-"+k.feature2,k.feature1,k.feature2,i));
            }
        });
        return detected;
    }

    @Override
    public List<AnomalyReport> detectFromLine(ConcurrentHashMap<String, Float> data) {
        List<AnomalyReport> detected = new ArrayList<>();

        circlesHashMap.forEach((k,v)->{
            Point point = new Point(data.get(k.feature1),data.get(k.feature2));
                if(!v.containsPoint(point))
                    detected.add(new AnomalyReport(k.feature1+"-"+k.feature2,k.feature1,k.feature2,1)); //TODO: ADD A TIMESTAP

        });
        return detected;
    }


    private class WelzlAlgorithm {
        private Random rand = new Random();

        public Circle calculateMiniCircle(final List<Point> points) {
            return calculateMiniCircle(points, new ArrayList<Point>());
        }

        private Circle calculateMiniCircle(final List<Point> points, final List<Point> boundary) {
            Circle minimumCircle = null;

            if (boundary.size() == 3) {
                minimumCircle = new Circle(boundary.get(0), boundary.get(1), boundary.get(2));
            } else if (points.isEmpty() && boundary.size() == 2) {
                minimumCircle = new Circle(boundary.get(0), boundary.get(1));
            } else if (points.size() == 1 && boundary.isEmpty()) {
                minimumCircle = new Circle(points.get(0).x, points.get(0).y, (float) 0);
            } else if (points.size() == 1 && boundary.size() == 1) {
                minimumCircle = new Circle(points.get(0), boundary.get(0));
            } else {
                Point p = points.remove(rand.nextInt(points.size()));
                minimumCircle = calculateMiniCircle(points, boundary);

                if (minimumCircle != null && !minimumCircle.containsPoint(p)) {
                    boundary.add(p);
                    minimumCircle = calculateMiniCircle(points, boundary);
                    boundary.remove(p);
                    points.add(p);
                }
            }

            return minimumCircle;
        }
    }
}
