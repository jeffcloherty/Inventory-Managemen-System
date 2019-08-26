package inventorysystem;

/**
 *
 * @author      Jeff Cloherty
 * @project     Inventory Management System
 * 
 */

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import inventorysystem.Model.InHouse;
import inventorysystem.Model.Inventory;
import inventorysystem.Model.Outsourced;
import inventorysystem.Model.Product;

public class InventorySystem extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/inventorysystem/View_Controller/MainInventory.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
               
        primaryStage.setTitle("Inventory System");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Populate Product table with sample data
        Inventory.addProduct(new Product(Inventory.getNextProductNumber(), "Product 1", 5.00, 5, 1, 8,null));
        Inventory.addProduct(new Product(Inventory.getNextProductNumber(), "Product 2", 9.99, 7, 1, 8,null));
        Inventory.addProduct(new Product(Inventory.getNextProductNumber(), "Product 3", 15.00, 8, 1, 8,null));
        
        // Populate Parts table with sample data
        Inventory.addPart(new InHouse(Inventory.getNextPartNumber(), "Part 1", 12.4, 2, 1, 8, 9));
        Inventory.addPart(new Outsourced(Inventory.getNextPartNumber(), "Part 2", 1624, 5, 1, 8,"test1"));
        Inventory.addPart(new Outsourced(Inventory.getNextPartNumber(), "Part 3", 125.33, 8, 1, 8,"test2"));   
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    
    }
    
}
