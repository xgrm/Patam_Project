package view;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import viewModel.ViewModel;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class FleetOverViewController extends BaseController{



    @FXML
    AnchorPane WorldMap,DataBoard;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void init(ViewModel vm, Node root) throws Exception {
        viewModel = vm;
        addPane(WorldMap, "WorldMap.fxml",0,0, "worldMap");
        addPane(DataBoard, "DataBoard.fxml",0,0, "fleetOverView");
    }


    @Override
    public void updateUi(Object obj) {}
}