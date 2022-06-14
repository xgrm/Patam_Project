package view;

import javafx.fxml.Initializable;

import javafx.scene.canvas.Canvas;
import viewModel.ViewModel;

public abstract class BaseController extends Canvas implements Initializable {
    static ViewModel viewModel;
    static void setViewModel(ViewModel vm){
        BaseController.viewModel = vm;
    }
    public abstract void updateUi(Object obj);
}