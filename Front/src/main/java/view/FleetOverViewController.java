package view;


import com.sothawo.mapjfx.Projection;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import view.Charts.TabController;
import viewModel.ViewModel;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class FleetOverViewController extends BaseController {



    @FXML
    AnchorPane WorldMap,DataBoard;

    SerializableCommand command;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void init(ViewModel vm, Node root) throws Exception {
        viewModel = vm;
        command = new SerializableCommand("setMapData","");
        command.setId(1);
        addPane(WorldMap, "WorldMap.fxml",0,0,0,0, "worldMap");
//        addPane(WorldMap, "s.fxml",0,0,0,0, "worldMap");
        addPane(DataBoard, "DataBoard.fxml",0,0,0,0, "fleetOverView");
        WorldMapController controller = (WorldMapController) controllers.get("WorldMapController");
//        TestController controller = (TestController) controllers.get("TestController");
        final Projection projection =Projection.WEB_MERCATOR;
        controller.initMapAndControls(projection);
    }


    @Override
    public void updateUi(Object obj) {
        this.controllers.forEach((key,value)->value.updateUi(obj));
    }

    @Override
    public void onTabSelection() {
        command.setId(1);
        this.controllers.forEach((key,value)->value.onTabSelection());
        viewModel.exe(command);
    }

    @Override
    public void onTabLeave() {
        super.onTabLeave();
        command.setId(0);
        viewModel.exe(command);
    }
}