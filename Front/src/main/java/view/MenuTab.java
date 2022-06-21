package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.AnomalyDetection.utils.Circle;
import model.AnomalyDetection.utils.Point;
import model.MainModel;
import viewModel.ViewModel;

import java.text.DecimalFormat;

public class MenuTab extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MenuTab.class.getResource("MenuTab.fxml"));
        MainModel model = new MainModel("src/main/java/model/model.data");
        ViewModel vm = new ViewModel("src/main/java/viewModel/prop.txt",model,stage);
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        MenuTabController mwc = fxmlLoader.getController();
        mwc.init(vm,fxmlLoader.getRoot());
        stage.setTitle("Patam Project!");
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }
}
