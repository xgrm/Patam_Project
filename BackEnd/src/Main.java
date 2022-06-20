import controller.Controller;
import model.BackendModel;
import view.SerializableCommand;

import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class Main {
    public static void main(String[] args) {
        BackendModel model = new BackendModel("src/model/dbDetails.data");
        Controller cn = new Controller(model);

        new Thread(()->{
            try {
                Thread.sleep(1000*60*10);
                cn.close();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        ).start();


    }
}