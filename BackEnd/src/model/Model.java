package model;

public interface Model {
    public int addFlight(String flightName,String active,float miles);
    public void insertRow(int id,String line);
    public String getFlightById(int id);
    public void deleteFlightById(int id);
    public String getKPI();
    public void runInterpreter(String code);
    public void close();
}
