package model;

public interface Model {
    public int addFlight(String flight);
    public void insertRow(int id,String line);
    public String getFlightById(int id);
    public void deleteFlightById(int id);
    public String getKPI(int id);
    public void runInterpreter(String code);
    public void close();
}
