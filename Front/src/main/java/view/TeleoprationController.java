package view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.*;

public class TeleoprationController extends BaseController {
    @FXML
    TextArea textFile;
    @Override
    public void updateUi(Object obj) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void sendCode(){
        System.out.println(textFile.getText());
        textFile.setText("");
    }
}
