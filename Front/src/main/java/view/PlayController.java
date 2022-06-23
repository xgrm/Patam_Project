package view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
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
    long speed;
    long rewindMulti;
    long forwardMulti;

    long localSpeed;
    Stage stage;
    @Override
    public void init(ViewModel vm, Node root) throws Exception {
        this.timeSlider.setMin(1);
        this.speed = 1;
        this.forwardMulti=0;
        this.rewindMulti=0;
        this.viewModel = vm;
        this.stage = viewModel.getStage();
        this.viewModel.timeStep.bind(timeSlider.valueProperty());
        this.fileChooser = new FileChooser();
        setPlayerDisable(true);
        setTextFormatter();
        speedControl.setOnKeyPressed((e)-> getSpeedFromSpeedTextField(e));
        open.setOnAction((e)->open());
        timeSlider.valueProperty().addListener((o,ov,nv)->setTimeLabel((Double) nv));
    }
    @Override
    public void updateUi(Object obj) {
        SerializableCommand command = (SerializableCommand) obj;
        if(command.getCommandName().intern()=="timeStepsSize"){
            this.timeStepSize = Integer.parseInt(command.getData())-1;
            this.timeSlider.setMax(this.timeStepSize);
            setTimeLine();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    private void setPlayerDisable(boolean isDisable){
        forward.setDisable(isDisable);
        rewind.setDisable(isDisable);
        pause.setDisable(isDisable);
        play.setDisable(isDisable);
        stop.setDisable(isDisable);
        timeSlider.setDisable(isDisable);
        speedControl.setDisable(isDisable);
    }
    public void open(){
        File file = fileChooser.showOpenDialog(this.stage);
        if (file != null) {
            viewModel.exe(new SerializableCommand("setPath",file.getAbsolutePath()));
            setPlayerDisable(false);
        }
        else {
            //TODO: ERR
        }
    }
    private void setTextFormatter(){
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?([1-9][0-9]*)?")) {
                return change;
            }
            return null;
        };
        speedControl.setTextFormatter(new TextFormatter<Long>(new LongStringConverter(), 1l, integerFilter));
    }
    private void getSpeedFromSpeedTextField(KeyEvent e){
        if(e.getCode().equals((KeyCode.ENTER))){
            Long speed = (Long) speedControl.getTextFormatter().getValue();
            if (speed != null) {
                this.speed = speed;
            } else
                this.speed = 1l;
            if(timeline.getStatus().equals(Animation.Status.RUNNING))
                setCustomTimeLine((long) timeSlider.getValue(),speed);
            else
                setTimeLine(); //TODO: IF IN PAUSE
        }
    }
    private void setSpeedControl(long speed) {
        this.speed = speed;
        this.speedControl.setText(String.valueOf(speed));
    }
    private void setTimeLine(){
        forwardMulti = 0;
        rewindMulti = 0;
        this.timeline = new Timeline(
                new KeyFrame(Duration.millis(0),new KeyValue(timeSlider.valueProperty(),1)),
                new KeyFrame(Duration.millis(timeStepSize*100/speed),new KeyValue(timeSlider.valueProperty(),timeStepSize))
        );
    }
    private void setCustomTimeLine(long timeStep, long speed){
        timeline.stop();
        timeline.getKeyFrames().clear();
        this.timeline = new Timeline(
                new KeyFrame(Duration.millis(0),new KeyValue(timeSlider.valueProperty(),timeStep)),
                new KeyFrame(Duration.millis(timeStepSize*100/speed),new KeyValue(timeSlider.valueProperty(),timeStepSize))
        );
        setSpeedControl(speed);
        timeline.play();
    }
    public void play(ActionEvent actionEvent) {
        viewModel.exe(new SerializableCommand("play"," "));
        if(timeline.getStatus().equals(Animation.Status.RUNNING)){
            restForwardOrRewind();
            setCustomTimeLine((long) timeSlider.getValue(),speed);
        }
        else
            timeline.play();

    }
    public void pause(ActionEvent actionEvent) {
        restForwardOrRewind();
        setCustomTimeLine((long) timeSlider.getValue(),speed);
        timeline.pause();
    }
    public void stop(ActionEvent actionEvent) {
        this.timeSlider.valueProperty().setValue(1);
        viewModel.exe(new SerializableCommand("stop",""));
        setCustomTimeLine(1,1l);
        timeline.stop();
    }
    public void forward(ActionEvent actionEvent) {
        if(timeline.getStatus().equals(Animation.Status.RUNNING)){
            if(rewindMulti>0) {
                setCustomTimeLine((long) timeSlider.getValue(), speed - (rewindMulti));
                rewindMulti = 0;
            }
            setCustomTimeLine((long) timeSlider.getValue(),speed+1);
            forwardMulti++;
        }
    }
    public void rewind(ActionEvent actionEvent) {
        if(timeline.getStatus().equals(Animation.Status.RUNNING)){
            if(forwardMulti>0) {
                setCustomTimeLine((long) timeSlider.getValue(), speed - (forwardMulti));
                forwardMulti = 0;
            }
            timeline.stop();
            timeline.getKeyFrames().clear();
            this.timeline = new Timeline(
                    new KeyFrame(Duration.millis(0),new KeyValue(timeSlider.valueProperty(),(long) timeSlider.getValue())),
                    new KeyFrame(Duration.millis(timeStepSize*100/(speed+1)),new KeyValue(timeSlider.valueProperty(),0))
            );
            timeline.play();
            setSpeedControl(speed+1);
            rewindMulti++;
        }
    }
    private void setTimeLabel(double timeStep){ //TODO: FIX THE TIME CALC

        float sec = (float)(timeStepSize-timeStep)*(float) 100 /(float)1000;
        SimpleDateFormat df = new SimpleDateFormat("mm:ss");
        String re = df.format(new Date((long) (sec/60)));
        time.setText(re);
    }
    private void restForwardOrRewind(){
        if(forwardMulti>0){
            speed -= forwardMulti;
            forwardMulti=0;
        } else if (rewindMulti>0) {
            speed-=rewindMulti;
            rewindMulti = 0;
        }
    }
    public void setTimeSlider(MouseEvent mouseEvent) {}
}
