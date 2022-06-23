package view.Charts;

import javafx.scene.Node;
import viewModel.ViewModel;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class BarChart extends Chart{
    String name;

    @Override
    public void init(ViewModel vm, Node root) throws Exception {

    }

    @Override
    public void updateUi(Object obj) {}
    @Override
    public void updateChart(Map<Float, Float> map) {}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}