package viewModel;

import javafx.beans.property.FloatProperty;
import model.MainModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class ViewModel extends Observable implements Observer {
    MainModel model;
    Socket backend;
    HashMap<String,String> propMap;
    public FloatProperty aileron, elevator, rudder, throttle;

    public ViewModel(MainModel model,String propPath) {
        this.model = model;
        this.propMap = new HashMap<>();
        createPropMap(propPath);
        new Thread(()->{
            try {
                backend = new Socket(propMap.get("backEndIP"),Integer.parseInt(propMap.get("backEndPort")));
                connectToBackend();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    private void createPropMap(String propPath){
        try {
            Scanner scanner = new Scanner(new File(propPath));
            String[] tokens;
            while (scanner.hasNext()){
                tokens = scanner.nextLine().split(",");
                this.propMap.put(tokens[0],tokens[1]);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void connectToBackend(){

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
