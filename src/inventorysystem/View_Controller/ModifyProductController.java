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

import inventorysystem.Model.Part;
import inventorysystem.Model.Product;
import static inventorysystem.Model.Inventory.getAllParts;
import static inventorysystem.Model.Inventory.lookupPart;
import static inventorysystem.View_Controller.MainInventoryController.getModifiedProduct;

public class ModifyProductController implements Initializable {

    @FXML
    private TextField modifyProductID;
    
    @FXML
    private TextField modifyProductMax;

    @FXML
    private TextField modifyProductMin;

    @FXML
    private TextField modifyProductName;

    @FXML
    private TextField modifyProductPrice;

    @FXML
    private TextField modifyProductInventory;


    @FXML
    private TableView<Part> modifyProductPartSearchTable;

    @FXML
    private TableColumn<Part, Integer> modifyProductPartSearchIDColumn;

    @FXML
    private TableColumn<Part, String> modifyProductPartSearchNameColumn;

    @FXML
    private TableColumn<Part, Integer> modifyProductPartSearchInventoryColumn;

    @FXML
    private TableColumn<Part, Double> modifyProductPartSearchPriceColumn;

    @FXML
    private TableView<Part> modifyProductAssPartsTable;

    @FXML
    private TableColumn<Part, Integer> modifyProductAssPartsTableIDColumn;

    @FXML
    private TableColumn<Part, String> modifyProductAssPartsTableNameColumn;

    @FXML
    private TableColumn<Part, Integer> modifyProductAssPartsTableInventoryColumn;

    @FXML
    private TableColumn<Part, Double> modifyProductAssPartsTablePriceColumn;

    @FXML
    private Button modifyProductSave;


    @FXML
    private void modifyProductAssPartsDelete(ActionEvent event){
        partsList.remove(modifyProductAssPartsTable.getSelectionModel().getSelectedItem());
        refreshAssPartsTable();
    }
    
    @FXML
    private void modifyProductPartSearch(ActionEvent event){
        modifyProductPartSearchTable.setItems(lookupPart(txtfldAddProductPartSearch.getText()));
    }
    
    @FXML
    private TextField txtfldAddProductPartSearch;

    @FXML
    private void modifyProductAssPartsAdd(ActionEvent event){
        partsList.add(modifyProductPartSearchTable.getSelectionModel().getSelectedItem());
        refreshAssPartsTable();
    }

    @FXML
    private void modifyProductReturnMain(ActionEvent event) throws IOException {
        
        if(validateInput()){
            getModifiedProduct().modifyProduct(tempProduct.getProductID(), modifyProductName.getText(), Double.parseDouble(modifyProductPrice.getText()),
                    Integer.parseInt(modifyProductInventory.getText()), Integer.parseInt(modifyProductMin.getText()), Integer.parseInt(modifyProductMax.getText()),partsList);
            
            //Change Scene
            Parent modifyProductParent = FXMLLoader.load(getClass().getResource("MainInventory.fxml"));
            Scene modifyProductScene = new Scene(modifyProductParent);
            Stage modifyProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modifyProductStage.setScene(modifyProductScene);
            modifyProductStage.show();
        }
    } 
    
    @FXML
    private void cancelProductReturnMain(ActionEvent event) throws IOException {
        //Change Scene
        Parent modifyProductParent = FXMLLoader.load(getClass().getResource("MainInventory.fxml"));
        Scene modifyProductScene = new Scene(modifyProductParent);
        Stage modifyProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        modifyProductStage.setScene(modifyProductScene);
        modifyProductStage.show();
    } 
    private void refreshPartsTable(){
        modifyProductPartSearchTable.setItems(getAllParts());
    }
    
    private void refreshAssPartsTable(){
        modifyProductAssPartsTable.setItems(partsList);
    }
    
    private final ObservableList<Part> partsList = FXCollections.observableArrayList();
    

    private boolean validateInput(){
        try{
           double dPrice = Double.parseDouble(modifyProductPrice.getText());
       } catch (NumberFormatException | NullPointerException nfe){
           return false;
       }
        try{
           int iStock = Integer.parseInt(modifyProductInventory.getText());
       } catch (NumberFormatException | NullPointerException nfe){
           return false;
       }
        try{
           int iMin = Integer.parseInt(modifyProductMin.getText());
       } catch (NumberFormatException | NullPointerException nfe){
           return false;
       }
        try{
           int iMax = Integer.parseInt(modifyProductMax.getText());
       } catch (NumberFormatException | NullPointerException nfe){
           return false;
       }
        return modifyProductName.getText() != null &&
            Integer.parseInt(modifyProductMin.getText()) >= 0 &&
            Integer.parseInt(modifyProductMin.getText()) <= Integer.parseInt(modifyProductInventory.getText()) &&
            Integer.parseInt(modifyProductInventory.getText()) <= Integer.parseInt(modifyProductMax.getText()) &&
            Double.parseDouble(modifyProductPrice.getText()) >= 0;
    }
    
    
    private void allowAddButton(){
        if(validateInput()){
            modifyProductSave.setDisable(false);
        }
        else{
            modifyProductSave.setDisable(true);
        }
    }
    
    private Product tempProduct;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        tempProduct = getModifiedProduct();
        if(tempProduct.getAllAssociatedParts() != null){
            partsList.addAll(tempProduct.getAllAssociatedParts());
        }
        
        modifyProductID.setText(Integer.toString(tempProduct.getProductID()));

        modifyProductName.setText(tempProduct.getProductName());
        modifyProductInventory.setText(Integer.toString(tempProduct.getProductStock()));
        modifyProductPrice.setText(Double.toString(tempProduct.getProductPrice()));
        modifyProductMin.setText(Integer.toString(tempProduct.getProductMin()));
        modifyProductMax.setText(Integer.toString(tempProduct.getProductMax()));
        
        refreshPartsTable();
        modifyProductPartSearchIDColumn.setCellValueFactory(rowData -> rowData.getValue().partIdProperty());
        modifyProductPartSearchNameColumn.setCellValueFactory(rowData -> rowData.getValue().partNameProperty());
        modifyProductPartSearchInventoryColumn.setCellValueFactory(rowData -> rowData.getValue().partInventoryProperty());
        modifyProductPartSearchPriceColumn.setCellValueFactory(rowData -> rowData.getValue().partPriceProperty());
        
        refreshAssPartsTable();
        modifyProductAssPartsTableIDColumn.setCellValueFactory(rowData -> rowData.getValue().partIdProperty());
        modifyProductAssPartsTableNameColumn.setCellValueFactory(rowData -> rowData.getValue().partNameProperty());
        modifyProductAssPartsTableInventoryColumn.setCellValueFactory(rowData -> rowData.getValue().partInventoryProperty());
        modifyProductAssPartsTablePriceColumn.setCellValueFactory(rowData -> rowData.getValue().partPriceProperty());
        
        allowAddButton();
        
        // Listeners to instantly update search results
        modifyProductName.textProperty().addListener((observable) -> {allowAddButton();});
        modifyProductMin.textProperty().addListener((observable) -> {allowAddButton();});
        modifyProductMax.textProperty().addListener((observable) -> {allowAddButton();});
        modifyProductInventory.textProperty().addListener((observable) -> {allowAddButton();});
        modifyProductPrice.textProperty().addListener((observable) -> {allowAddButton();});
    }    
    
}
