package inventorysystem.View_Controller;

/**
 *
 * @author      Jeff Cloherty
 * @project     Inventory Management System

 * 
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import inventorysystem.Model.Inventory;
import static inventorysystem.Model.Inventory.addProduct;
import static inventorysystem.Model.Inventory.getAllParts;
import static inventorysystem.Model.Inventory.lookupPart;
import inventorysystem.Model.Part;
import inventorysystem.Model.Product;

public class AddProductController implements Initializable {

@FXML
    private TextField addProductMax;

    @FXML
    private TextField addProductMin;

    @FXML
    private TextField addProductName;

    @FXML
    private TextField addProductPrice;

    @FXML
    private TextField addProductInventory;

    @FXML
    private TableView<Part> addProductPartSearchTable;

    @FXML
    private TableColumn<Part, Integer> addProductPartSearchIDColumn;

    @FXML
    private TableColumn<Part, String> addProductPartSearchNameColumn;

    @FXML
    private TableColumn<Part, Integer> addProductPartSearchInventoryColumn;

    @FXML
    private TableColumn<Part, Double> addProductPartSearchPriceColumn;

    @FXML
    private TableView<Part> addProductAssPartsTable;

    @FXML
    private TableColumn<Part, Integer> addProductAssPartsTableIDColumn;

    @FXML
    private TableColumn<Part, String> addProductAssPartsTableNameColumn;

    @FXML
    private TableColumn<Part, Integer> addProductAssPartsTableInventoryColumn;

    @FXML
    private TableColumn<Part, Double> addProductAssPartsTablePriceColumn;

    @FXML
    private Button addProductSave;


    @FXML
    private void addProductAssPartsDelete(ActionEvent event){
        partsList.remove(addProductAssPartsTable.getSelectionModel().getSelectedItem());
        refreshAssPartsTable();
    }
    
    @FXML
    private void addProductPartSearch(ActionEvent event){
        addProductPartSearchTable.setItems(lookupPart(txtfldAddProductPartSearch.getText()));
    }
    
    @FXML
    private TextField txtfldAddProductPartSearch;

    @FXML
    private void addProductAssPartsAdd(ActionEvent event){
        partsList.add(addProductPartSearchTable.getSelectionModel().getSelectedItem());
        refreshAssPartsTable();
    }

    @FXML
    private void addProductReturnMain(ActionEvent event) throws IOException {
        
        if(validateInput()){
            addProduct(new Product(Inventory.getNextProductNumber(), addProductName.getText(), Double.parseDouble(addProductPrice.getText()),
                    Integer.parseInt(addProductInventory.getText()), Integer.parseInt(addProductMin.getText()), Integer.parseInt(addProductMax.getText()),partsList));
            
            //Change Scene
            Parent addProductParent = FXMLLoader.load(getClass().getResource("MainInventory.fxml"));
            Scene addProductScene = new Scene(addProductParent);
            Stage addProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            addProductStage.setScene(addProductScene);
            addProductStage.show();
        }
    } 
    
    @FXML
    private void cancelProductReturnMain(ActionEvent event) throws IOException {
        //Change Scene
        Parent addProductParent = FXMLLoader.load(getClass().getResource("MainInventory.fxml"));
        Scene addProductScene = new Scene(addProductParent);
        Stage addProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addProductStage.setScene(addProductScene);
        addProductStage.show();
    } 
    private void refreshPartsTable(){
        addProductPartSearchTable.setItems(getAllParts());
    }
    
    private void refreshAssPartsTable(){
        addProductAssPartsTable.setItems(partsList);
    }
    
    private final ObservableList<Part> partsList = FXCollections.observableArrayList();
    

    private boolean validateInput(){
        try{
           double dPrice = Double.parseDouble(addProductPrice.getText());
       } catch (NumberFormatException | NullPointerException nfe){
           return false;
       }
        try{
           int iStock = Integer.parseInt(addProductInventory.getText());
       } catch (NumberFormatException | NullPointerException nfe){
           return false;
       }
        try{
           int iMin = Integer.parseInt(addProductMin.getText());
       } catch (NumberFormatException | NullPointerException nfe){
           return false;
       }
        try{
           int iMax = Integer.parseInt(addProductMax.getText());
       } catch (NumberFormatException | NullPointerException nfe){
           return false;
       }
        return addProductName.getText() != null &&
            Integer.parseInt(addProductMin.getText()) >= 0 &&
            Integer.parseInt(addProductMin.getText()) <= Integer.parseInt(addProductInventory.getText()) &&
            Integer.parseInt(addProductInventory.getText()) <= Integer.parseInt(addProductMax.getText()) &&
            Double.parseDouble(addProductPrice.getText()) >= 0;
    }
    
    
    private void allowAddButton(){
        if(validateInput()){
            addProductSave.setDisable(false);
        }
        else{
            addProductSave.setDisable(true);
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        refreshPartsTable();
        addProductPartSearchIDColumn.setCellValueFactory(rowData -> rowData.getValue().partIdProperty());
        addProductPartSearchNameColumn.setCellValueFactory(rowData -> rowData.getValue().partNameProperty());
        addProductPartSearchInventoryColumn.setCellValueFactory(rowData -> rowData.getValue().partInventoryProperty());
        addProductPartSearchPriceColumn.setCellValueFactory(rowData -> rowData.getValue().partPriceProperty());
        
        refreshAssPartsTable();
        addProductAssPartsTableIDColumn.setCellValueFactory(rowData -> rowData.getValue().partIdProperty());
        addProductAssPartsTableNameColumn.setCellValueFactory(rowData -> rowData.getValue().partNameProperty());
        addProductAssPartsTableInventoryColumn.setCellValueFactory(rowData -> rowData.getValue().partInventoryProperty());
        addProductAssPartsTablePriceColumn.setCellValueFactory(rowData -> rowData.getValue().partPriceProperty());
        
        allowAddButton();
        
        // Listeners to instantly update search results
        addProductName.textProperty().addListener((observable) -> {allowAddButton();});
        addProductMin.textProperty().addListener((observable) -> {allowAddButton();});
        addProductMax.textProperty().addListener((observable) -> {allowAddButton();});
        addProductInventory.textProperty().addListener((observable) -> {allowAddButton();});
        addProductPrice.textProperty().addListener((observable) -> {allowAddButton();});
    }    
    
}
