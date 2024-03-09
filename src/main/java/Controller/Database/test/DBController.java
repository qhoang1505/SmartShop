package Controller.Database.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBController {

    //Tao ket noi den db
    public static Connection getConnection ()  {
        Connection a = null;

        try {
            //Dang ki mysql driver
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            String url = "https://5c57-2402-800-6205-fc7b-1d0a-ab80-2957-550a.ngrok-free.app/phpmyadmin/index.php?route=/database/structure&db=hl_shop";
            String username = "root";
            String password = "";

            // create ket noi
            a = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return a;
    }

    //ngat ket noi voi db
    public static void closeConnecttion(Connection c) {
        try {
            if(c != null) {
                c.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void printInfo(Connection c) throws SQLException {
        if(c != null) {
            System.out.println(c.getMetaData().toString());
        }
    }
}
