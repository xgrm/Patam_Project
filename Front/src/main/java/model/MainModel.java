package model;



import model.utils.Config;
import model.utils.Play;

import java.net.Socket;

public class MainModel implements Model{
    Socket fg;
    private Play player;
//    Server server;
//    AnomalyDetection am;
//    TimeSeries ts;

    private void connectToFG(){}
    private void openServer(){}
    private void createMapsFromFiles(String s1, String s2){}
    private  void startServer(){}
    private void startClient(){}
    public void close(){}

    public MainModel() {
        this.player = new Play();
    }

    @Override
    public void setNewAlgo() {}
    public void startPlay(int speed, String path){
        this.setPlayerPath(path);
        this.setPlayerSpeed(speed);
        this.player.play();
    }

    public void setPlayerSpeed(int speed) { this.player.setSpeed(speed); }
    public void setPlayerPath(String path) {
        this.player.setPath(path);
    }
    public void setPlayerPause(){
        this.player.pause();
    }
    public void setPlayerStop(){
        this.player.stop();
    }
}
