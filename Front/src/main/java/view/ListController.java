package view;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import viewModel.ViewModel;

import java.net.URL;
import java.util.ResourceBundle;


public class ListController extends BaseController {
    @FXML
    ListView list;
    @Override
    public void init(ViewModel vm, Node root) throws Exception {

    }

    @Override
    public void updateUi(Object obj) {
        if(obj instanceof String){
            String line = (String) obj;
            String[] tokens = line.split("~");
            if(tokens[0].equals("list")){
                Platform.runLater(()->{
                    list.getItems().clear();
                    list.getItems().addAll(line.split(","));
                });
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // featureList.setItems(observableList);
    }
    public void  getListFromServer(){



    }
}
