import Controller.*;
import Model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AgentModel model = new AgentModel("src/external_files/ModelProprties.txt", "src/external_files/Symbols.txt");
       Controller cn = new Controller(model, "src/external_files/ControllerProprties.txt");
        //Controller cn = new Controller(model, "src/external_files/ControllerProprties.txt",true);

    new Thread(()->{
        try {
            Thread.sleep(1000*60*20);
            cn.close();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }).start();
    }

}