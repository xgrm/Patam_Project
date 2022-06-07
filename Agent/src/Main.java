import Controller.Controller;
import Interpreter.Interpreter;
import Interpreter.ShuntingYardAlgorithm;
import Model.AgentModel;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        AgentModel model = new AgentModel("src/external_files/ModelProprties.txt","src/external_files/Symbols.txt");


//        Controller cn = new Controller(model,"src/external_files/ControllerProprties.txt");
//        try {
//            Thread.sleep(25*60*1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        cn.close();
        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("end the sleep");
            Interpreter interpreter = new Interpreter(model);
            interpreter.run("var throttle = bind '/controls/engines/current-engine/throttle'\n" +
                    "throttle = 1\n" +
                    "while throttle >= 0 {\n" +
                    "throttle = throttle - 0.1\n" +
                    "print 'exit'\n" +
                    "print throttle + 1\n" +
                    "}" );
            model.closeModel();
        }).start();

    }
}