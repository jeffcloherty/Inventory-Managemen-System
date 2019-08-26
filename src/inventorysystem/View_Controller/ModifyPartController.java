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

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import inventorysystem.Model.InHouse;
import inventorysystem.Model.Outsourced;
import inventorysystem.Model.Part;
import static inventorysystem.View_Controller.MainInventoryController.getModifiedPart;

public class ModifyPartController implements Initializable {

    @FXML
    private Label modifyPartSourceLabel;

    @FXML
    private TextField modifyPartMax;

    @FXML
    private TextField modifyPartMin;

    @FXML
    private TextField modifyPartName;

    @FXML
    private TextField modifyPartSource;

    @FXML
    private TextField modifyPartPrice;

    @FXML
    private TextField modifyPartInventory;

    @FXML
    private TextField modifyPartID;

    @FXML
    private RadioButton modifyPartInhouse;

    @FXML
    private RadioButton modifyPartOutsourced;

    @FXML
    private Button modifyPartSave;
    
    @FXML
    private void updateInhouse(ActionEvent event){
        updateInhouse();
    }
    
    private void updateInhouse(){
        partIsOutsourced = false;
        modifyPartOutsourced.setSelected(false);
        modifyPartInhouse.setSelected(true);
        modifyPartSourceLabel.setText("Machine ID");
        modifyPartSource.setPromptText("Machine ID");
        partIsOutsourced = false;
    }
    
    @FXML
    private void updateOutsourced(ActionEvent event){
        updateOutsourced();
    }
    private void updateOutsourced(){
        partIsOutsourced = true;
        modifyPartInhouse.setSelected(false);
        modifyPartOutsourced.setSelected(true);
        modifyPartSourceLabel.setText("Company Name");
        modifyPartSource.setPromptText("Company Name");
        partIsOutsourced = true;
    }
    
    
    @FXML
    private void modifyPartReturnMain(ActionEvent event) throws IOException {
        
        //Validate Input
        if(validateInput()){  
            
            //Modify Part
            if(getModifiedPart() instanceof InHouse){
                getModifiedPart().modifyPart(tempPart.getPartID(),modifyPartName.getText(), Double.parseDouble(modifyPartPrice.getText()),
                        Integer.parseInt(modifyPartInventory.getText()), Integer.parseInt(modifyPartMin.getText()), Integer.parseInt(modifyPartMax.getText()));
                ((InHouse) getModifiedPart()).setMachine(Integer.parseInt(modifyPartSource.getText()));
            }else if(getModifiedPart() instanceof Outsourced){
                getModifiedPart().modifyPart(tempPart.getPartID(),modifyPartName.getText(), Double.parseDouble(modifyPartPrice.getText()),
                        Integer.parseInt(modifyPartInventory.getText()), Integer.parseInt(modifyPartMin.getText()), Integer.parseInt(modifyPartMax.getText()));
                ((Outsourced) getModifiedPart()).setCompanyName(modifyPartSource.getText());
            }
            
            //Change Scene
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
           double dPrice = Double.parseDouble(modifyPartPrice.getText());
       } catch (NumberFormatException | NullPointerException nfe){
           return false;
       }
        try{
           int iStock = Integer.parseInt(modifyPartInventory.getText());
       } catch (NumberFormatException | NullPointerException nfe){
           return false;
       }
        try{
           int iMin = Integer.parseInt(modifyPartMin.getText());
       } catch (NumberFormatException | NullPointerException nfe){
           return false;
       }
        try{
           int iMax = Integer.parseInt(modifyPartMax.getText());
       } catch (NumberFormatException | NullPointerException nfe){
           return false;
       }
        if(!partIsOutsourced){
        try{
           int iID = Integer.parseInt(modifyPartSource.getText());
       } catch (NumberFormatException | NullPointerException nfe){
           return false;
       }
        }
        return modifyPartName.getText() != null &&
                modifyPartSource.getText() != null &&
                Integer.parseInt(modifyPartMin.getText()) >= 0 &&
                Integer.parseInt(modifyPartMin.getText()) <= Integer.parseInt(modifyPartInventory.getText()) &&
                Integer.parseInt(modifyPartInventory.getText()) <= Integer.parseInt(modifyPartMax.getText()) &&
                Double.parseDouble(modifyPartPrice.getText()) >= 0;
    }
    
    private void allowModifyButton(){
        if(validateInput()){
            modifyPartSave.setDisable(false);
        }
        else{
            modifyPartSave.setDisable(true);
        }
    }
    
    private Part tempPart;
    private Boolean partIsOutsourced;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tempPart = getModifiedPart();
        
        if(tempPart instanceof InHouse){
            updateInhouse();
            modifyPartSource.setText(Integer.toString(((InHouse) tempPart).getMachineId()));
        }else if(tempPart instanceof Outsourced){
            updateOutsourced();
            modifyPartSource.setText(((Outsourced) tempPart).getCompanyName());
        }
        modifyPartID.setText(Integer.toString(tempPart.getPartID()));
        modifyPartName.setText(tempPart.getPartName());
        modifyPartInventory.setText(Integer.toString(tempPart.getPartStock()));
        modifyPartPrice.setText(Double.toString(tempPart.getPartPrice()));
        modifyPartMin.setText(Integer.toString(tempPart.getPartMin()));
        modifyPartMax.setText(Integer.toString(tempPart.getPartMax()));
        
        allowModifyButton();
        
        // Listeners to instantly update search results
        modifyPartName.textProperty().addListener((observable) -> {allowModifyButton();});
        modifyPartMin.textProperty().addListener((observable) -> {allowModifyButton();});
        modifyPartMax.textProperty().addListener((observable) -> {allowModifyButton();});
        modifyPartInventory.textProperty().addListener((observable) -> {allowModifyButton();});
        modifyPartPrice.textProperty().addListener((observable) -> {allowModifyButton();});
    }  
}
