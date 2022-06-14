import Controller.*;
import Model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AgentModel model = new AgentModel("src/external_files/ModelProprties.txt", "src/external_files/Symbols.txt");
       //Controller cn = new Controller(model, "src/external_files/ControllerProprties.txt");
        Controller cn = new Controller(model, "src/external_files/ControllerProprties.txt",true);

    new Thread(()->{
        try {
            Scanner scanner = new Scanner(new File("src/external_files/code.txt"));
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNext()){
                sb.append(scanner.nextLine()+" \n");
            }
            Thread.sleep(1000);
            System.out.println("after sleep");
            model.startInterpreter(sb.toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }).start();
    new Thread(()->{
        try {
            Thread.sleep(1000*60*3);
            cn.close();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }).start();
    }


}