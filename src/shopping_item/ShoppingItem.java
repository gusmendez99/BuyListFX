package shopping_item;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ShoppingItem extends RecursiveTreeObject<ShoppingItem> {
    SimpleStringProperty name;
    SimpleIntegerProperty quantity;
    SimpleDoubleProperty price;
    SimpleBooleanProperty isComplete;

    public ShoppingItem(String name, int quantity, double price){
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.isComplete = new SimpleBooleanProperty(false);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public SimpleDoubleProperty totalProperty(){
        double total = (price.get() * quantity.get());
        return new SimpleDoubleProperty(total);
    }

    public SimpleBooleanProperty isCompleteProperty() {
        return isComplete;
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

    public boolean isComplete(){
        return this.isComplete.get();
    }

    public void setComplete(){
        this.isComplete.set(true);
    }

    public void setIncomplete(){
        this.isComplete.set(false);
    }
}
