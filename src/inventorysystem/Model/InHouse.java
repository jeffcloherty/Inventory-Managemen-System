package inventorysystem.Model;

/**
 *
 * @author      Jeff Cloherty
 * @project     Inventory Management System
 * 
 */

import javafx.beans.property.SimpleObjectProperty;

public class InHouse extends Part {
    private final SimpleObjectProperty<Integer> machineId;

    //Constructor
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = new SimpleObjectProperty<>(machineId);
    }
    
    public void setMachine(int machineId){
        this.machineId.set(machineId);
    }
    
    public int getMachineId(){
        return this.machineId.get();
    }
   
}
