package inventorysystem.Model;

/**
 *
 * @author      Jeff Cloherty
 * @project     Inventory Management System
 * 
 */

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product{
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private final ObjectProperty<Integer> id;
    private final ObjectProperty<Integer> stock;
    private final ObjectProperty<Integer> min;
    private final ObjectProperty<Integer> max;
    private final SimpleStringProperty name;
    private final ObjectProperty<Double> price;

    public Product(int id, String name, double price, int stock, int min, int max,ObservableList<Part> associatedParts){
        this.id = new SimpleObjectProperty<>(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleObjectProperty<>(price);
        this.stock = new SimpleObjectProperty<>(stock);
        this.min = new SimpleObjectProperty<>(min);
        this.max = new SimpleObjectProperty<>(max);
        if(associatedParts != null){
            this.associatedParts.addAll(associatedParts);
        }
    }

    public void modifyProduct(int id, String name, double price, int stock, int min, int max,ObservableList<Part> associatedParts){
        this.id.set(id); 
        this.name.set(name); 
        this.price.set(price); 
        this.stock.set(stock);
        this.min.set(min);
        this.max.set(max);
        this.associatedParts = FXCollections.observableArrayList();
        if(associatedParts != null){
            this.associatedParts.addAll(associatedParts);
        }
    }
    
    public int getProductID(){
        return this.id.get();
    }    
    
    public String getProductName(){
        return this.name.get();
    }
    
    public double getProductPrice(){
        return this.price.get();
    }
    
    public int getProductStock(){
        return this.stock.get();
    }
    
    public int getProductMin(){
        return this.min.get();
    }
    
    public int getProductMax(){
        return this.max.get();
    }
    
    public ObjectProperty<Integer> productIdProperty(){
        return id;
    }
    
    public StringProperty productNameProperty(){
        return name;
    }
        
    public ObjectProperty<Double> productPriceProperty(){
        return price;
    }
            
    public ObjectProperty<Integer> productInventoryProperty(){
        return stock;
    }
        
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
