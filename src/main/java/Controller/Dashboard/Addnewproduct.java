package Controller.Dashboard;

import Controller.Database.test.DBController;
import Model.Person.Administrator;
import Model.Person.ObjectCurrent;
import Model.Thing.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.*;

public class Addnewproduct {
    @FXML
    private TextField categorytext;

    @FXML
    private TextField cputext;

    @FXML
    private TextField displaytext;

    @FXML
    private TextField idtext;

    @FXML
    private TextField nametext;

    @FXML
    private TextField quantitytext; // int

    @FXML
    private TextField ramtext;

    @FXML
    private TextField storagetext;
    @FXML
    private TextField pricetext;

    @FXML
    private TextField yeartext;

    // Trung tam xu li su kien --------------------------------------------------------

    public void addnewproductAction(ActionEvent event) {
        //Chuyen doi textfield sang String---------------------------------------------
        String id = idtext.getText();
        String category = categorytext.getText();
        String name = nametext.getText();
        String cpu = cputext.getText();
        String ram = ramtext.getText();
        String storage = storagetext.getText();
        String display = displaytext.getText();
        int year = Integer.parseInt(yeartext.getText());
        int quantity = Integer.parseInt(quantitytext.getText());
        int price = Integer.parseInt(pricetext.getText());
        //---------------------------------------------------------------------------------
        String username = null;
        Object currentUser = ObjectCurrent.getObjectCurrent();
        if (currentUser instanceof Administrator) {
            Administrator admin = (Administrator) currentUser;
            username = admin.getUsername();
        }
        Product product = new Product(id, category, name, cpu, ram, storage, display, year, quantity, price, username);
        try {
            insert(product);
            Alert alertSuccess = new Alert(Alert.AlertType.CONFIRMATION);
            alertSuccess.setTitle("Add a new product");
            alertSuccess.setHeaderText("Added a product is: " + name + " Successful!");
            alertSuccess.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alertFail = new Alert(Alert.AlertType.ERROR);
            alertFail.setTitle("Add a new product");
            alertFail.setHeaderText("Added a product is: " + name + " Failed, please try again");
            alertFail.showAndWait();
        }
    }
    

    //Database-------------------------------------------------------------------------
    public void insert(Product product) throws SQLException {
            Connection con = DBController.getConnection();
            // Tao doi tuong statement
            Statement st = con.createStatement();
            // thuc thi
            String sql = "INSERT INTO product(ID, Category, Name, CPU, Ram, Storage, Display, Year, Quantity, Price, Seller)" +
                    " VALUES ('" + product.getID() + "', '" + product.getCategory() + "', '" + product.getName() + "', " +
                    "'" + product.getCPU() + "', '" + product.getRam() + "', '" + product.getStorage() + "', " +
                    "'" + product.getDisplay() + "', '" + product.getYear() + "', " +
                    "'" + product.getQuantity() + "', '" + product.getPrice() + "', '" + product.getSeller() + "')";

            //Kiem tra da chay hay chua
            st.executeUpdate(sql);
    }
}
