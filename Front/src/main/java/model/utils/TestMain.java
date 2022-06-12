package model.utils;

public class TestMain {
    public static void main(String[] args) {
        Play player = new Play();
        player.setPath("flt.csv"); // TODO: change to correct file name

        new Thread(()->{
            player.play();
            player.setSpeed(2);

        }).start();
    }
}
