import java.util.Scanner;

public class nn {
    public static void main(String[] args) {

        Scanner hh = new Scanner(System.in);
        System.out.println("How to Order:\n" +
                "\tTo Add Product: \n" +
                "\t\tEnter the product number followed by the quantity.\t\n" +
                "\t\tExample: 1 50 (Adds 50 chips)\n" +
                "\tTo Adjust Quantity:\n" +
                "\t\t+ to add more items: 1 +50 (Adds 50 more chips)\n" +
                "\t\t- to reduce items: 1 -50 (Removes 50 chips)\n" +
                "\tEnter : [รอรับค่าถัดไป]\n");
    }
}
