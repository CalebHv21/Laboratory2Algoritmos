package domain;

public class Item {

    private String name;
    private double value;
    private double weight;
    private double valueWeight; //Para maximizar la ganancia

    public Item(String name, double value, double weight) {

        this.name = name;
        this.value = value;
        this.weight = weight;
        this.valueWeight = value / weight; //Para las ganancias

    }//CONSTRUCTOR

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getValueWeight() {
        return valueWeight;
    }

    public void setValueWeight(double valueWeight) {
        this.valueWeight = valueWeight;
    }

    @Override
    public String toString() {

        //% Para aplicar formato
        return String.format("%20s, %12.2f, %12.2f", this.name, this.value, this.weight); //El primer valor un String de 20, el segundo un punto flotante de 12.2, y el tercero punto flotante de 12.2



        /*return "Item{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", weight=" + weight +
                ", valueWeight=" + valueWeight +
                '}';*/
    }

}
