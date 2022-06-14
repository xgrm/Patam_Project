import controller.Controller;
import model.BackendModel;
import model.db.DBQueries;


public class Main {
    public static void main(String[] args) {
        BackendModel model = new BackendModel("src/model/dbDetails.data");
        Controller cn = new Controller(model);

        new Thread(()->{
            try {
                Thread.sleep(1000*60*10);
                cn.close();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        ).start();
    }
}