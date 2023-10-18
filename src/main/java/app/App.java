
package app;


import java.io.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

class Item {

    private String name;
    private double price;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + ";" + price;
    }
}


public class App {

    public static void main(String[] args) {

        Set<Item> items = Set.of(
                new Item("Bag", 150.99),
                new Item("Hat", 20.99),
                new Item("Animal", 10.99),
                new Item("T-Shirt", 100.99)
        );


        File file = new File("C:\\Users\\Acer\\IdeaProjects\\ExcelCrud\\src\\main\\java\\rayan" + "\\test.csv");


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Item item : items) {
                writer.write(item.toString());
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            Set<Item> newItems = new HashSet<>(items.size());

            while (reader.ready()) {
                String[] line = reader.readLine().split(";");
                newItems.add(new Item(line[0], Double.parseDouble(line[1])));
            }


            double mediumPrice = newItems.stream()
                    .map(Item::getPrice)
                    .mapToDouble(Double::doubleValue)
                    .sum() / newItems.size();

            System.out.printf("Average total price of items: %.2f  \n", mediumPrice);
            System.out.println("Items with medium price below: ");

            newItems.stream()
                    .filter(item -> item.getPrice() < mediumPrice)
                    .map(Item::getName)
                    .sorted(Comparator.naturalOrder())
                    .forEach(System.out::println);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

}



