package buylist;

import javafx.beans.property.SimpleStringProperty;
import product.Product;

import java.util.ArrayList;

public class BuyList {
    SimpleStringProperty name;
    private ArrayList products;

    public BuyList(String name){
        this.name = new SimpleStringProperty(name);
        this.products = new ArrayList<>();
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ArrayList getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }
}
