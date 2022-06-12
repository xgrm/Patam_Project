package view;

import javafx.fxml.Initializable;

import viewModel.ViewModel;

public abstract class BaseController implements Initializable {
    ViewModel viewModel;
    public abstract void updateUi(Object obj);
}