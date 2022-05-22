package TimeSeries;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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

    public void addLine(String line){
        String[] arrLine = line.split(",");
        for (int i = 0; i < props.length; i++) {
            map.get(props[i]).add(Float.parseFloat(arrLine[i]));
        }
    }
    public void exportCSV(String fileName){
        try {
            PrintWriter csvFile = new PrintWriter(new File(fileName));
            for (int i = 0; i < props.length ; i++) {
                csvFile.print(props[i]);
                if(i!=(props.length-1))
                    csvFile.print(",");
            }
            csvFile.println("");
            int rowsNum = map.get(props[0]).size();
            for (int i = 0; i <rowsNum ; i++) {
                for (int j = 0; j < props.length ; j++) {
                    csvFile.print(map.get(props[j]).get(i));
                    if(j!=(props.length -1))
                        csvFile.print(",");
                }
                csvFile.println("");
            }
            csvFile.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
