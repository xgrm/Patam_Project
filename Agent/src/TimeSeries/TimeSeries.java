package TimeSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TimeSeries {

    private Map<String, ArrayList<Float>> map;
    private String[] props;

    public TimeSeries(String[] props){
        map = new HashMap<>();
        this.props =props;
        Arrays.stream(props).forEach(s->map.put(s,new ArrayList<>()));
    }
}
