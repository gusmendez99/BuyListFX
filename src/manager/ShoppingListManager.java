package manager;

import shopping_item.ShoppingItem;
import shopping_list.ShoppingList;

import java.util.ArrayList;

public class ShoppingListManager {
    private static ShoppingListManager instance;
    private ArrayList<ShoppingList> shoppingLists = new ArrayList<>();

    protected ShoppingListManager() {
        addDummyData();
    } // Exists only to defeat instantiation.

    public static ShoppingListManager getInstance() {
        if(instance == null) {
            instance = new ShoppingListManager();
        }
        return instance;
    }

    private void addDummyData(){
        ShoppingList list1 = new ShoppingList("ToDos", "Todos a realizar la otra semana");
        list1.addShoppingItem(new ShoppingItem("Manzanas", 2, 5.00));
        this.shoppingLists.add(list1);
        this.shoppingLists.add(new ShoppingList("Amazon Wish List", "Todos a realizar la otra semana"));
    }

    public ArrayList<ShoppingList> getShoppingLists() {
        return this.shoppingLists;
    }

    public ShoppingList getShoppingListByName(String name){
        return this.shoppingLists.stream()             // convert list to stream
                .filter(sl -> sl.getName().equals(name))
                .findAny()
                .orElse(null);

    }

    public boolean addShoppingList(String name, String description){
        if(getShoppingListByName(name) == null) { //It seem the shopping list exists
            this.shoppingLists.add(new ShoppingList(name, description));
            return true;
        }
        return false;
    }

    public void deleteShoppingList(ShoppingList shoppingList){
        shoppingLists.removeIf(x -> x == shoppingList);
    }


}
