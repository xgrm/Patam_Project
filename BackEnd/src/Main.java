import model.db.DBQueries;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        DBQueries db = new DBQueries("dbDetails.txt");
//        int id =  db.addFlight("TLV-JFK");
//        try {
//            Scanner scanner = new Scanner(new File("fly_num.csv"));
//            scanner.nextLine();
//            while (scanner.hasNext()){
//                db.insertRow(id,scanner.nextLine());
//            }
//            scanner.close();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        db.getFlightById(id);
//        db.deleteFlightById(1);

    }
}