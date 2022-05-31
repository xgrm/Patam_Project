package model.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class DBQueries {

    Connection db_connection;
    String flight_data_cols;

    public String getFlight_data_cols() {
        return flight_data_cols;
    }

    public DBQueries(String detailPath) {
        try {
            Scanner scanner = new Scanner(new File(detailPath));
            String[] param = scanner.nextLine().split(" ");
            this.flight_data_cols = scanner.nextLine();
            Class.forName("org.postgresql.Driver");
            this.db_connection = DriverManager.getConnection(param[0],param[1],param[2]);
            if(this.db_connection!=null)
                System.out.println("Connection is established!");
            else System.out.println("Connection failed!");
            scanner.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTable() {
        Statement statement;
        String table = "";
        try {
            Scanner prop = new Scanner(new File("Symbols.txt"));
            StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS flight_data(id SERIAL NOT NULL PRIMARY KEY,flight_id int,FOREIGN KEY (flight_id) REFERENCES flights(flight_id),");
            while (prop.hasNext()) {
                sb.append(prop.nextLine() + " float,");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
            table = sb.toString();
            prop.close();
            statement = this.db_connection.createStatement();
            statement.executeUpdate(table);
            System.out.println("Table Created!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public int addFlight(String name) {
        Statement statement;
        int id = 0;
        try {
            String query = String.format("insert into flights(flight_name) values('%s');", name);
            statement = this.db_connection.createStatement();
            statement.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
            ResultSet result = statement.getGeneratedKeys();
            if (result.next())
                id = (int)result.getObject(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public void insertRow(int id,String data) {
        StringBuilder sb = new StringBuilder("'"+id+"',");
        data = sb.append(data).toString();
        Statement statement;
        try {
            String query = String.format("insert into flight_data %s values(%s);", this.flight_data_cols, data);
            statement = this.db_connection.createStatement();
            statement.executeUpdate(query);
//            System.out.println("Row has been created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFlightById(int id) {
        Statement statement;
        ResultSet rs = null;
        String columns = this.flight_data_cols.substring(flight_data_cols.indexOf("(")+1,flight_data_cols.indexOf(")"));
        String[] cols = columns.split(",");
        try {
            String query = String.format("select %s from flight_data where flight_id=%d",columns, id);
            statement = this.db_connection.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()) {
                StringBuilder sb = new StringBuilder();
                for (String col: cols) {
                    sb.append(rs.getString(col) +",");
                }
                sb.deleteCharAt(sb.length() - 1);
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "problem";
    }

    public void deleteFlightById(int id) {
        Statement statement;
        try {
            String flightsQuery = String.format("delete from flights where flight_id=%d",id);
            String flights_dataQuery = String.format("delete from flight_data where flight_id=%d;", id);
            statement = this.db_connection.createStatement();
            statement.executeUpdate(flights_dataQuery);
            statement.executeUpdate(flightsQuery);
            System.out.println("Row deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            db_connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
