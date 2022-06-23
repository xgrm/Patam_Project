package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import viewModel.ViewModel;

import java.net.URL;
import java.util.*;


public class MenuTabController extends BaseController implements Initializable, Observer {

    @FXML
    Tab fleetOverViewTab,monitoringTab,teleoperationTab,timeCapsuleTab;

    @FXML
    AnchorPane FleetOverView,Monitoring,Teleopration,TimeCapsule;

    String currentTab;

    TabPane root;
    public MenuTabController() {

    }
    @Override
    public void init(ViewModel vm, Node root) throws Exception{
        this.setViewModel(vm);
        vm.addObserver(this);
        this.root = (TabPane) root;
        addPane(FleetOverView, "FleetOverView.fxml",0,0,0,0, "Fleet OverView");
        addPane(Monitoring, "Monitoring.fxml", 0,0,0,0,"Monitoring");
        addPane(Teleopration, "Teleopration.fxml",0,0,0,0, "Teleoperation");
        addPane(TimeCapsule, "TimeCapsule.fxml", 0,0,0,0,"Time Capsule");
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {viewModel.exe(new SerializableCommand("getData",""));
            }
        }, 0, viewModel.getUpdateRate());
    }

    @Override
    public void update(Observable o, Object arg) {
        SerializableCommand command = (SerializableCommand) arg;
        if(command.getCommandName().intern()=="moveTab"){
            root.getSelectionModel().select(1);
        }
        this.controllers.get(currentTab).updateUi(arg);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.currentTab = "FleetOverViewController";
        fleetOverViewTab.setOnSelectionChanged(e->{
            controllers.get(currentTab).onTabLeave();
            this.currentTab = "FleetOverViewController";
            controllers.get("FleetOverViewController").onTabSelection();
        });
        monitoringTab.setOnSelectionChanged(e->{
            controllers.get(currentTab).onTabLeave();
            this.currentTab = "MonitoringController";
            controllers.get("MonitoringController").onTabSelection();
        });
        teleoperationTab.setOnSelectionChanged(e->{
            controllers.get(currentTab).onTabLeave();
            this.currentTab = "TeleoprationController";
            controllers.get("TeleoprationController").onTabSelection();
        });
        timeCapsuleTab.setOnSelectionChanged(e->{
            controllers.get(currentTab).onTabLeave();
            this.currentTab = "TimeCapsuleController";
            controllers.get("TimeCapsuleController").onTabSelection();
        });
    }
    @Override
    public void updateUi(Object obj) {

    }
}