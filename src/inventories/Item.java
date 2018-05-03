package inventories;

public class Item {

    private int damage, id, cost, weight;
    private String type, name;

    public Item(int id, String type, int damage, int cost, int weight, String name) {
        this.name = name;
        this.damage = damage;
        this.type = type;
        this.id = id;
        this.cost = cost;
        this.weight = weight;
    }

    public int getCost() {
        return cost;
    }

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
