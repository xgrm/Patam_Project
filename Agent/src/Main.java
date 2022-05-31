import Controller.Controller;
import Controller.Test;
import Model.*;

public class Main {
    public static void main(String[] args) {
        AgentModel model = new AgentModel("src/external_files/ModelProprties.txt", "src/external_files/Symbols.txt");
//        Controller cn = new Controller(model,"src/external_files/ControllerProprties.txt",false);
        Test cn = new Test(model, "src/external_files/ControllerProprties.txt");

        new Thread(() -> {
            try {
                Thread.sleep(1000 * 20);
                cn.close();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }
}