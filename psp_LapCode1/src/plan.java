public class plan {

    // ค้นหาด้วย id
    /*import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String FILE_PATH = "C:\\Users\\yothi\\Downloads\\PRODUCT.txt";
    private static List<Product> products = new ArrayList<>();

    public static void main(String[] args) {
        loadProducts(); // โหลดสินค้าจากไฟล์
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            // แสดงเมนูหลัก
            System.out.println("===== SE STORE =====");
            System.out.println("1. Show Product");
            System.out.println("2. Exit");
            System.out.println("3. Buy Product");
            System.out.println("====================");
            System.out.print("Select (1-3) : ");
            choice = scanner.nextInt();
            scanner.nextLine(); // รับ \n หลังจากรับตัวเลขเพื่อไม่ให้ส่งผลกระทบต่อการอ่าน string

            switch (choice) {
                case 1:
                    showProducts(); // แสดงรายการสินค้า
                    break;
                case 2:
                    // ออกจากโปรแกรม
                    System.out.println("===== SE STORE =====");
                    System.out.println("Thank you for using our service :3");
                    break;
                case 3:
                    buyProduct(scanner); // ซื้อสินค้า
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-3.");
                    break;
            }
        } while (choice != 2); // ออกจากลูปเมื่อผู้ใช้เลือก 2 (ออกจากโปรแกรม)

        scanner.close(); // ปิด Scanner หลังใช้งาน
    }

    private static void loadProducts() {
        // โหลดสินค้าจากไฟล์และบันทึกใน List
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t"); // ใช้ tab ในการแยกข้อมูล
                if (parts.length < 4) {  // ตรวจสอบว่า array มีอย่างน้อย 4 ส่วน
                    System.out.println("Invalid product entry: " + line);
                    continue;  // ข้ามรายการที่ไม่ถูกต้อง
                }

                // ดึงข้อมูลจากแต่ละส่วนของ array
                String name = parts[1].trim();
                double price = Double.parseDouble(parts[2].trim().substring(1)); // ตัด $ ออก
                int quantity = Integer.parseInt(parts[3].trim());
                products.add(new Product(name, price, quantity)); // เพิ่มสินค้าลงใน List
            }
        } catch (IOException e) {
            // จัดการกรณีที่ไม่สามารถอ่านไฟล์ได้
            System.out.println("Error reading the product file.");
        } catch (NumberFormatException e) {
            // จัดการกรณีที่ไม่สามารถแปลงข้อมูลเป็นตัวเลขได้
            System.out.println("Error parsing the product data.");
        }
    }

    private static void showProducts() {
        // แสดงรายการสินค้าทั้งหมด
        System.out.println("=========== SE STORE's Products ===========");
        // กำหนดรูปแบบการแสดงผลของหัวตาราง
        System.out.printf("%-5s %-15s %-10s %-10s%n", "#", "Name", "Price", "Quantity");

        // แสดงข้อมูลของสินค้าทีละรายการ
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            // แสดงลำดับที่, ชื่อสินค้า, ราคา, และจำนวนสินค้า
            System.out.printf("%-5d %-15s $%-9.2f %-10d%n", (i + 1), product.getName(), product.getPrice(), product.getQuantity());
        }
        System.out.println("===========================================");
    }

    private static void buyProduct(Scanner scanner) {
        // ฟังก์ชันสำหรับซื้อสินค้า
        System.out.print("Enter the product ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter the quantity to buy: ");
        int quantityToBuy = scanner.nextInt();

        if (id > 0 && id <= products.size()) {
            Product product = products.get(id - 1); // ดึงสินค้าโดยใช้ ID (ลำดับที่ - 1)
            if (product.getQuantity() >= quantityToBuy) {
                double totalPrice = product.getPrice() * quantityToBuy;
                product.setQuantity(product.getQuantity() - quantityToBuy); // อัปเดตจำนวนสินค้าในคลัง
                System.out.printf("You bought %d %s(s) for $%.2f%n", quantityToBuy, product.getName(), totalPrice);
            } else {
                System.out.println("Sorry, not enough stock available."); // แจ้งเตือนหากสินค้าไม่เพียงพอ
            }
        } else {
            System.out.println("Invalid product ID."); // แจ้งเตือนหาก ID ไม่ถูกต้อง
        }
    }
}

*/
}


        //ค้นหาด้วยชื่อ//
/*
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String FILE_PATH = "C:\\Users\\yothi\\Downloads\\PRODUCT.txt";
    private static List<Product> products = new ArrayList<>();

    public static void main(String[] args) {
        loadProducts(); // โหลดสินค้าจากไฟล์
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            // แสดงเมนูหลัก
            System.out.println("===== SE STORE =====");
            System.out.println("1. Show Product");
            System.out.println("2. Exit");
            System.out.println("3. Buy Product");
            System.out.println("====================");
            System.out.print("Select (1-3) : ");
            choice = scanner.nextInt();
            scanner.nextLine(); // รับ \n หลังจากรับตัวเลขเพื่อไม่ให้ส่งผลกระทบต่อการอ่าน string

            switch (choice) {
                case 1:
                    showProducts(); // แสดงรายการสินค้า
                    break;
                case 2:
                    System.out.println("===== SE STORE =====");
                    System.out.println("Thank you for using our service :3");
                    break;
                case 3:
                    buyProduct(scanner); // ซื้อสินค้า
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-3.");
                    break;
            }
        } while (choice != 2); // ออกจากลูปเมื่อผู้ใช้เลือก 2 (ออกจากโปรแกรม)

        scanner.close(); // ปิด Scanner หลังใช้งาน
    }

    private static void loadProducts() {
        // โหลดสินค้าจากไฟล์และบันทึกใน List
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t"); // ใช้ tab ในการแยกข้อมูล
                if (parts.length < 4) {  // ตรวจสอบว่า array มีอย่างน้อย 4 ส่วน
                    System.out.println("Invalid product entry: " + line);
                    continue;  // ข้ามรายการที่ไม่ถูกต้อง
                }

                // ดึงข้อมูลจากแต่ละส่วนของ array
                String name = parts[1].trim();
                double price = Double.parseDouble(parts[2].trim().substring(1)); // ตัด $ ออก
                int quantity = Integer.parseInt(parts[3].trim());
                products.add(new Product(name, price, quantity)); // เพิ่มสินค้าลงใน List
            }
        } catch (IOException e) {
            // จัดการกรณีที่ไม่สามารถอ่านไฟล์ได้
            System.out.println("Error reading the product file.");
        } catch (NumberFormatException e) {
            // จัดการกรณีที่ไม่สามารถแปลงข้อมูลเป็นตัวเลขได้
            System.out.println("Error parsing the product data.");
        }
    }

    private static void showProducts() {
        // แสดงรายการสินค้าทั้งหมด
        System.out.println("=========== SE STORE's Products ===========");
        // กำหนดรูปแบบการแสดงผลของหัวตาราง
        System.out.printf("%-5s %-15s %-10s %-10s%n", "#", "Name", "Price", "Quantity");

        // แสดงข้อมูลของสินค้าทีละรายการ
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            // แสดงลำดับที่, ชื่อสินค้า, ราคา, และจำนวนสินค้า
            System.out.printf("%-5d %-15s $%-9.2f %-10d%n", (i + 1), product.getName(), product.getPrice(), product.getQuantity());
        }
        System.out.println("===========================================");
    }

    private static void buyProduct(Scanner scanner) {
        // ฟังก์ชันสำหรับซื้อสินค้า
        System.out.print("Enter the product name: ");
        String productName = scanner.nextLine().trim(); // รับชื่อสินค้าที่ผู้ใช้ต้องการซื้อ

        // ค้นหาสินค้าจากชื่อ
        Product productToBuy = null;
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                productToBuy = product;
                break;
            }
        }

        if (productToBuy != null) {
            // ถ้าพบสินค้า ให้ผู้ใช้ระบุจำนวนที่ต้องการซื้อ
            System.out.print("Enter the quantity to buy: ");
            int quantityToBuy = scanner.nextInt();

            if (productToBuy.getQuantity() >= quantityToBuy) {
                double totalPrice = productToBuy.getPrice() * quantityToBuy;
                productToBuy.setQuantity(productToBuy.getQuantity() - quantityToBuy); // อัปเดตจำนวนสินค้าในคลัง
                System.out.printf("You bought %d %s(s) for $%.2f%n", quantityToBuy, productToBuy.getName(), totalPrice);
            } else {
                System.out.println("Sorry, not enough stock available."); // แจ้งเตือนหากสินค้าไม่เพียงพอ
            }
        } else {
            System.out.println("Product not found. Please check the product name."); // แจ้งเตือนหากไม่พบสินค้า
        }
    }
}
 */

//  เพิ่มจำนวนสินค้าในคลัง และการลดสินค้าในคลัง เพิ่มชนิดสินค้า//

/*
public class Product {
    private String name;
    private double price;
    private int quantity;
    private String category;

    public Product(String name, double price, int quantity, String category) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addStock(int quantity) {
        this.quantity += quantity;
    }

    public void reduceStock(int quantity) {
        if (this.quantity >= quantity) {
            this.quantity -= quantity;
        } else {
            throw new IllegalArgumentException("Not enough stock to reduce");
        }
    }
}
 */

// เพิ่มจำนวนสินค้าในคลัง และการลดสินค้าในคลัง เพิ่มชนิดสินค้า Main//

/*
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String FILE_PATH = "C:\\Users\\yothi\\Downloads\\PRODUCT.txt";
    private static List<Product> products = new ArrayList<>();

    public static void main(String[] args) {
        loadProducts(); // โหลดสินค้าจากไฟล์
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("===== SE STORE =====");
            System.out.println("1. Show Product");
            System.out.println("2. Buy Product");
            System.out.println("3. Add Stock");
            System.out.println("4. Reduce Stock");
            System.out.println("5. Exit");
            System.out.println("====================");
            System.out.print("Select (1-5) : ");
            choice = scanner.nextInt();
            scanner.nextLine(); // รับ \n หลังจากรับตัวเลขเพื่อไม่ให้ส่งผลกระทบต่อการอ่าน string

            switch (choice) {
                case 1:
                    showProducts();
                    break;
                case 2:
                    buyProduct(scanner);
                    break;
                case 3:
                    modifyStock(scanner, true);
                    break;
                case 4:
                    modifyStock(scanner, false);
                    break;
                case 5:
                    System.out.println("===== SE STORE =====");
                    System.out.println("Thank you for using our service :3");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-5.");
                    break;
            }
        } while (choice != 5);

        scanner.close();
    }

    private static void loadProducts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t"); // ใช้ tab ในการแยกข้อมูล
                if (parts.length < 5) {  // ตรวจสอบว่า array มีอย่างน้อย 5 ส่วน
                    System.out.println("Invalid product entry: " + line);
                    continue;  // ข้ามรายการที่ไม่ถูกต้อง
                }

                String name = parts[1].trim();
                double price = Double.parseDouble(parts[2].trim().substring(1)); // ตัด $ ออก
                int quantity = Integer.parseInt(parts[3].trim());
                String category = parts[4].trim(); // รับชนิดสินค้า

                products.add(new Product(name, price, quantity, category));
            }
        } catch (IOException e) {
            System.out.println("Error reading the product file.");
        } catch (NumberFormatException e) {
            System.out.println("Error parsing the product data.");
        }
    }

    private static void showProducts() {
        System.out.println("=========== SE STORE's Products ===========");
        System.out.printf("%-5s %-15s %-10s %-10s %-15s%n", "#", "Name", "Price", "Quantity", "Category");

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            System.out.printf("%-5d %-15s $%-9.2f %-10d %-15s%n", (i + 1), product.getName(), product.getPrice(), product.getQuantity(), product.getCategory());
        }
        System.out.println("===========================================");
    }

    private static void buyProduct(Scanner scanner) {
        System.out.print("Enter the product name: ");
        String productName = scanner.nextLine().trim();

        // ค้นหาสินค้าจากชื่อ
        Product productToBuy = null;
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                productToBuy = product;
                break;
            }
        }

        if (productToBuy != null) {
            System.out.print("Enter the quantity to buy: ");
            int quantityToBuy = scanner.nextInt();

            try {
                productToBuy.reduceStock(quantityToBuy);
                double totalPrice = productToBuy.getPrice() * quantityToBuy;
                System.out.printf("You bought %d %s(s) for $%.2f%n", quantityToBuy, productToBuy.getName(), totalPrice);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Product not found. Please check the product name.");
        }
    }

    private static void modifyStock(Scanner scanner, boolean isAdding) {
        System.out.print("Enter the product name: ");
        String productName = scanner.nextLine().trim();

        // ค้นหาสินค้าจากชื่อ
        Product productToModify = null;
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                productToModify = product;
                break;
            }
        }

        if (productToModify != null) {
            System.out.print("Enter the quantity to " + (isAdding ? "add: " : "reduce: "));
            int quantity = scanner.nextInt();

            if (isAdding) {
                productToModify.addStock(quantity);
                System.out.printf("Stock of %s increased by %d units.%n", productToModify.getName(), quantity);
            } else {
                try {
                    productToModify.reduceStock(quantity);
                    System.out.printf("Stock of %s reduced by %d units.%n", productToModify.getName(), quantity);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else {
            System.out.println("Product not found. Please check the product name.");
        }
    }
}

 */
