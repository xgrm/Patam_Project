package view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import viewModel.ViewModel;

import java.net.URL;
import java.util.ResourceBundle;

public class TextFileController extends BaseController{
    @FXML
    TextArea code;
    @FXML
    Button send;

    boolean sent;

    @Override
    public void init(ViewModel vm, Node root) throws Exception {
        this.viewModel = vm;
        sent = false;
        send.setOnAction((e)->{
            if(!sent) {
                code.setDisable(true);
                this.viewModel.exe(new SerializableCommand("Interpreter",code.getText()));
                send.setText("Edit");
                sent = true;
            }
            else{
                code.setDisable(false);
                send.setText("Send");
                sent = false;
            }

        });
    }

    @Override
    public void updateUi(Object obj) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
