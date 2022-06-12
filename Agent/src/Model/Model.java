package Model;

import Server.ClientHandler;
import TimeSeries.TimeSeries;

public interface Model extends ClientHandler {
    public void setAileron(double x);
    public void setElevator(double x);
    public void setRudder(double x);
    public void setThrottle(double x);
    public TimeSeries getTimeSeries();
}
