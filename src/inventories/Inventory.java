package inventories;

import ai.NPCs.Merchant;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<Item> items;
    private InventoryReader reader;

    public Inventory() {
        items = new ArrayList<>();
        reader = new InventoryReader("Merchant1_Inventory");
        items = reader.items();
    }

    public void setInventoryLocation(String fileName){
        reader = new InventoryReader(fileName);
        items = reader.items();
    }

    public List<Item> getItems() {
        return items;
    }

    public Item getItem(Item item){
        for(Item i: items){
            if(i == item){
                items.remove(item);
                return item;
            }
        }
        return null;
    }

    public void addItem(Item item){
        items.add(item);
    }
}
