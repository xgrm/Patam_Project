package view;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import java.util.Observable;

public class JoystickController extends Observable {


    @FXML
    Canvas joystick;

    boolean mousePushed;
    double jx,jy;
    double mx,my;

    double aileron,elevator;

    public double getAileron(){
        return aileron;
    }

    public double getElevator(){
        return elevator;
    }

    public JoystickController() {
        mousePushed=false;
        jx=0;
        jy=0;
        aileron=0;
        elevator=0;
    }

    public void paint(){
        GraphicsContext gc=joystick.getGraphicsContext2D();
        mx=joystick.getWidth()/2;
        my= joystick.getHeight()/2;
        gc.clearRect(0,0,joystick.getWidth(), joystick.getHeight());
        gc.strokeOval(jx-50,jy-50,100,100);
        aileron=(jx-mx)/mx;
        elevator=(jy-my)/my;
        setChanged();
        notifyObservers();
        System.out.println(aileron+" , " + elevator);

    }

    public void mouseDown(MouseEvent me){
        if(!mousePushed){
            mousePushed=true;
            System.out.println("mouse is down");
            jx=mx;
        }
    }

    public void mouseUp(MouseEvent me){
        if(mousePushed){
            mousePushed=false;
            System.out.println("mouse is up");
            jx=mx;
            jy=my;
            paint();

        }
    }

    public void mouseMove(MouseEvent me){
        if(mousePushed){
            jx=me.getX();
            jy=me.getY();
            paint();
        }
    }

}
