import Controller.Controller;
import Model.AgentModel;

public class Main {
    public static void main(String[] args) {
        AgentModel model = new AgentModel("external_files/ModelProprties.txt","external_files/Symbols.txt");
        Controller cn = new Controller(model,"external_files/ControllerProprties.txt");
        try {
            Thread.sleep(25*60*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        cn.close();
    }
}