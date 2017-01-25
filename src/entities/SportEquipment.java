package entities;

public class SportEquipment {

    private String category;
    private String title;
    private int price;

    public SportEquipment(String category, String title, int price){
        this.title =title;
        this.category=category;
        this.price = price;
    }
     public String getCategory(){
        return category;
    }
     public String getTitle(){
        return title;
    }
     public int getPrice(){
        return price;
    }
}
