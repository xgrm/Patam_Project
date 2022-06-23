import controller.Controller;
import model.BackendModel;


public class Main {
    public static void main(String[] args) {
        BackendModel model = new BackendModel("src/model/model.data");
        Controller cn = new Controller("src/controller/controller.data",model);

        new Thread(()->{
            try {
                Thread.sleep(1000*60*60*1);
                cn.close();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        ).start();
    }
}