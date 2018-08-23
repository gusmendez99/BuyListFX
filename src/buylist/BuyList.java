package buylist;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import product.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BuyList extends RecursiveTreeObject<BuyList> {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    SimpleStringProperty name;
    SimpleObjectProperty<String> date;
    private ArrayList<Product> products;


    public BuyList(String name){
        this.name = new SimpleStringProperty(name);
        this.products = new ArrayList<>();
        this.date = new SimpleObjectProperty<String>(sdf.format(new Date()));
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleObjectProperty dateProperty() {
        return date;
    }

    public SimpleIntegerProperty pendingProductsProperty(){
        return new SimpleIntegerProperty(getPendingProducts().size());
    }

    public SimpleDoubleProperty estimatePendingProperty(){
        return new SimpleDoubleProperty(getEstimatePendingTotal());
    }

    public List<Product> getPendingProducts(){
        return this.products.stream()             // convert list to stream
                .filter(p -> !p.isComplete())     // we dont like not pendings
                .collect(Collectors.toList());
    }

    public double getEstimatePendingTotal(){
        return this.products.stream()             // convert list to stream
                .filter(p -> !p.isComplete())
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

}
