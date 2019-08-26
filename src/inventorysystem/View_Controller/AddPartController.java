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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import inventorysystem.Model.Outsourced;
import inventorysystem.Model.Part;
import inventorysystem.Model.InHouse;
import inventorysystem.Model.Inventory;

public class AddPartController implements Initializable {

    @FXML
    private Label addPartSourceLabel;

    @FXML
    private TextField addPartMax;

    @FXML
    private TextField addPartMin;

    @FXML
    private TextField addPartName;

    @FXML
    private TextField addPartSource;

    @FXML
    private TextField addPartPrice;

    @FXML
    private TextField addPartInventory;

    @FXML
    private RadioButton addPartInhouse;

    @FXML
    private RadioButton addPartOutsourced;

    @FXML
    private Button addPartSave;


    @FXML
    private void updateInhouse(ActionEvent event){
        updateInhouse();
    }
    private void updateInhouse(){
        partIsOutsourced = false;
        addPartOutsourced.setSelected(false);
        addPartInhouse.setSelected(true);
        addPartSourceLabel.setText("Machine ID");
        addPartSource.setPromptText("Machine ID");
        partIsOutsourced = false;
    }
    
    @FXML
    private void updateOutsourced(ActionEvent event){
        updateOutsourced();
    }
    
    private void updateOutsourced(){
        partIsOutsourced = true;
        addPartInhouse.setSelected(false);
        addPartOutsourced.setSelected(true);
        addPartSourceLabel.setText("Company Name");
        addPartSource.setPromptText("Company Name");
        partIsOutsourced = true;
    }
    
    @FXML
    private void addPartReturnMain(ActionEvent event) throws IOException {
        
      if(validateInput()){             
        if(partIsOutsourced){
            newPart = new Outsourced(Inventory.getNextPartNumber(), addPartName.getText(), Double.parseDouble(addPartPrice.getText()),
                    Integer.parseInt(addPartInventory.getText()), Integer.parseInt(addPartMin.getText()), Integer.parseInt(addPartMax.getText()), addPartSource.getText());
            }else{
            newPart = new InHouse(Inventory.getNextPartNumber(), addPartName.getText(), Double.parseDouble(addPartPrice.getText()),
                    Integer.parseInt(addPartInventory.getText()), Integer.parseInt(addPartMin.getText()), Integer.parseInt(addPartMax.getText()), Integer.parseInt(addPartSource.getText()));
            }

        //Change Scene
        Inventory.addPart(newPart);
        Parent addProductParent = FXMLLoader.load(getClass().getResource("MainInventory.fxml"));
        Scene addProductScene = new Scene(addProductParent);
        Stage addProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addProductStage.setScene(addProductScene);
        addProductStage.show();
        } 
    }
      
    
    @FXML
    private void cancelPartReturnMain(ActionEvent event) throws IOException {
        //Change Scene
        Parent addProductParent = FXMLLoader.load(getClass().getResource("MainInventory.fxml"));
        Scene addProductScene = new Scene(addProductParent);
        Stage addProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addProductStage.setScene(addProductScene);
        addProductStage.show();
    } 
    private boolean validateInput(){
        try{
           double dPrice = Double.parseDouble(addPartPrice.getText());
       } catch (NumberFormatException | NullPointerException nfe){
           return false;
       }
        try{
           int iStock = Integer.parseInt(addPartInventory.getText());
       } catch (NumberFormatException | NullPointerException nfe){
           return false;
       }
        try{
           int iMin = Integer.parseInt(addPartMin.getText());
       } catch (NumberFormatException | NullPointerException nfe){
           return false;
       }
        try{
           int iMax = Integer.parseInt(addPartMax.getText());
       } catch (NumberFormatException | NullPointerException nfe){
           return false;
       }
        if(!partIsOutsourced){
        try{
           int iID = Integer.parseInt(addPartSource.getText());
       } catch (NumberFormatException | NullPointerException nfe){
           return false;
       }
        }
        return addPartName.getText() != null &&
                addPartSource.getText() != null &&
                Integer.parseInt(addPartMin.getText()) >= 0 &&
                Integer.parseInt(addPartMin.getText()) <= Integer.parseInt(addPartInventory.getText()) &&
                Integer.parseInt(addPartInventory.getText()) <= Integer.parseInt(addPartMax.getText()) &&
                Double.parseDouble(addPartPrice.getText()) >= 0;
    }
    
    
    private void allowAddButton(){
        if(validateInput()){
            addPartSave.setDisable(false);
        }
        else{
            addPartSave.setDisable(true);
        }
    }
    private Part newPart;
    private Boolean partIsOutsourced = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateInhouse();
        allowAddButton();
        
        // Listeners to instantly update search results
        addPartName.textProperty().addListener((observable) -> {allowAddButton();});
        addPartMin.textProperty().addListener((observable) -> {allowAddButton();});
        addPartMax.textProperty().addListener((observable) -> {allowAddButton();});
        addPartInventory.textProperty().addListener((observable) -> {allowAddButton();});
        addPartPrice.textProperty().addListener((observable) -> {allowAddButton();});
    }  
}
