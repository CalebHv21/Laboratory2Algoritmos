package domain;

public class Knapsack {

    private Item[] list; //lista de objetos candidatos para la mochila
    private double capacity; //capacidad máxima en la mochila

    //Constructor
  public Knapsack(Item[] list, double capacity) {
        this.list = list;
        this.capacity = capacity;
    }

    //Este me devuelve la lista de los objetos agregados a la mochila
    public Item[] solve() { // método que resuelve el problema


        bubbleSort();  //Primero ordeno la lista de items
        Item[] knapsackList = new Item[this.list.length];

        int i = 0, pos = 0;
        int n = list.length;
        double TotalWeight = 0; //Para llevar el peso total

        while(TotalWeight <= this.capacity && i < n){

            if(TotalWeight+list[i].getWeight() <= capacity){
                // sumo el peso del elemento a cargar
                TotalWeight += list[i].getWeight(); //Se agrega el elemento a la mochila

                // agrego el elemento a la solución
                knapsackList[pos++] = list[i]; //Se incrementa pos luego de que se guardara un valor para que el siguiente elemento se guarde en otra posición

            }//end if
            i++; //se agregue o no el elemento incrementamos el índice
        }//end while

        return knapsackList;
    }


    public void bubbleSort() {
        boolean swapped;
        int n = this.list.length;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (list[j].getValueWeight() < list[j + 1].getValueWeight()) {

                    Item temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;

                    swapped = true;

                }//end if
            }

            // Si no se realizaron intercambios, ya está ordenado, y salimos del ciclo
            if (swapped == false) {
                break;
            }
        }
    }


    @Override
    public String toString() {

        double totalWeight = 0;
        double totalValue = 0;
        Item[] solution = solve(); // resuelve el problema
        String result = "KNAPSACK PROBLEM"
                +"\n___________________________________________________"
                +"\nMax Weight: " + this.capacity
                +"\nItem list added: "
                +"\n\t\t\t\tName\t\t\tValue\t\t\tWeight";

        int i=1;
        for(Item item : solution){

            if(item==null) //Si es nulo es porque no hay más elementos
                break;

            totalWeight+=item.getWeight();
            totalValue+=item.getValue();
            result+="\n("+(i++)+")" + item;

        }
        result += "\n___________________________________________________";
        result += "\nTOTAL VALUE\t\t\t\t\t\t\t\t\t" + util.Utility.$format(totalValue);
        result += "\nTOTAL WEIGHT\t\t\t\t\t\t\t\t" + util.Utility.format(totalWeight);

    return result;
    }




}//END CLASS

