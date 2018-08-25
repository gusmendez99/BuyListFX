package manager;

import buylist.ShoppingList;

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
        this.shoppingLists.add(new ShoppingList("ToDos", "Todos a realizar la otra semana"));
        this.shoppingLists.add(new ShoppingList("Amazon Wish List", "Todos a realizar la otra semana"));
    }

    public ArrayList<ShoppingList> getShoppingLists() {
        return this.shoppingLists;
    }

    public void addBuyList(ShoppingList shoppingList){
        this.shoppingLists.add(shoppingList);
    }

    public void deleteBuyList(ShoppingList shoppingList){
        shoppingLists.removeIf(x -> x == shoppingList);
    }


}
