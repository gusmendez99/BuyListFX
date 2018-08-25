package manager;

import buylist.BuyList;

import java.util.ArrayList;

public class BuyListManager {
    private static BuyListManager instance;
    private ArrayList<BuyList> buyLists = new ArrayList<>();

    protected BuyListManager() {
        addDummyData();
    } // Exists only to defeat instantiation.

    public static BuyListManager getInstance() {
        if(instance == null) {
            instance = new BuyListManager();
        }
        return instance;
    }

    private void addDummyData(){
        this.buyLists.add(new BuyList("ToDos", "Todos a realizar la otra semana"));
        this.buyLists.add(new BuyList("Amazon Wish List", "Todos a realizar la otra semana"));
    }

    public ArrayList<BuyList> getBuyLists() {
        return this.buyLists;
    }

    public void addBuyList(BuyList buyList){
        this.buyLists.add(buyList);
    }


}
