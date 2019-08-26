package inventorysystem.View_Controller;

/**
 *
 * @author      Jeff Cloherty
 * @project     Inventory Management System
 * 
 */

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import inventorysystem.Model.Part;
import inventorysystem.Model.Product;
import static inventorysystem.Model.Inventory.deletePart;
import static inventorysystem.Model.Inventory.deleteProduct;
import static inventorysystem.Model.Inventory.lookupPart;
import static inventorysystem.Model.Inventory.lookupProduct;
import static inventorysystem.Model.Inventory.getAllParts;
import static inventorysystem.Model.Inventory.getAllProducts;

public class MainInventoryController implements Initializable{

    @FXML
    private TableView<Part> mainPartTable;

    @FXML
    private TableColumn<Part, Integer> mainPartIdColumn;

    @FXML
    private TableColumn<Part, String> mainPartNameColumn;

    @FXML
    private TableColumn<Part, Integer> mainPartInventoryColumn;

    @FXML
    private TableColumn<Part, Double> mainPartPriceColumn;
    
    @FXML
    private TableView<Product> mainProductTable;

    @FXML
    private TableColumn<Product, Integer> mainProductIdColumn;

    @FXML
    private TableColumn<Product, String> mainProductNameColumn;

    @FXML
    private TableColumn<Product, Integer> mainProductInventoryColumn;

    @FXML
    private TableColumn<Product, Double> mainProductPriceColumn;
    
    @FXML 
    private void btnMainDeletePart(ActionEvent event){
        deletePart(mainPartTable.getSelectionModel().getSelectedItem());
        refreshPartsTable();
    }

    @FXML 
    private void btnMainExit(ActionEvent event){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setContentText("Do you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            System.exit(0);
        } 
        else {
            System.out.println("Exit cancelled");
        }
    }

    @FXML
    private void btnMainSearchPart(ActionEvent event){
        mainSearchPart();
    }
    
    private void mainSearchPart(){
        mainPartTable.setItems(lookupPart(txtfldMainSearchPart.getText()));
    }
    
    @FXML
    private TextField txtfldMainSearchPart;

    @FXML 
    private void btnMainDeleteProduct(ActionEvent event){
        deleteProduct(mainProductTable.getSelectionModel().getSelectedItem());
        refreshProductsTable();
    }

    @FXML
    private TextField txtfldMainSearchProduct;

    @FXML
    private void btnMainSearchProduct(ActionEvent event){
        mainSearchProduct();
    }

    private void mainSearchProduct(){
        mainProductTable.setItems(lookupProduct(txtfldMainSearchProduct.getText()));
    }
    
    @FXML
    private void openAddPartScreen(ActionEvent event) throws IOException {
        Parent addProductParent = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene addProductScene = new Scene(addProductParent);
        Stage addProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addProductStage.setScene(addProductScene);
        addProductStage.show();
    }    
    
    @FXML
    private void openModifyPartScreen(ActionEvent event) throws IOException {
        if(mainPartTable.getSelectionModel().getSelectedItem() != null){
            setModifiedPart(mainPartTable.getSelectionModel().getSelectedItem());
        Parent addProductParent = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
        Scene addProductScene = new Scene(addProductParent);
        Stage addProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addProductStage.setScene(addProductScene);
        addProductStage.show();
    }
    }  
    
    @FXML
    private void openAddProductScreen(ActionEvent event) throws IOException {
        //Change Scene
        Parent addProductParent = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene addProductScene = new Scene(addProductParent);
        Stage addProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addProductStage.setScene(addProductScene);
        addProductStage.show();
    }
    
    @FXML
    private void openModifyProductScreen(ActionEvent event) throws IOException {
        if(mainProductTable.getSelectionModel().getSelectedItem() != null){
            setModifiedProduct(mainProductTable.getSelectionModel().getSelectedItem());
        
        //Change Scene
        Parent addProductParent = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
        Scene addProductScene = new Scene(addProductParent);
        Stage addProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addProductStage.setScene(addProductScene);
        addProductStage.show();
    }
    }
    
    // Create temporary objects to be modified
    
    private static Part modifiedPart;
    private static Product modifiedProduct;

    // Modified object setters and getters
    
    public static Part getModifiedPart(){
        return modifiedPart;
    }
    
    public static void setModifiedPart(Part part){
        modifiedPart = part;
    }
    
    public static Product getModifiedProduct(){
        return modifiedProduct;
    }
    
    public static void setModifiedProduct(Product product){
        modifiedProduct = product;
    }   
    
    private void refreshProductsTable(){
        mainProductTable.setItems(getAllProducts());
    }

    private void refreshPartsTable(){
        mainPartTable.setItems(getAllParts());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

        // Set values for Products tableview 
        refreshProductsTable();
        mainProductIdColumn.setCellValueFactory(rowData -> rowData.getValue().productIdProperty());
        mainProductNameColumn.setCellValueFactory(rowData -> rowData.getValue().productNameProperty());
        mainProductInventoryColumn.setCellValueFactory(rowData -> rowData.getValue().productInventoryProperty());
        mainProductPriceColumn.setCellValueFactory(rowData -> rowData.getValue().productPriceProperty());
        
        // Set values for Parts tableview
        refreshPartsTable();
        mainPartIdColumn.setCellValueFactory(rowData -> rowData.getValue().partIdProperty());
        mainPartNameColumn.setCellValueFactory(rowData -> rowData.getValue().partNameProperty());
        mainPartInventoryColumn.setCellValueFactory(rowData -> rowData.getValue().partInventoryProperty());
        mainPartPriceColumn.setCellValueFactory(rowData -> rowData.getValue().partPriceProperty());
        
        // Listeners to instantly update search results
        txtfldMainSearchPart.textProperty().addListener((observable) -> {mainSearchPart();});
        txtfldMainSearchProduct.textProperty().addListener((observable) -> {mainSearchProduct();});
    }
}
