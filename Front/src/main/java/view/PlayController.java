package view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;
import viewModel.ViewModel;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class PlayController extends BaseController{
    @FXML
    Button forward,rewind,pause,play,stop,open;
    @FXML
    TextField speedControl;
    @FXML
    Slider timeSlider;
    @FXML
    Label time;
    int timeStepSize;
    FileChooser fileChooser;
    Timeline timeline;
    long speed = 1;
    long rewindMulti = 2;
    long forwardMulti = 2;
    long localSpeed;
    Stage stage;
    @Override
    public void init(ViewModel vm, Node root) throws Exception {
        this.speed = 1;
        this.viewModel = vm;
        this.stage = viewModel.getStage();
        this.viewModel.timeStep.bind(timeSlider.valueProperty());
        setDisable();
        this.fileChooser = new FileChooser();

        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?([1-9][0-9]*)?")) {
                return change;
            }
            return null;
        };
        speedControl.setTextFormatter(new TextFormatter<Long>(new LongStringConverter(), 1l, integerFilter));
        speedControl.setOnKeyPressed((e)->{
            if(e.getCode().equals((KeyCode.ENTER))){
                Long speed = (Long) speedControl.getTextFormatter().getValue();
                if (speed != null) {
                    setSpeedControl(speed);
                } else
                    setSpeedControl(1l);
            }
        });
        open.setOnAction((e)->{
            open();
        });
        timeSlider.valueProperty().addListener((o,ov,nv)->{setLabel((Double) nv);});
    }

    private void setDisable(){
        forward.setDisable(true);
        rewind.setDisable(true);
        pause.setDisable(true);
        play.setDisable(true);
        stop.setDisable(true);
        timeSlider.setDisable(true);
        speedControl.setDisable(true);
    }
    @Override
    public void updateUi(Object obj) {
        SerializableCommand command = (SerializableCommand) obj;
        if(command.getCommandName().intern()=="timeStepsSize"){
            this.timeStepSize = Integer.parseInt(command.getData());
            this.timeSlider.setMax(this.timeStepSize);
            setTimeLine();
//            setLabel(0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    private void setTimeLine(){
        forwardMulti =2;
        rewindMulti = 2;
        this.timeline = new Timeline(
                new KeyFrame(Duration.millis(0),new KeyValue(timeSlider.valueProperty(),0)),
                new KeyFrame(Duration.millis(timeStepSize*100),new KeyValue(timeSlider.valueProperty(),timeStepSize))
        );
    }
    private void setCustTimeLine(long timeStep, long speed){
        timeline.stop();
        timeline.getKeyFrames().clear();
        this.timeline = new Timeline(
                new KeyFrame(Duration.millis(0),new KeyValue(timeSlider.valueProperty(),timeStep)),
                new KeyFrame(Duration.millis(timeStepSize*100/speed),new KeyValue(timeSlider.valueProperty(),timeStepSize))
        );
        timeline.play();
    }
    public void stop(ActionEvent actionEvent) {
        viewModel.exe(new SerializableCommand("stop",""));
        timeline.stop();
        setTimeLine();
    }

    public void setSpeedControl(long speed) {
        this.speed = speed;
        viewModel.exe(new SerializableCommand("setSpeed",speed+""));
        setCustTimeLine((long) timeSlider.getValue(),speed);
    }
    private void setLabel(double timeStep){ //TODO: FIX THE TIME CALC
        float sec = (float)(timeStepSize-timeStep)*(float) 100 /(float)1000;
        SimpleDateFormat df = new SimpleDateFormat("mm:ss");

        String re = df.format(new Date((long) (sec/60)));
        System.out.println(sec+" "+re);
        time.setText(re);
    }
    public void play(ActionEvent actionEvent) {
        viewModel.exe(new SerializableCommand("play"," "));
        if(timeline.getStatus().equals("RUNNING")){
            setCustTimeLine((long) timeSlider.getValue(),1);
            rewindMulti = 2;
            forwardMulti =2;
        }
        timeline.play();

    }
    public void pause(ActionEvent actionEvent) {
        viewModel.exe(new SerializableCommand("pause"," "));
        timeline.pause();
    }

    public void open(){
        File file = fileChooser.showOpenDialog(this.stage);
        if (file != null) {
            viewModel.exe(new SerializableCommand("setPath",file.getAbsolutePath()));
            forward.setDisable(false);
            rewind.setDisable(false);
            pause.setDisable(false);
            play.setDisable(false);
            stop.setDisable(false);
            timeSlider.setDisable(false);
            speedControl.setDisable(false);
        }
        else {
            //TODO: ERR
        }
    }
    public void rewind(ActionEvent actionEvent) {
        setSpeedControl(speed*rewindMulti);
        timeline.stop();
        timeline.getKeyFrames().clear();
        this.timeline = new Timeline(
                new KeyFrame(Duration.millis(0),new KeyValue(timeSlider.valueProperty(),(long) timeSlider.getValue())),
                new KeyFrame(Duration.millis(timeStepSize*100/(speed)),new KeyValue(timeSlider.valueProperty(),0))
        );
        timeline.play();
        rewindMulti++;
    }

    public void forward(ActionEvent actionEvent) {
        setSpeedControl(speed*rewindMulti);
        setCustTimeLine((long) timeSlider.getValue(),speed);
        forwardMulti++;
    }

    public void setTimeSlider(MouseEvent mouseEvent) {
    }
}
