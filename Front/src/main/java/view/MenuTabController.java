package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import viewModel.ViewModel;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MenuTabController implements Initializable, Observer {
    DoubleProperty aileron, elevators;
    ViewModel vm;

    @FXML
    Tab FleetOverView_Tab;

    public MenuTabController() {}

    public void setViewModel(ViewModel vm) {
        this.vm=vm;
        vm.addObserver(this);

        this.aileron=new SimpleDoubleProperty();
        vm.aileron.bind(this.aileron);
        this.elevators=new SimpleDoubleProperty();
        vm.elevator.bind(this.elevators);
    }

    @Override
    public void update(Observable o, Object arg) {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}