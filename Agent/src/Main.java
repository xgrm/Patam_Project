import Controller.Controller;
import Model.*;

public class Main {
    public static void main(String[] args) {
//        AgentModel model = new AgentModel("src/external_files/ModelProprties.txt", "src/external_files/Symbols.txt");
//        Controller cn = new Controller(model, "src/external_files/ControllerProprties.txt",true);
//
//        new Thread(() -> {
//            try {
//                Thread.sleep(1000 * 60 * 2);
//                cn.close();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }).start();

        Play play = new Play();
        play.openCSV("src/external_files/FlightData.csv");
        play.play();

    }
}