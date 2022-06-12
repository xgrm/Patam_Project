package view;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import view.AirPlane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
public class WorldMapController extends BaseController {
        @FXML
        Canvas background;

        GraphicsContext canvasGc;
        Image map;
        Image airplane;
        Map<String, AirPlane> planeMap;
        Double heightMap;
        Double widthMap;

        @Override
        public void updateUi(Object obj) {}
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                planeMap=new HashMap<>();
                this.canvasGc=this.background.getGraphicsContext2D();
                widthMap=background.getWidth();
                heightMap=background.getHeight();

                airplane=null;
                map=null;
                try{
                        airplane=new Image(new FileInputStream("src/main/resources/images/airplane.png"));
                        map=new Image(new FileInputStream("src/main/resources/images/world-map.jpeg"));
                        AirPlane plane1=new AirPlane("noy", 50, 50,50, new Position(100,100));
                        AirPlane plane2=new AirPlane("maayan", 50, 50,50, new Position(50,200));
                        planeMap.put("noy",plane1);
                        planeMap.put("maayan",plane2);
                        this.drawAirplane(background.getHeight(), background.getWidth());
                }catch(FileNotFoundException e){
                        e.printStackTrace();
                }
        }
        public void drawMap(Double height, Double width){
                System.out.println("draw map");
                this.canvasGc.drawImage(this.map,0,0,height, width);
        }

        public void drawAirplane( Double height, Double width) {
                System.out.println("draw airplane");
                this.drawMap(height, width);
                if (planeMap.isEmpty()) {
                        System.out.println("no airplanes");
                } else {
                        for (AirPlane p : planeMap.values()) {
                                this.canvasGc.drawImage(this.airplane, p.getP().getX(), p.getP().getY(), 50, 50);
                        }
                }
        }

        public void zoomIn(){
                System.out.println("zoom in");
                heightMap+=50;
                widthMap+=50;
                this.canvasGc.clearRect(0,0,1000,1000);
                this.drawAirplane(heightMap,widthMap);


        }

        public void zoomOut(){
                System.out.println("zoom out");

                this.canvasGc.clearRect(0,0,1000,1000);
                if(heightMap==0 || widthMap==0) return;
                heightMap-=50;
                widthMap-=50;
                this.drawAirplane(heightMap,widthMap);

        }
}