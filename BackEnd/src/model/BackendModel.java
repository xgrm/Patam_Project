package model;

import model.db.DBQueries;
import model.interpreter.Interpreter;

import java.util.Observable;

public class BackendModel extends Observable implements Model {
    DBQueries db;
    Interpreter interpreter;

    public BackendModel(String DBdetailPath) {
        this.db = new DBQueries(DBdetailPath);
        this.interpreter = new Interpreter();
    }

    @Override
    public int addFlight(String flight) {
        return db.addFlight(flight);
    }
    public int addFlight2(String flightName,String active,float miles) {
        return db.addFlight2(flightName,active,miles);
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
        this.db.updateFlight2(id,active,miles);
    }

    @Override
    public String getKPI() { //TODO: need to implement
        return db.getKPI();
    }

    @Override
    public void runInterpreter(String code) {
        interpreter.run(code);
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
