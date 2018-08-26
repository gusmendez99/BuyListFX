package shopping_list;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import shopping_item.ShoppingItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingList extends RecursiveTreeObject<ShoppingList> {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    SimpleStringProperty name;
    SimpleStringProperty description;
    SimpleObjectProperty<String> date;
    private ArrayList<ShoppingItem> shoppingItems;


    public ShoppingList(String name, String description){
        this.description = new SimpleStringProperty(description);
        this.name = new SimpleStringProperty(name);
        this.shoppingItems = new ArrayList<>();
        this.date = new SimpleObjectProperty<String>(sdf.format(new Date()));
    }

    public String getName() {
        return this.name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleObjectProperty dateProperty() {
        return date;
    }

    public SimpleIntegerProperty pendingProductsProperty(){
        return new SimpleIntegerProperty(getPendingShoppingItems().size());
    }

    public SimpleDoubleProperty estimatePendingProperty(){
        return new SimpleDoubleProperty(getEstimatePendingTotal());
    }

    public List<ShoppingItem> getPendingShoppingItems(){
        return this.shoppingItems.stream()             // convert list to stream
                .filter(p -> !p.isComplete())     // we dont like not pendings
                .collect(Collectors.toList());
    }

    public List<ShoppingItem> getAllShoppingItems(){
        return this.shoppingItems;
    }

    public double getEstimatePendingTotal(){
        return this.shoppingItems.stream()             // convert list to stream
                .filter(p -> !p.isComplete())
                .mapToDouble( p -> p.getPrice() * p.getQuantity())
                .sum();
    }

    public void addShoppingItem(ShoppingItem shoppingItem) {
        this.shoppingItems.add(shoppingItem);
    }

}
