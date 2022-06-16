package view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;
import view.AirPlane;
import javafx.scene.transform.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
public class WorldMapController extends BaseController {
        @FXML
        Canvas background;
        @FXML
        AnchorPane page;



        GraphicsContext canvasGc;
        Image map;
        ImageView airplane;
        Map<String, AirPlane> planeMap;
        Double heightMap;
        Double widthMap;
        double orgTranslateX, orgTranslateY;
        double orgSceneX, orgSceneY;
        Button bt;
        Integer zoomoutCounter=0;
        SnapshotParameters params = new SnapshotParameters();

        @Override
        public void updateUi(Object obj) {}
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                planeMap=new HashMap<>();
                this.canvasGc=this.background.getGraphicsContext2D();
                widthMap=background.getWidth();
                heightMap=background.getHeight();
                background.setOnMouseDragged(canvasOnMouseDraggedEventHandler);

                airplane=null;
                map=null;
                try {
                        params.setFill(Color.TRANSPARENT);
                        airplane=new ImageView(new Image(new FileInputStream("src/main/resources/images/airplane.png")));
                        map=new Image(new FileInputStream("src/main/resources/images/world-map.jpeg"));
                        AirPlane plane1=new AirPlane("noy", 50, 60,50, new Position(100,100));
                        AirPlane plane2=new AirPlane("maayan", 50, 50,50, new Position(50,200));
                        AirPlane plane3=new AirPlane("maya", 50, 100,50, new Position(200,250));
                        AirPlane plane4=new AirPlane("saar", 50, 70,50, new Position(500,300));
                        AirPlane plane5=new AirPlane("oz", 50, 10,50, new Position(200,520));
                        planeMap.put("noy",plane1);
                        planeMap.put("maayan",plane2);
                        planeMap.put("maya",plane3);
                        planeMap.put("saar",plane4);
                        planeMap.put("oz",plane5);
                        this.drawAirplane(background.getHeight(), background.getWidth());

                        Label lblName = new Label();
                        Label lblDirection = new Label( "");
                        Label lblHeight = new Label("");
                        Label lblSpeed = new Label("");
                        VBox vBox = new VBox(lblName, lblDirection, lblHeight,lblSpeed);
                        PopOver popOver = new PopOver(vBox);

                        page.setOnMouseClicked(mouseEvent -> {
                                boolean found = false;
                                for (AirPlane ap: this.planeMap.values()) {
                                        if(mouseEvent.getX() >= ap.getP().getX()-5 &&mouseEvent.getX() <= ap.getP().getX()+40
                                                && mouseEvent.getY() >= ap.getP().getY()-5 && mouseEvent.getY() <= ap.getP().getY()+40) {
                                                if(mouseEvent.getClickCount()==1) {
                                                        found = true;
                                                        lblName.setText(" name: " + ap.getAirplaneName());
                                                        lblDirection.setText(" direction: " + ap.getDir() + "");
                                                        lblHeight.setText(" height: " + ap.getHeight() + "");
                                                        lblSpeed.setText(" speed: " + ap.getSpeed() + "");
                                                        popOver.show(this.background, mouseEvent.getX() + 190, mouseEvent.getY() + 60);
                                                }
                                                else
                                                        if(mouseEvent.getClickCount()==2){
                                                                System.out.println("2");
                                                            try {
                                                                Parent root= FXMLLoader.load(getClass().getResource("MenuTab.fxml"));
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }

                                                        }
                                        }
                                }
                                if (!found) popOver.hide();
                        });



//                        this.background.setOnMouseExited(mouseEvent -> { if (popOver.isShowing()) popOver.hide(Duration.millis(5000)); });

                } catch(FileNotFoundException e){
                        e.printStackTrace();
                }
        }
        public void drawMap(Double height, Double width){
                System.out.println("draw map");
                this.canvasGc.drawImage(this.map,0,0,height, width);
        }

        EventHandler<MouseEvent> canvasOnMouseDraggedEventHandler = new EventHandler<MouseEvent>()
        {
                @Override
                public void handle(MouseEvent mouseEvent)
                {

                        double offsetX = mouseEvent.getSceneX() - 400 ;
                        double offsetY = mouseEvent.getSceneY() - 400;
                        double newTranslateX = orgTranslateX + offsetX;
                        double newTranslateY = orgTranslateY + offsetY;

                        ((Canvas) (mouseEvent.getSource())).setTranslateX(newTranslateX);  //transform the object
                        ((Canvas) (mouseEvent.getSource())).setTranslateY(newTranslateY);
                }
        };

        public void drawAirplane( Double height, Double width) {
                System.out.println("draw airplane");
                this.drawMap(height, width);
                if (planeMap.isEmpty()) {
                        System.out.println("no airplanes");
                } else {

                        for (AirPlane p : planeMap.values()) {
                                this.airplane.setRotate(p.getDir());
                                final Image airplaneImage = this.airplane.snapshot(this.params ,null);
                            this.canvasGc.drawImage(airplaneImage, p.getP().getX(), p.getP().getY(), p.imageHeight, p.imageWidth);


                        }
                }
        }

        public void zoomIn(){
                /*System.out.println("zoom in");
                heightMap+=50;
                widthMap+=50;
                this.canvasGc.clearRect(0,0,1000,1000);
                for (AirPlane p : planeMap.values()) {
                p.imageWidth+=5;
                p.imageHeight+=5;
                p.setP(new Position(p.getP().getX()+25,p.getP().getY()+25));
                }
                this.drawAirplane(heightMap,widthMap);*/

                this.canvasGc.scale(1.1,1.1);
                this.drawAirplane(heightMap,widthMap);
        }

        public void zoomOut(){
                System.out.println("zoom out");


                /*if(heightMap==0 || widthMap==0) return;
                heightMap-=50;
                widthMap-=50;
                for (AirPlane p : planeMap.values()) {
                        p.imageWidth-=5;
                        p.imageHeight-=5;
                        p.setP(new Position(p.getP().getX()-10,p.getP().getY()-25));
                }
                this.drawAirplane(heightMap,widthMap);*/
                if(zoomoutCounter<5){
                        this.canvasGc.clearRect(0,0,1000,1000);
                        this.canvasGc.scale(0.9,0.9);
                        this.drawAirplane(heightMap,widthMap);
                }
                zoomoutCounter++;


        }
}