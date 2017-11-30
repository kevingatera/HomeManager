package besmart.team.homemanager.logic;

public class ShoppingItem {

    private String id;
    private String name;
    private String quantity;
     private String type;

     public ShoppingItem(){

     }

    public ShoppingItem(String id, String name, String quantity, String type){
         this.id = id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
