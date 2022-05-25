import Controller.Controller;
import Model.*;

public class Main {
    public static void main(String[] args) {
        AgentModel model = new AgentModel("src/external_files/ModelProprties.txt","src/external_files/Symbols.txt");
        Controller cn = new Controller(model,"src/external_files/ControllerProprties.txt",false);


//        this.commandMap.put("Aileron",new Controller.Commands.AileronCommand());
//        this.commandMap.put("Elevator",new Controller.Commands.ElevatorCommand());
//        this.commandMap.put("Rudder",new Controller.Commands.RudderCommand());
//        this.commandMap.put("Throttle",new Controller.Commands.ThrottleCommand());
        try {
            for (int i = 0; i < 3; i++) {
                cn.exe("Aileron -1");
                cn.exe("Elevator -1");
                cn.exe("Rudder -1");
                cn.exe("Throttle -1");

                Thread.sleep(2*1000);
                cn.exe("Aileron 1");
                cn.exe("Elevator 1");
                cn.exe("Rudder 1");
                cn.exe("Throttle 1");
                Thread.sleep(2*1000);
            }
            Thread.sleep(45*1000);
            cn.close();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        cn.close();
    }
}