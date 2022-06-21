package model.AnomalyDetection;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TimeSeries {
    private Map<String, float[]> map;
    private String[] features;
    private int size;
    private int timeStep;

    private boolean lineByline;
    private List<CorrelatedFeatures> correlatedFeatures;

    public TimeSeries(String csvFileName) {
        Scanner csvFile = null;
        map = new HashMap<>();
        Map<String, ArrayList<Float>> tempMap = new HashMap<>();

        // Open the csv file
        try {
            csvFile = new Scanner(new BufferedInputStream(new FileInputStream(csvFileName)));
        } catch (FileNotFoundException e) {}

        // create a properties array
        String props[] = csvFile.nextLine().split(",");
        for (String prop : props) {
            tempMap.put(prop, new ArrayList<>());
        }

        csvFile.useDelimiter(",|\\n");
        while (csvFile.hasNext()) {
            for (int i = 0; i < props.length; i++){
                tempMap.get(props[i]).add(csvFile.nextFloat());
            }
        }
        csvFile.close();

        for(String prop : props){
            ArrayList<Float> tempArr = tempMap.get(prop);
            Float[] temp = new Float[tempArr.size()];
            tempArr.toArray(temp);
            map.put(prop,FloatToPrimitive(temp));
        }
        this.size= props.length;
        this.features = props;
    }

    public TimeSeries(String[] features){
        this.features = features;
        this.lineByline = true;
        this.map = new HashMap<>();
        this.timeStep = 0;
        for (String feature: features){
            this.map.put(feature,new float[500]);
        }
    }

    public void addLine(ConcurrentHashMap<String,Float> data){
        if(lineByline){
            if (timeStep>map.get(features[0]).length-1){
                map.forEach((k,v)->{
                    float[] temp = new float[v.length*2];
                    System.arraycopy(v,0,temp,0,v.length);
                    map.put(k,temp);
                });
            }
            map.forEach((k,v)->this.map.get(k)[timeStep] = data.get(k));
            timeStep++;
        }
    }

    private float[] FloatToPrimitive(Float[] arr) {
        float[] temp = new float[arr.length];
        int i = 0;
        for(Float f : arr){
            temp[i++] = f;
        }
        return temp;
    }

    public float[] GetValueByProp(String prop) {
        return map.get(prop);
    }

    public int getSize() {
        return size;
    }

    public int getRowLength() {
        return this.map.get(this.features[0]).length;
    }

    public String[] getFeatures() {
        return features;
    }

    public void setCorrelatedFeatures(List<CorrelatedFeatures> correlatedFeatures) {
        this.correlatedFeatures = correlatedFeatures;
    }

    public List<CorrelatedFeatures> getCorrelatedFeatures() {
        return correlatedFeatures;
    }
}
