package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class KnapsackTest {

    @Test
    void test() {

        Item items[] = new Item[12];
        items[0] = new Item("Smart TV 65 pulg 4k", 1000, 20);
        items[1] = new Item("PS5", 600, 2);
        items[2] = new Item("Libro Java", 20, 1);
        items[3] = new Item("Samsung Galaxy", 700, 0.5);
        items[4] = new Item("Huawei", 400, 0.5);
        items[5] = new Item("Libro C++", 25, 0.5);
        items[6] = new Item("Xbox One", 500, 2.2);
        items[7] = new Item("Drone", 500, 3);
        items[8] = new Item("Proyector", 200, 3);
        items[9] = new Item("LapTop", 800, 3);
        items[10] = new Item("Impresora 3D", 800, 4);
        items[11] = new Item("iPhone", 800, 0.5);


        System.out.println("KNAPSACK PROBLEM");
        System.out.println(show(items));

        System.out.println("KNAPSACK PROBLEM - SOLUTION");
        Knapsack knapsack = new Knapsack(items, 14.5);
        //knapsack.bubbleSort();
        //System.out.println("Item");
        //System.out.println("Item - Sorted by Bubble");
        //System.out.println(show(items));
        System.out.println(knapsack);

    }

    private String show(Item[] items) {

        String result = "Item List\n";
        result += "\t\t\tName\t\t\tValue\t\t\tWeight";

        for(Item item : items){
            if(item==null) //Si es nulo es porque no hay más elementos
                break;

            result += "\n" +item.toString();

        }

        /**  for (int i = 0; i < items.length; i++){

            result+=items[0].getName();
        }
       **/

        return result;
    }


}