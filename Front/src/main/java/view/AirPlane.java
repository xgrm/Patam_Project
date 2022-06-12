package view;



import java.net.URL;
import java.util.ResourceBundle;

public class AirPlane extends BaseController {
    private String airplaneName;
    private float  height, direction, speed;
    public float imageHeight, imageWidth;
    Position p;

    public AirPlane(String airplaneName, float height, float dir, float speed, Position p) {
        this.airplaneName = airplaneName;
        this.height = height;
        this.direction = dir;
        this.speed = speed;
        this.p = p;
        this.imageHeight=50;
        this.imageWidth=50;
    }


    @Override
    public void updateUi(Object obj) {}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public String getAirplaneName() {return airplaneName;}
    public void setAirplaneName(String airplaneName) {this.airplaneName = airplaneName;}
    public float getHeight() {return height;}
    public void setHeight(float height) {this.height = height;}
    public float getDir() {return direction;}
    public void setDir(float dir) {this.direction = dir;}
    public float getSpeed() {return speed;}
    public void setSpeed(float speed) {this.speed = speed;}
    public Position getP() {return p;}
    public void setP(Position p) {this.p = p;}
}
