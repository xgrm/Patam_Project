package view;


import com.sothawo.mapjfx.Projection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import viewModel.ViewModel;
import java.net.URL;
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
        //creating a command that put a flag on the getData command that ask for agent data.
        command = new SerializableCommand("setMapData","");
        command.setId(1);
        addPane(WorldMap, "WorldMap.fxml",0,0,0,0, "worldMap");
        addPane(DataBoard, "DataBoard.fxml",0,0,0,0, "fleetOverView");
        WorldMapController controller = (WorldMapController) controllers.get("WorldMapController");
        final Projection projection =Projection.WEB_MERCATOR;
        controller.initMapAndControls(projection);
    }


    @Override
    public void updateUi(Object obj) {
        // transfer the data to the controllers.
        this.controllers.forEach((key,value)->value.updateUi(obj));
    }

    @Override
    public void onTabSelection() {
        command.setId(1);
        this.controllers.forEach((key,value)->value.onTabSelection());
        // adding a flag to get data about agents.
        viewModel.exe(command);
        //adj the window size to fit
        Platform.runLater(()->{
            this.viewModel.getStage().setMaxWidth(1150);
            this.viewModel.getStage().setMaxHeight(588);
            this.viewModel.getStage().setMinWidth(1150);
            this.viewModel.getStage().setMinHeight(588);
        });
    }

    @Override
    public void onTabLeave() {
        super.onTabLeave();
        // removing the flag
        command.setId(0);
        viewModel.exe(command);
    }
}