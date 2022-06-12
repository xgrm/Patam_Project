package view;

import javafx.fxml.Initializable;

import javafx.scene.canvas.Canvas;
import viewModel.ViewModel;

public abstract class BaseController extends Canvas implements Initializable {
    ViewModel viewModel;
    public abstract void updateUi(Object obj);
}