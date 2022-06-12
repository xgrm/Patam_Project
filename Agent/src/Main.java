import Controller.Controller;
import Interpreter.Interpreter;
import Interpreter.ShuntingYardAlgorithm;
import Model.AgentModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

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
                StringBuilder sb = new StringBuilder();
                Scanner scanner = new Scanner(new File("src/external_files/code.txt"));
                while (scanner.hasNext()){
                    sb.append(scanner.nextLine()+" \n");
                }
                sb.deleteCharAt(sb.length()-1);
                Thread.sleep(1*1000);
            System.out.println("done sleeping!");
            Interpreter interpreter = new Interpreter(model);
            String code = sb.toString();
            interpreter.run(code);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).start();

//        new Thread(()->{
//            try {
//                Thread.sleep(1*1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("end the sleep");
//            Interpreter interpreter = new Interpreter(model);
//            interpreter.run("var brakes = bind \"/controls/speedbreak\" \n" +
//                    "var throttle = bind \"/controls/engines/engine/throttle\" \n" +
//                    "var heading = bind \"/instrumentation/heading-indicator/offset-deg\" \n" +
//                    "var airspeed = bind \"/instrumentation/airspeed-indicator/indicated-speed-kt\" \n" +
//                    "var roll = bind \"/instrumentation/attitude-indicator/indicated-roll-deg\" \n" +
//                    "var pitch = bind \"/instrumentation/attitude-indicator/internal-pitch-deg\" \n" +
//                    "var rudder = bind \"/controls/flight/rudder\" \n" +
//                    "var aileron = bind \"/controls/flight/aileron\" \n" +
//                    "var elevator = bind \"/controls/flight/elevator\" \n" +
//                    "var alt = bind \"/instrumentation/altimeter/indicated-altitude-ft\" \n" +
//                    "brakes = 0 \n" +
//                    "throttle = 1 \n" +
//                    "var h0 = heading \n" +
//                    "while alt < 1000 { \n" +
//                    "rudder = ( h0 - heading ) / 20 \n" +
//                    "aileron = - roll / 70 \n" +
//                    "elevator = pitch / 50 \n" +
//                    "sleep 250 \n" +
//                    "} \n" +
//                    "print \"exit\" \n");
//
//        }).start();

    //String exit = "\"exit\"";
    //System.out.println(exit.contains("\""));
    //        System.out.println(exit.substring(1,exit.length()-1));

    }
}