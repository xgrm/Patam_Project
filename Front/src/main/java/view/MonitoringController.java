package view;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class MonitoringController extends BaseController {
    @FXML
    ListView listF;

    @Override
    public void updateUi(Object obj) {}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setList();
    }
    public void  setList(){
        Set<String> s= new HashSet<>();
        s.add("gps_alttitude");
        s.add("magnetic_compass");
        s.add("Skid_ball");
        s.add("vertical_speed");
        s.add("airspeed_indicator");
        s.add("altimeter_pressure");
        s.add("attitude_pitch_deg");
        s.add("attitude_roll_deg");
        s.add("attitude_pitch_deg");
        s.add("attitudel_roll_deg");
        listF.getItems().addAll(s);
    }

}