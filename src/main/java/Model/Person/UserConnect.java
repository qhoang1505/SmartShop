package Model.Person;

import Controller.Database.test.DBController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserConnect implements IConnect<User> {
    int check;
    public static UserConnect getInstance() {
        return new UserConnect();
    }
    @Override
    public int insert(User user) throws SQLException {
        Connection con = DBController.getConnection();

        // Tao doi tuong statement
        Statement st = con.createStatement();

        // thuc thi
        String sql = "INSERT INTO user(username, password)" + "VALUES ('"+user.getUsername()+"', '"+user.getPassword()+"')";

        check = st.executeUpdate(sql);
        System.out.println("Da thuc thi" + sql);
        System.out.println("Có " + check + "dong bi thay doi!");
        return 0;
    }

    @Override
    public int update(User user) throws SQLException {
        Connection con = DBController.getConnection();

        // Tao doi tuong statement
        Statement st = con.createStatement();

        // thuc thi
        String sql = "UPDATE user " +
                "SET " +
                "username = '" + user.getUsername() + "', " +
                "password = '" + user.getPassword() + "' " +
                "WHERE username = '" + user.getUsername() + "'";

        check = st.executeUpdate(sql);
        System.out.println("Da sua doi" + sql);
        System.out.println("Có " + check + "dong bi thay doi!");
        return 0;
    }

    @Override
    public int detele(User o) throws SQLException {
        return 0;
    }

    @Override
    public ArrayList<User> selectAll() {
        ArrayList<User> result = new ArrayList<User>();
        Connection con = DBController.getConnection();

        // Tao doi tuong statement
        try {
            Statement st = con.createStatement();
            String sql = "SELECT * FROM user";
            ResultSet rs;
            rs = st.executeQuery(sql);

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("PASSWORD");
                User user = new User(username, password);
                result.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
