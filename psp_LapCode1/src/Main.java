/**************** ********************************************************************/
/* Program Assignment: SE STORE */
/* Student ID: 66160088 */
/* Student Name: Yothin Sisaitham #1 */
/* Date: 25/08/2024 */
/* Description: ร้านค้า SE STORE  ที่แสดงสินค้าที่มี   */
/**********************************************************************************/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String choice;

        do {
            System.out.println("===== SE STORE =====");
            System.out.println("1. Show Product");
            System.out.println("2. Exit");
            System.out.println("====================");
            System.out.print("Select (1-2) : ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showProducts();
                    break;
                case "2":
                    System.out.println("===== SE STORE =====");
                    System.out.println("Thank you for using our service :3");
                    break;
                default:
                    System.out.println("Please select 1 or 2");
                    break;
            }
        } while (!choice.equals("2"));

        scanner.close();
    }

    private static void showProducts() {
        List<Product> products = new ArrayList<>();
        String FILE_PATH = "C:\\Users\\yothi\\Downloads\\PRODUCT.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t"); // ใช้ tab ในการแยกข้อมูล
                if (parts.length < 4) {  // ตรวจสอบว่า array มีอย่างน้อย 4 ส่วน
                    System.out.println("Invalid product entry: " + line);
                    continue;  // ข้ามรายการที่ไม่ถูกต้อง
                }

                String name = parts[1].trim();
                double price = Double.parseDouble(parts[2].trim().substring(1)); // ตัด $ ออก
                int quantity = Integer.parseInt(parts[3].trim());
                products.add(new Product(name, price, quantity));
            }
        } catch (IOException e) {
            System.out.println("Error reading the product file.");
            return;
        } catch (NumberFormatException e) {
            System.out.println("Error parsing the product data.");
            return;
        }

        System.out.println("=========== SE STORE's Products ==========="); // แสดงหัวข้อของตารางสินค้า

        System.out.printf("%-5s %-15s %-10s %-10s%n", "#", "Name", "Price", "Quantity");
        // แสดงชื่อหัวคอลัมน์ในตาราง โดยกำหนดความกว้างของคอลัมน์
        // %-5s : ช่องสำหรับหมายเลข (#) กว้าง 5 ตัวอักษร
        // %-15s : ช่องสำหรับชื่อสินค้า (Name) กว้าง 15 ตัวอักษร
        // %-10s : ช่องสำหรับราคา (Price) กว้าง 10 ตัวอักษร
        // %-10s : ช่องสำหรับจำนวนสินค้า (Quantity) กว้าง 10 ตัวอักษร

        for (int i = 0; i < products.size(); i++) { // แสดงข้อมูลสินค้าในลำดับที่อ่านจากไฟล์
            Product product = products.get(i);
            System.out.printf("%-5d %-15s $%-9.2f %-10d%n", (i + 1), product.getName(), product.getPrice(), product.getQuantity());
            // แสดงลำดับ, ชื่อ, ราคา และจำนวนสินค้า โดยใช้ฟอร์แมตที่กำหนด
            // (i + 1) : หมายเลขสินค้าเริ่มจาก 1
            // $%-9.2f : ราคาแสดงในรูปแบบเงินดอลลาร์ ($) และมีทศนิยม 2 ตำแหน่ง
        }
        System.out.println("===========================================");
    }
}