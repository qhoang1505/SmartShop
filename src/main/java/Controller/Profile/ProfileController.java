package Controller.Profile;

import Controller.Database.test.DBController;
import Model.Person.Administrator;
import Model.Person.ObjectCurrent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    //Phan 1: Thuoc tinh ---------------------------------------------------------------------------------------------
    @FXML
    private Label emailtext;

    @FXML
    private Label fullnametext;

    @FXML
    private Label phonenumtext;

    @FXML
    private Label usernametext;

    private Stage stage;
    private Scene scene;
    private Connection con;
    private PreparedStatement st;
    private ResultSet rs;
    ObservableList<Administrator> admindata;



    //Phan 2: Xu li su kien lien quan-----------------------------------------------------------------------------------
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Object currentUser = ObjectCurrent.getObjectCurrent();
        if (currentUser instanceof Administrator) {
            Administrator admin = (Administrator) currentUser;
            List<Administrator> adminList = Collections.singletonList(admin);
            String username = admin.getUsername();
            // Hiển thị username trong label
            usernametext.setText(username);
            // Có thể thêm mã để hiển thị các thông tin khác nếu cần
            fullnametext.setText(admin.getFullname());
            emailtext.setText(admin.getEmail());
        } else {
            // Xử lý trường hợp không có ai đăng nhập vào
            usernametext.setText("N/A");
            System.err.println("Unexpected type for currentUser: " + currentUser.getClass().getName());
        }
    }

    public void editprofile(MouseEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/Controller/Profile/resource/Editprofile.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);

        stage.setOnHiding((WindowEvent windowEvent) -> {
            refreshprofile();
        });

        stage.show();
    }


    //Phan 3: Ket noi database -----------------------------------------------------------------------------------------

    public void refreshprofile()  {
        try {
            Object currentUser = ObjectCurrent.getObjectCurrent();
            con = DBController.getConnection();
            admindata = FXCollections.observableArrayList();
            st = con.prepareStatement("Select * From administrator");
            rs = st.executeQuery();
            while (rs.next()) {
                if (currentUser instanceof Administrator) {
                    Administrator admin = (Administrator) currentUser;
                    String username = admin.getUsername();
                    if(username.equals(rs.getString(1))) {
                        admindata.add(new Administrator(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                        // Hiển thị username trong label
                        usernametext.setText(rs.getString(1));
                        // Có thể thêm mã để hiển thị các thông tin khác nếu cần
                        fullnametext.setText(rs.getString(4));
                        emailtext.setText(rs.getString(3));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //Phan 4: Chuyen trang ---------------------------------------------------------------------------------------------
    public void switchhomescene(MouseEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/MainApplication/HomePage/HomePage.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void switchtocart(MouseEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/MainApplication/HomePage/SellHomePage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

}
