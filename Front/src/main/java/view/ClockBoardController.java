package view;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.skins.HSkin;
import eu.hansolo.medusa.skins.LcdSkin;
import eu.hansolo.medusa.skins.ModernSkin;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import viewModel.ViewModel;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class ClockBoardController extends BaseController {

    @FXML
    Gauge yaw, airSpeed, altimeter, roll, pitch;
    @Override
    public void init(ViewModel vm, Node root) throws Exception {

    }


    @Override
    public void updateUi(Object obj) {
        SerializableCommand command = (SerializableCommand) obj;
        if(command.getCommandName().intern() == "agentData") {
            ConcurrentHashMap<String,Float> symbolTable = new ConcurrentHashMap<>(command.getDataMap());
            Platform.runLater(()->{
                yaw.setValue(symbolTable.get("indicated-heading-deg"));
                airSpeed.setValue(symbolTable.get("airspeed-indicator_indicated-speed-kt"));
                altimeter.setValue(symbolTable.get("altimeter_indicated-altitude-ft"));
                roll.setValue(symbolTable.get("attitude-indicator_indicated-roll-deg"));
                pitch.setValue(symbolTable.get("attitude-indicator_indicated-pitch-deg"));
            });

        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        yaw.setSkin(new LcdSkin(yaw));
        airSpeed.setSkin(new HSkin(airSpeed));
        altimeter.setSkin(new ModernSkin(altimeter));
        roll.setSkin(new ModernSkin(roll));
        pitch.setSkin(new ModernSkin(pitch));
        airSpeed.setMinValue(0);
        airSpeed.setMaxValue(100);
        altimeter.setMinValue(-15);
        altimeter.setMaxValue(700);
        roll.setMinValue(-40);
        roll.setMaxValue(20);
        pitch.setMinValue(-10);
        pitch.setMaxValue(20);
        yaw.setMinValue(0);
        yaw.setMaxValue(357);
    }
}
