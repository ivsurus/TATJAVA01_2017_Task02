package main;

import entities.RentUnit;
import entities.Shop;
import entities.SportEquipment;
import utilities.Reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {


    private final static String HELLO_MESSAGE = "Вас приветствует магазин проката спортивного инвентаря!";
    private final static String GOODBYE_MESSAGE = "Good Bye!";
    private final static String MENU = "Введите цифру для выбора действия:\n"+
                                         "1 - Вывести доступные в магазине товары\n" +
                                         "2 - Вывести товары в прокате у пользователя\n"+
                                          "3 - Поиск товара в магазине\n"+
                                           "4 - Взять товар\n"+
                                            "5 - Вернуть товар\n"+
                                             "0 - Выход из программы";
    private final static String MENU_MESSAGE_1 = "Введите категорию товара:";
    private final static String MENU_MESSAGE_2 = "Введите название товара:";
    private final static String MENU_MESSAGE_3 = "Товар в наличии\n";
    private final static String MENU_MESSAGE_4 = "Товар отсутствует\n";
    private final static String MENU_MESSAGE_5 = "Вы не можете взять более 3 товаров\n";
    private final static String MENU_MESSAGE_6 = "Вы не брали данный товар\n";
    private final static String MENU_MESSAGE_7 = "Товар добавлен пользователю\n";
    private final static String MENU_MESSAGE_8 = "Товар возвращен в магазин\n";
    private final static String MENU_MESSAGE_9 = "Доступны в магазине:";
    private final static String MENU_MESSAGE_10 = "Отданы в прокат:";
    private final static String USAGE_MESSAGE = "USAGE: To start the application you should specify correct" +
                                                " path to the file with sports equipment catalog as an argument";
    private final static String EXAMPLE_MESSAGE = "EXAMPLE: D:\\shop\\data.txt";
    private final static String WARNING_MESSAGE1 = "Файла с данными не существует";
    private final static String WARNING_MESSAGE2 = "Файл с данными пустой";



   private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {

        if (args.length >= 1) {   //проверка указания пути к файлу с данными в качестве аргумета
            Main main = new Main();
            //создание объекта для чтения данных из файла
            Reader myReader = new Reader(args);
            boolean dataIsOk = main.checkDataFile(myReader);
            if (dataIsOk) { //проверка, существует ли файл с данными, файл с данными не должен быть пустым
                System.out.println(HELLO_MESSAGE);
                //инициализация объекта класса Shop данными из файла
                Shop shop = myReader.parseDataFile(myReader.readDataFile());
                //создание объекта для хранения данных о товарах, которые пользователь взял в прокат
                RentUnit unit = new RentUnit();
                //запуск консольного меню
                main.consoleMenuNew(shop, unit);
                System.out.println(GOODBYE_MESSAGE);
            }
        } else {
            System.out.println(USAGE_MESSAGE + "\n" + EXAMPLE_MESSAGE);
        }
    }

    //проверка, существует ли файл с данными, файл с данными не должен быть пустым
    private boolean checkDataFile(Reader myReader){
        boolean flag = true;
        if (!myReader.allEquipments.exists()) {
                    System.out.println(WARNING_MESSAGE1);
                    flag = false;
                } else if (myReader.allEquipments.length() == 0) {
                    System.out.println(WARNING_MESSAGE2);
                    flag = false;
                }
        return flag;
    }

    //консольное меню
    private void consoleMenuNew(Shop shop, RentUnit unit){
        String menuChoice;
        do
            {
                System.out.println(MENU);
                menuChoice = readMenuChoice();
                switch (menuChoice)
                {
                    case "1":
                        case1Perform(shop);
                        break;
                    case "2":
                        case2Perform(unit);
                        break;
                    case "3":
                        case3Perform(shop);
                        break;
                    case "4":
                        case4Perform(shop , unit);
                        break;
                    case "5":
                        case5Perform(shop, unit);
                        break;
                    default:
                        break;
                }
            } while (!menuChoice.equals("0"));
        }
    //поиск товара в магазине
    private void case3Perform(Shop shop){
            String[] userAnswer = readUserEquipment();
            boolean equipmentAvailable = checkIfEquipmentIsAvailable(shop, userAnswer[0],userAnswer[1]);
            System.out.println(equipmentAvailable? MENU_MESSAGE_3 : MENU_MESSAGE_4);
    }
    //взять товар
    private void case4Perform(Shop shop, RentUnit unit){
        if (unit.getUnits().size() < 3){
            String[] userAnswer = readUserEquipment();
            boolean equipmentAvailable = checkIfEquipmentIsAvailable(shop, userAnswer[0],userAnswer[1]);
            if (equipmentAvailable) {
                takeEquipment(shop, unit, userAnswer[0], userAnswer[1]);
            } else {
                System.out.println(MENU_MESSAGE_4);
            }
        } else {
            System.out.println(MENU_MESSAGE_5);
        }
    }
    //вернуть товар
    private void case5Perform(Shop shop, RentUnit unit){
        String[] userAnswer = readUserEquipment();
        boolean equipmentRented = checkIfEquipmentIsRented(unit,userAnswer[0],userAnswer[1]);
        if (equipmentRented){
            returnEquipment(shop,unit,userAnswer[0],userAnswer[1]);
        } else {
            System.out.println(MENU_MESSAGE_6);
        }
    }
    //чтение категории и названия выбранного пользователем товара с консоли
    private String[] readUserEquipment(){
        String[] answer = new String[2];
        try {
            System.out.println(MENU_MESSAGE_1);
            answer[0] = reader.readLine();
            System.out.println(MENU_MESSAGE_2);
            answer[1] = reader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return answer;
    }
    //чтение выбранного пользователем пункта консольного меню
    private String readMenuChoice(){
        String answer = "";
        try {
            answer = reader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return answer;
    }
    //вернуть товар
    private void returnEquipment(Shop shop, RentUnit unit, String userCategory, String userTitle){
        for (HashMap.Entry<SportEquipment, Integer> entry : shop.getGoods().entrySet()) {
            if ((entry.getKey().getTitle().equals(userTitle)) && ((entry.getKey().getCategory().equals(userCategory)))){
                // увеличиваем количество товара в магазине на 1
                shop.getGoods().put(entry.getKey(), entry.getValue() + 1);
                //удаляем товар у пользователя
                unit.removeUnits(entry.getKey());
                System.out.println(MENU_MESSAGE_8);
            }
        }
    }

    // взять товар
    private void takeEquipment(Shop shop, RentUnit unit, String userCategory, String userTitle){
        for (HashMap.Entry<SportEquipment, Integer> entry : shop.getGoods().entrySet()) {
            if ((entry.getKey().getTitle().equals(userTitle)) && ((entry.getKey().getCategory().equals(userCategory)))){
                // уменьшаем количество товара в магазине на 1
                shop.getGoods().put(entry.getKey(), entry.getValue() - 1);
                //добавляем товар пользователю
                unit.setUnits(entry.getKey());
                System.out.println(MENU_MESSAGE_7);
            }
        }
    }
    // проверка, брал ли пользователь данный товар
    private boolean checkIfEquipmentIsRented(RentUnit unit, String userCategory, String userTitle){
        boolean flag = false;
        if (unit.getUnits().size()>0) { //проверка наличия хотя бы одного товара у пользователя
            for (SportEquipment equipment : unit.getUnits()) {
                if ((equipment.getCategory().equals(userCategory)) && (equipment.getTitle().equals(userTitle))){
                    flag = true;
                }
            }
        }
        return flag;
    }
    //проверка доступности товара в магазине по наименованию и по количеству
    private boolean checkIfEquipmentIsAvailable(Shop shop, String userCategory, String userTitle){
        boolean flag = false;
        for (HashMap.Entry<SportEquipment, Integer> entry : shop.getGoods().entrySet()) {
            if ((entry.getKey().getCategory().equals(userCategory)) && ((entry.getKey().getTitle().equals(userTitle)))
                    && ((entry.getValue() > 0))) {
                flag = true;
            }
        }
        return flag;
    }
    // отчет о товарах которые находятся в магазине
    private void case1Perform(Shop shop){
        System.out.println(MENU_MESSAGE_9);
        for (HashMap.Entry<SportEquipment, Integer> entry : shop.getGoods().entrySet()) {
            System.out.println(entry.getKey().getCategory() + " " + entry.getKey().getTitle() + " " +
                    +entry.getKey().getPrice() + " " + entry.getValue());
        }
        System.out.println();
    }

    // отчет о товарах которые были отданы в прокат
    private void case2Perform(RentUnit unit){
        System.out.println(MENU_MESSAGE_10);
        for (SportEquipment equipment :unit.getUnits()){
            System.out.println(equipment.getTitle() + " (" + equipment.getCategory() + ")");
        }
        System.out.println();
    }
}
