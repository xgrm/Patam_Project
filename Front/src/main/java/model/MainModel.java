package model;


import model.AnomalyDetection.AnomalyDetection;
import model.AnomalyDetection.TimeSeries;
import model.Server.Server;

import java.net.Socket;

public class MainModel implements Model{
    Socket fg;
    Server server;
    AnomalyDetection am;
    TimeSeries ts;

    private void connectToFG(){}
    private void openServer(){}
    private void createMapsFromFiles(String s1, String s2){}
    private  void startServer(){}
    private void startClient(){}
    public void close(){}

    @Override
    public void setAileron() {}

    @Override
    public void setElevator() {}

    @Override
    public void setThrottle() {}

    @Override
    public void setRudder() {}

    @Override
    public void setNewAlgo() {}

}
