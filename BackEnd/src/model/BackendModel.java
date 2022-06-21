package model;

import model.db.DBQueries;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Scanner;


public class BackendModel extends Observable implements Model {
    DBQueries db;
    HashMap<String,String> propMap;
    String featuresList;
    public BackendModel(String propPath) {
        this.propMap = new HashMap<>();
        createPropMap(propPath);
        this.db = new DBQueries(propMap.get("dbDetails"));
        this.featuresList = propMap.get("featuresList");
    }
    private void createPropMap(String path){
        try {
            Scanner scanner = new Scanner(new File(path));
            String[] tokens;
            while (scanner.hasNext()){
                tokens = scanner.nextLine().split(",");
                this.propMap.put(tokens[0],tokens[1]);
            }
            scanner.close();
        } catch (FileNotFoundException e) {throw new RuntimeException(e);}
    }
    @Override
    public int addFlight(String flightName,String active,float miles) {
        return db.addFlight(flightName,active,miles);
    }

    @Override
    public void insertRow(int id, String line) {
        db.insertRow(id,line);
    }

    @Override
    public String getFlightById(int id) {
        return db.getFlightById(id);
    }

    @Override
    public void deleteFlightById(int id) {
        db.deleteFlightById(id);
    }
    public void updateFlight(int id,String active, float miles) {
        this.db.updateFlight(id,active,miles);
    }

    @Override
    public String getKPI() { //TODO: need to implement
        return db.getKPI();
    }

    public String getFeaturesList() {
        return featuresList;
    }

    @Override
    public void close() {
        db.close();
    }

    @Override
    public void finalize() {
        this.close();
    }
}
