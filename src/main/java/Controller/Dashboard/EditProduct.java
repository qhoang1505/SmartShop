package Controller.Dashboard;

import Controller.Database.test.DBController;
import Model.Thing.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.*;

public class EditProduct {
    @FXML
    private TextField cputext;

    @FXML
    private TextField displaytext;

    @FXML
    private TextField idtext;

    @FXML
    private TextField quantitytext; // int

    @FXML
    private TextField ramtext;

    @FXML
    private TextField storagetext;
    @FXML
    private Button editbutton;
    @FXML
    private TextField pricetext;

    public void EditproductAction(ActionEvent event) {

        //Chuyen doi textfield sang String----------------------------------------------
        String id = idtext.getText();
        String cpu = cputext.getText();
        String ram = ramtext.getText();
        String storage = storagetext.getText();
        String display = displaytext.getText();
        int quantity = Integer.parseInt(quantitytext.getText());
        String buttonsave = editbutton.getText();
        int price = Integer.parseInt(pricetext.getText());
        //---------------------------------------------------------------------------------

        if (buttonsave.equals("Save")) {

            Product product = new Product(id, cpu, ram, storage, display, quantity, price);

            try {
                update(product);
                Alert alertSuccess = new Alert(Alert.AlertType.CONFIRMATION);
                alertSuccess.setTitle("Add a new product");
                alertSuccess.setHeaderText("Added a product is: Successful!");
                alertSuccess.showAndWait();

            } catch (SQLException e) {
                e.printStackTrace();
                Alert alertFail = new Alert(Alert.AlertType.ERROR);
                alertFail.setTitle("Add a new product");
                alertFail.setHeaderText("Added a product is: Failed, please try again");
                alertFail.showAndWait();
            }
        }
    }

    public void update(Product product) throws SQLException {
            Connection con = DBController.getConnection();

            Statement st = con.createStatement();
            // thuc thi
            String sql = "UPDATE product SET " +
                    "CPU = '" + product.getCPU() + "', " +
                    "Ram = '" + product.getRam() + "', " +
                    "Storage = '" + product.getStorage() + "', " +
                    "Display = '" + product.getDisplay() + "', " +
                    "Quantity = '" + product.getQuantity() + "', " +
                    "Price = '" + product.getPrice() + "' " +
                    "WHERE ID = '" + product.getID() + "'";

            st.executeUpdate(sql);
    }
}
