package utilities;

import entities.Shop;
import entities.SportEquipment;

import java.io.*;
import java.util.ArrayList;


public class Reader {


   public File allEquipments;
   private Shop shop;

    public Reader(String[] args) {
        this.allEquipments = new File(args[0]);
    }

    //чтение данных о товарах их файла
    public ArrayList<String> readDataFile() {
        ArrayList<String> listStr = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(allEquipments)));
            String string;
            while ((string = reader.readLine()) != null) {
                listStr.add(string);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return listStr;
    }

    //парсинг текстовых данных, создание и инициализация экземпляра объекта класса Shop
    public Shop parseDataFile(ArrayList<String> listStr) {
        // Создание экземпляра магазина
        try {
            this.shop = Shop.getInstance();
        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        for (String aListStr : listStr) {
            String[] mass = aListStr.trim().split(" +");//парсим строку по пробелам
            shop.getGoods().put(new SportEquipment(mass[0], mass[1], Integer.parseInt(mass[2])),
                    Integer.parseInt(mass[3]));
        }
        return shop;
    }

}
