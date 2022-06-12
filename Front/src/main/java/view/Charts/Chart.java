package view.Charts;


import view.BaseController;

import java.util.Map;

public abstract class Chart extends BaseController {
    Map<Float,Float> graph;
    public abstract void updateChart(Map<Float, Float> map);
}
