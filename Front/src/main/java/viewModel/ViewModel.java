package viewModel;

import javafx.beans.property.FloatProperty;
import model.MainModel;

import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class ViewModel extends Observable implements Observer {
    MainModel model;
    Socket backend;
    public FloatProperty aileron, elevator, rudder, throttle;

    public ViewModel(MainModel model) {
        this.model = model;
    }

    private void connectToBackend(){}

    @Override
    public void update(Observable o, Object arg) {

    }
}
