/**************** ********************************************************************/
/* Program Assignment: SE STORE#3 */
/* Student ID: 66160088 */
/* Student Name: Yothin Sisaitham  */
/* Date: 28/08/2024 */
/* Description: ร้านค้า SE STORE  ที่แสดงสินค้าที่มี   */
/**********************************************************************************/


import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // สร้าง Scanner สำหรับรับข้อมูลจากผู้ใช้
        Scanner scanner = new Scanner(System.in);

        // โหลดข้อมูล Category จากไฟล์
        List<String> categories = loadCategories();

        // โหลดข้อมูล Product จากไฟล์
        Map<String, List<Product>> products = loadProducts();

        // วนลูปเพื่อแสดงเมนูให้ผู้ใช้เลือกทำงาน
        while (true) {
            // แสดงเมนูหลัก
            System.out.println("===== SE STORE =====");
            System.out.println("1. Show Category");
            System.out.println("2. Exit");
            System.out.println("====================");
            System.out.print("Select (1-2): ");

            // รับข้อมูลจากผู้ใช้
            String choice = scanner.nextLine();

            // ตรวจสอบการเลือกของผู้ใช้
            if (choice.equals("1")) {
                // แสดง Category และสินค้าที่เกี่ยวข้อง
                showCategories(scanner, categories, products);
            } else if (choice.equals("2")) {
                // ออกจากโปรแกรม
                System.out.println("Thank you for using our service :3");
                break;
            } else {
                // แสดงข้อความเมื่อผู้ใช้เลือกตัวเลือกที่ไม่ถูกต้อง
                System.out.println("Please Select 1-2");
            }
        }
        // ปิด Scanner
        scanner.close();
    }

    // เมธอดสำหรับโหลด Category จากไฟล์
    private static List<String> loadCategories() {
        List<String> categories = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\yothi\\Downloads\\CATEGORY.txt"))) {
            String line;
            // อ่านไฟล์ทีละบรรทัด
            while ((line = br.readLine()) != null) {
                // ตรวจสอบว่าบรรทัดไม่ใช่บรรทัดว่าง
                if (!line.trim().isEmpty()) {
                    categories.add(line);
                }
            }
        } catch (IOException e) {
            // แสดงข้อความเมื่อเกิดข้อผิดพลาดในการโหลดไฟล์
            System.out.println("Error loading categories: " + e.getMessage());
        }
        return categories;
    }

    // เมธอดสำหรับโหลด Product จากไฟล์
    private static Map<String, List<Product>> loadProducts() {
        Map<String, List<Product>> productsMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\yothi\\Downloads\\PRODUCT.txt"))) {
            String line;
            // อ่านไฟล์ทีละบรรทัด
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 5) {
                    // แยกข้อมูล Product ตาม Category
                    String categoryCode = parts[4];
                    Product product = new Product(parts[1], Double.parseDouble(parts[2].substring(1)), Integer.parseInt(parts[3]));
                    productsMap.computeIfAbsent(categoryCode, k -> new ArrayList<>()).add(product);
                }
            }
        } catch (IOException e) {
            // แสดงข้อความเมื่อเกิดข้อผิดพลาดในการโหลดไฟล์
            System.out.println("Error loading products: " + e.getMessage());
        }
        return productsMap;
    }

    // เมธอดสำหรับแสดง Category ให้ผู้ใช้เลือก
        private static void showCategories(Scanner scanner, List<String> categories, Map<String, List<Product>> products) {
        while (true) {
            System.out.println("===== SE STORE's Product Categories =====");
            System.out.printf("%-4s %-20s\n", "#", "Category");
            for (int i = 0; i < categories.size(); i++) {
                System.out.printf("%-4d %-20s\n", i + 1, categories.get(i).split("\t")[1]);
            }
            System.out.println("=========================================");
            System.out.print("Select Category to Show Product (1-" + categories.size() + ") or Q for exit: ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("Q")) {
                break;
            } else {
                try {
                    int categoryIndex = Integer.parseInt(choice) - 1;
                    if (categoryIndex >= 0 && categoryIndex < categories.size()) {
                        String categoryCode = categories.get(categoryIndex).split("\t")[0];
                        displayProducts(scanner, categories.get(categoryIndex).split("\t")[1], products.get(categoryCode));
                    } else {
                        System.out.println("Invalid category selection. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number or Q to exit.");
                }
            }
        }
    }

    // เมธอดสำหรับแสดงรายการสินค้าใน Category ที่เลือก
    private static void displayProducts(Scanner scanner, String categoryName, List<Product> productList) {
        while (true) {
            // แสดงรายการสินค้าในรูปแบบตาราง
            System.out.println("============ " + categoryName + " ============");
            System.out.printf("%-4s %-15s %-9s %-8s\n", "#", "Name", "Price (฿)", "Quantity");
            for (int i = 0; i < productList.size(); i++) {
                Product product = productList.get(i);
                System.out.printf("%-4d %-15s %-9.2f %-8d\n", i + 1, product.getName(), product.getPrice() * 34 , product.getQuantity());
            }
            System.out.println("================================");
            System.out.print("Press Q to Exit: ");

            // รับข้อมูลจากผู้ใช้เพื่อกลับไปยังหน้าก่อนหน้า
            String choice = scanner.nextLine();

            // ตรวจสอบการเลือกของผู้ใช้
            if (choice.equalsIgnoreCase("Q")) {
                break;
            }
        }
    }
}
