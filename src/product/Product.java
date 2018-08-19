package product;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product {
    SimpleStringProperty name;
    SimpleIntegerProperty quantity;
    SimpleDoubleProperty price;
    SimpleBooleanProperty isComplete;

    public Product(String name, int quantity, double price){
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.isComplete = new SimpleBooleanProperty(false);
    }

    public String getName(){
        return this.name.get();
    }

    public void setName(String name){
        this.name.set(name);
    }

    public int getQuantity(){
        return this.quantity.get();
    }

    public void setQuantity(int quantity){
        this.quantity.set(quantity);
    }

    public Double getPrice(){
        return this.price.get();
    }

    public void setPrice(Double price){
        this.price.set(price);
    }

    public Boolean isComplete(){
        return this.isComplete.get();
    }

    public void setComplete(){
        this.isComplete.set(true);
    }

    public void setIncomplete(){
        this.isComplete.set(false);
    }
}
