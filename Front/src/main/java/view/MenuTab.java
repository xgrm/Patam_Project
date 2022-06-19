package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import viewModel.ViewModel;

public class MenuTab extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MenuTab.class.getResource("MenuTab.fxml"));
        ViewModel vm = new ViewModel("src/main/java/viewModel/prop.txt");
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
