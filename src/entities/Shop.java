package entities;

import java.util.HashMap;
import java.util.Map;

 public class Shop {

    private Map<SportEquipment, Integer> goods;

    private Shop(){
        this.goods = new HashMap<>();
    }
    // Указатель на экземпляр класса
    private static Shop instance = null;

    // Метод для получения экземпляра класса (реализован Singleton)
   public static Shop getInstance() throws ClassNotFoundException
    {
        if (instance == null)
        {
            instance = new Shop();
        }
        return instance;
    }

    public Map<SportEquipment, Integer> getGoods(){
        return goods;
    }
}
