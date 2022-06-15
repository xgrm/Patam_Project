package view;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import viewModel.ViewModel;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;


public class MenuTabController extends BaseController implements Initializable, Observer {

    @FXML
    SplitPane monitoring;

    @FXML
    AnchorPane FleetOverView,Monitoring,Teleopration,TimeCapsule;
    public MenuTabController() {

    }
    @Override
    public void init(ViewModel vm, Node root) throws Exception{
        this.setViewModel(vm);
        addPane(FleetOverView, "FleetOverView.fxml",0,0, "Fleet OverView");
        addPane(Monitoring, "Monitoring.fxml", 0,0,"Monitoring");
        addPane(Teleopration, "Teleopration.fxml",0,0, "Teleoperation");
        addPane(TimeCapsule, "TimeCapsule.fxml", 0,0,"Time Capsule");
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



    @Override
    public void updateUi(Object obj) {

    }
}