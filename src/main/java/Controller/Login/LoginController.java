package Controller.Login;

import Model.Person.*;
import Model.Person.ObjectCurrent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;


import static Controller.Database.test.DBController.getConnection;
import static Controller.Encryption.Encryption.encodePassword;

public class LoginController implements IConnect<Administrator> {
    private Stage stage;
    private Scene scene;
    @FXML
    private Button loginButton; // Updated variable name
    @FXML
    private PasswordField passwordText; // Updated variable name
    @FXML
    private TextField usernameText; // Updated variable name
    @FXML
    private TextField fullnameText;
    @FXML
    private TextField emailText;
    @FXML
    private Button signupbutton;
    @FXML
    private Button userbutton;
    @FXML
    private Button adminbutton;
    @FXML
    private Label failtext;

    @FXML
    private Label successfultext;
    private String username;

    //DANG NHAP VA DANG KI
    public void login() throws IOException {
        username = usernameText.getText();
        String password = passwordText.getText();
        String encryption_pass = encodePassword(password);
        Administrator admin = new Administrator(username, encryption_pass);
        ArrayList<Administrator> adminlist = AdministratorConnnect.getInstance().selectAll();

        // Kiem tra neu bang thi cho dang nhap
        int loginSuccessful = 0;
        for (Administrator adminstore : adminlist) {
            if (admin.getUsername().equals(adminstore.getUsername()) && admin.getPassword().equals(adminstore.getPassword())) {
                loginSuccessful = 1;
            }
        }
        if (loginSuccessful == 1) {
            Administrator ad = getUserInfo(username);
            ObjectCurrent.setObjectCurrent(ad);
            switchtohomepage();

            Stage stage = (Stage) usernameText.getScene().getWindow();
            stage.close();
        } else {
            // Dang nhap that bai
            failtext.setText("Login failed, please try again!");
        }
    }

    public static Administrator getUserInfo(String username) {
        String query = "SELECT username, password, email, fullname FROM administrator WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String retrievedUsername = resultSet.getString("username");
                    // Kiểm tra giá trị null và cung cấp giá trị mặc định
                    String password = resultSet.getString("password") != null ? resultSet.getString("password") : "";
                    String email = resultSet.getString("email") != null ? resultSet.getString("email") : "";
                    String fullName = resultSet.getString("fullname") != null ? resultSet.getString("fullname") : "";

                    // Tạo và trả về một đối tượng Administrator
                    return new Administrator(retrievedUsername, password, email, fullName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ một cách phù hợp
        }
        return null; // Trả về null nếu không tìm thấy người dùng hoặc có lỗi
    }

    //DANG KI
    public void signup2() {
        String username = usernameText.getText();
        String password = passwordText.getText();
        String email = emailText.getText();
        String fullname = fullnameText.getText();

        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                email == null || email.isEmpty()) {
                failtext.setText("Please fill in all required fields.");
        } else {
            if (password.length() <= 8 && password.length() >= 5) {
                String encryption_pass = encodePassword(password);
                Administrator admin = new Administrator(username, encryption_pass, email, fullname);
                try {
                    // Them admin vao csdl
                    insert(admin);
                    Alert alertFail = new Alert(Alert.AlertType.INFORMATION);
                    alertFail.setTitle("Sign up successful");
                    alertFail.setHeaderText("Your account is signed up successful, please login!");
                    alertFail.showAndWait();

                } catch (SQLException e) {
                    Alert alertFail = new Alert(Alert.AlertType.ERROR);
                    alertFail.setTitle("Sign up Error");
                    alertFail.setHeaderText("Failed to Sign Up. Please try again.");
                    alertFail.showAndWait();
                }
            }
            else {
                Alert alertFail = new Alert(Alert.AlertType.ERROR);
                alertFail.setTitle("Sign up Error");
                alertFail.setHeaderText("Your password should have from 5 to 8 index");
                alertFail.showAndWait();
            }
        }
    }

    // Khu vuc chuyen canh login va sign up
    public void switchtosignup(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LoginScreen/Signup.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchtosignin(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LoginScreen/Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchtohomepage() throws IOException {
        try {
            // Load the FXML for the home page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainApplication/HomePage/HomePage.fxml"));
            Parent root = loader.load();

            // Create a new stage and scene for the home page
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            // Set the scene for the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }


    //----------------------------------------------------
    // LUU Y: KHU VUC KET NOI DATABASE!!!!!!!!!!!!!!!!!!!!
    public static AdministratorConnnect getInstance() {
        return new AdministratorConnnect();
    }
    @Override
    public int insert(Administrator admin) throws SQLException {
        Connection con = getConnection();

        // Tao doi tuong statement
        Statement st = con.createStatement();

        // thuc thi
        String sql = "INSERT INTO administrator(username, password, email, fullname)" + "VALUES ('"+admin.getUsername()+"', '"+admin.getPassword()+"', '"+admin.getEmail()+"', '"+admin.getFullname()+"')";

        st.executeUpdate(sql);
        return 0;
    }
    @Override
    public int update(Administrator admin) throws SQLException {
        Connection con = getConnection();

        // Tao doi tuong statement
        Statement st = con.createStatement();

        // thuc thi
        String sql = "UPDATE administrator " +
                "SET " +
                "username = '" + admin.getUsername() + "', " +
                "password = '" + admin.getPassword() + "' " +
                "WHERE username = '" + admin.getUsername() + "'";

        st.executeUpdate(sql);
        return 0;
    }

    @Override
    public int detele(Administrator admin) throws SQLException {
        Connection con = getConnection();

        // Tao doi tuong statement
        Statement st = con.createStatement();

        // thuc thi
        String sql = "DELETE from administrator " +
                "WHERE username = '" + admin.getUsername() + "'";

        st.executeUpdate(sql);
        return 0;
    }

    @Override
    public ArrayList<Administrator> selectAll() {
        ArrayList<Administrator> result = new ArrayList<Administrator>();
        Connection con = getConnection();

        // Tao doi tuong statement
        try {
            Statement st = con.createStatement();
            String sql = "SELECT * FROM administrator";
            ResultSet rs;
            rs = st.executeQuery(sql);

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                Administrator admin = new Administrator(username, password);
                result.add(admin);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}