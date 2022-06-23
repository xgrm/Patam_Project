package view;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import view.Charts.TabController;
import viewModel.ViewModel;

import java.util.HashMap;

public abstract class BaseController extends Canvas implements Initializable, TabController {
    ViewModel viewModel;
    HashMap<String,BaseController> controllers = new HashMap<>();
    public abstract void init(ViewModel vm, Node root) throws Exception;
    public abstract void updateUi(Object obj);
    void addPane(AnchorPane root, String resource, int x, int y,int scaleX,int scaleY, String id) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
        Node node = fxmlLoader.load();
        node.idProperty().setValue(id);
        if (x != 0 && y != 0) {
            node.setLayoutX(x);
            node.setLayoutY(y);
            node.scaleYProperty().setValue(scaleY);
            node.scaleXProperty().setValue(scaleX);
        }
        root.getChildren().add(node);
        BaseController baseController = fxmlLoader.getController();
        putController(baseController.getClass().getSimpleName(), baseController);
        baseController.init(viewModel,node);
    }
    void putController(String name, BaseController controller){
        controllers.put(name,controller);
    }

    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onTabSelection() {}

    public void onTabLeave(){}
}