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

public abstract class Part {
    
    private final ObjectProperty<Integer> id;
    private final ObjectProperty<Integer> stock;
    private final ObjectProperty<Integer> min;
    private final ObjectProperty<Integer> max;
    private final SimpleStringProperty name;
    private final ObjectProperty<Double> price;
    
    public Part(int id, String name, double price, int stock, int min, int max){
        this.id = new SimpleObjectProperty<>(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleObjectProperty<>(price);
        this.stock = new SimpleObjectProperty<>(stock);
        this.min = new SimpleObjectProperty<>(min);
        this.max = new SimpleObjectProperty<>(max);        
    }
    
    public void modifyPart(int id, String name, double price, int stock, int min, int max) {
        this.id.set(id); 
        this.name.set(name); 
        this.price.set(price); 
        this.stock.set(stock);
        this.min.set(min);
        this.max.set(max);
    }
    
    public int getPartID(){
        return this.id.get();
    }    
    
    public String getPartName(){
        return this.name.get();
    }
    
    public double getPartPrice(){
        return this.price.get();
    }
    
    public int getPartStock(){
        return this.stock.get();
    }
    
    public int getPartMin(){
        return this.min.get();
    }
    
    public int getPartMax(){
        return this.max.get();
    }
    
    public ObjectProperty<Integer> partIdProperty(){
        return this.id;
    }
    
    public StringProperty partNameProperty(){
        return this.name;
    }
        
    public ObjectProperty<Double> partPriceProperty(){
        return this.price;
    }
            
    public ObjectProperty<Integer> partInventoryProperty(){
        return this.stock;
    }
}
  