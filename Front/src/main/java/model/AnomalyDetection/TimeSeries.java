package model.AnomalyDetection;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TimeSeries {
    private Map<String, float[]> map;
    private String[] features;
    private int size;

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
                //float f = csvFile.nextFloat();
                //System.out.println(f);
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
}
