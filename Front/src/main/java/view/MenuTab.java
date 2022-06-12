package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import model.MainModel;
import viewModel.ViewModel;

import java.io.IOException;

public class MenuTab extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MenuTab.class.getResource("MenuTab.fxml"));
        MenuTabController mwc = fxmlLoader.getController();
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setTitle("Patam Project!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
