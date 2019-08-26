package inventorysystem.Model;

/**
 *
 * @author      Jeff Cloherty
 * @studentID   #001136766
 * @project     Inventory Management System
 * @class       C482 - Software I
 * 
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    
    private static final  ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final  ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    private static int nextPartNumber;
    private static int nextProductNumber;
    
    public static int getNextPartNumber(){
        return ++nextPartNumber;
    }
    
    public static int getNextProductNumber(){
        return ++nextProductNumber;
    }
    
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        allParts.stream().filter((allPart) -> (allPart.getPartName().toLowerCase().contains(partName.toLowerCase())
                || Integer.toString(allPart.getPartID()).equals(partName))).forEachOrdered((allPart) -> {
                    foundParts.add(allPart);
        });
        return foundParts;
    }
    
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        allProducts.stream().filter((allProduct) -> (allProduct.getProductName().toLowerCase().contains(productName.toLowerCase())
                || Integer.toString(allProduct.getProductID()).contains(productName))).forEachOrdered((allProduct) -> {
                    foundProducts.add(allProduct);
        });
        return foundProducts;
    }
    
    public static void updatePart(int index, Part selectedPart){
       allParts.set(index, selectedPart);
    }
    
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }
    
    public static void deletePart(Part selectedPart){
        allParts.remove(selectedPart);
    }
    
    public static void deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
    }
    
    public static ObservableList<Part> getAllParts(){
        return allParts;
        
    }
    
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
        
    }
    
}
