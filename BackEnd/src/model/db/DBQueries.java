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
            createFlightsTable();
            createFlightDataTable();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void createFlightsTable() {
        Statement statement;
        String table = new String("CREATE TABLE IF NOT EXISTS flights(flight_id SERIAL NOT NULL PRIMARY KEY,aircraft_name varchar(100) ,isActive varchar (100),totalMiles float )");
        try {
            statement = this.db_connection.createStatement();
            statement.executeUpdate(table);
            System.out.println("Table Created!");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    public void createFlightDataTable() {
        Statement statement;
        String table = "";
        try {
            Scanner prop = new Scanner(new File("src/model/db/SymbolsForColums.data"));
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
    public int addFlight(String name,String active,float miles) {
        Statement statement;
        int id = 0;
        try {
            String query = String.format("insert into flights(aircraft_name, isActive ,totalMiles) values('%s','%s','%f');", name,active,miles);
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
    public void updateFlight(int id,String active,float miles) {
        Statement statement;
        try {
            String query = String.format("update flights set isActive = '%s' ,totalMiles = '%f' where flight_id = %d;",active,miles,id);
            statement = this.db_connection.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void insertRow(int id,String data) {
        StringBuilder sb = new StringBuilder("'"+id+"',");
        data = sb.append(data).toString();
        Statement statement;
        try {
            String query = String.format("insert into flight_data %s values(%s);", this.flight_data_cols, data);
            statement = this.db_connection.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getFlightById(int id) {
        Statement statement;
        ResultSet rs = null;
        StringBuilder table = new StringBuilder();
        StringBuilder sb;
        String columns = this.flight_data_cols.substring(flight_data_cols.indexOf("(")+1,flight_data_cols.indexOf(")"));
        String[] cols = columns.split(",");
        try {
            String query = String.format("select %s from flight_data where flight_id=%d",columns, id);
            statement = this.db_connection.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()) {
                sb = new StringBuilder();
                for (String col: cols) {
                    sb.append(rs.getString(col) +",");
                }
                sb.replace(sb.length() - 1,sb.length(),"\r\n");
                table.append(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table.toString();
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
    public String getKPI(){
        Statement statement;
        ResultSet rs = null;
        StringBuilder table = new StringBuilder();
        StringBuilder sb;
        String columns = "flight_id";
        try {
            String query = String.format("select %s from flights",columns);
            statement = this.db_connection.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()) {
                table.append(rs.getString(columns)).append(",");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        table.deleteCharAt(table.length()-1);
        return table.toString();
    }
    public float getTotalMiles(){
        Statement statement;
        ResultSet rs = null;
        float totalMiles = 0;
        try {
            String query = "select count(totalmiles) from flights";
            statement = this.db_connection.createStatement();
            rs = statement.executeQuery(query);
            totalMiles=rs.getFloat(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalMiles;
    }
    public String getActiveFlight(){
        Statement statement;
        ResultSet rs = null;
        int active = 0;
        int notActive = 0;
        try {
            String query = "select count(isactive) from flights where isactive = 'no'";
            statement = this.db_connection.createStatement();
            rs = statement.executeQuery(query);
            notActive = rs.getInt(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String query = "select count(isactive) from flights where isactive = 'yes'";
            statement = this.db_connection.createStatement();
            rs = statement.executeQuery(query);
            active = rs.getInt(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int total = active+notActive;
        return (active/total)*100+"%-"+(notActive/total)*100+"%";
    }
    public void close(){
        try {
            db_connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
