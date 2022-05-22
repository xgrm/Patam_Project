package Model;

import TimeSeries.TimeSeries;

public interface Model {
    public void setAileron(double x);
    public void setElevator(double x);
    public void setRudder(double x);
    public void setThrottle(double x);
    public TimeSeries getTimeSeries();
}
