/**************** ********************************************************************/
/* Progr am Assignment: SE STORE#3 */
/* Student ID: 66160088 */
/* Student Name: Yothin Sisaitham  */
/* Date: 05/09/2024 */
/* Description: ร้านค้า SE STORE  ที่มีการเข้าสู่ระบบก่อนใช้งาน  */
/**********************************************************************************/

import java.io.*;
import java.util.*;

public class Main {
    private static int loginAttempts = 0;
    private static String loggedInUser = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // เริ่มโปรแกรมแสดงเมนู Display#1
        while (true) {
            displayMainMenu();
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                // ทำการ Login
                if (login(scanner)) {
                    // ถ้า login สำเร็จ โหลด Category และ Product
                    List<String> categories = loadCategories();
                    Map<String, List<Product>> products = loadProducts();

                    // แสดงข้อมูลของผู้ใช้
                    displayUserInfo();

                    // วนลูปแสดงเมนูหลังจาก login สำเร็จ
                    while (true) {
                        showMainMenu();
                        String categoryChoice = scanner.nextLine();

                        if (categoryChoice.equals("1")) {
                            showCategories(scanner, categories, products);
                        } else if (categoryChoice.equals("2")) {
                            System.out.println("Logging out...");
                            break;
                        } else {
                            System.out.println("Please Select 1-2");
                        }
                    }
                } else {
                    System.out.println("You have exceeded the maximum number of login attempts. Please try again later.");
                }
            } else if (choice.equals("2")) {
                System.out.println("Thank you for using our service :3");
                break;
            } else {
                System.out.println("Please Select 1-2");
            }
        }

        scanner.close();
    }

    // ฟังก์ชันแสดงเมนูหลัก
    private static void showMainMenu() {
        System.out.println("===== SE STORE =====");
        System.out.println("1. Show Category");
        System.out.println("2. Logout");
        System.out.println("====================");
        System.out.print("Select (1-2): ");
    }

    // ฟังก์ชันแสดงเมนู Display#1
    private static void displayMainMenu() {
        System.out.println("===== SE STORE =====");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.println("====================");
        System.out.print("Select (1-2): ");
    }

    // ฟังก์ชันระบบ login
    private static boolean login(Scanner scanner) {
        while (loginAttempts < 3) {
            System.out.println("===== Login =====");
            System.out.print("Email: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (validateLogin(username, password)) {
                loggedInUser = username; // บันทึกอีเมลของผู้ใช้ที่เข้าสู่ระบบสำเร็จ
                return true;
            } else {
                loginAttempts++;
                System.out.println("Error! - Email or Password is Incorrect (" + loginAttempts + ")");
            }
        }
        return false;
    }

    // ฟังก์ชันตรวจสอบข้อมูล login
    private static boolean validateLogin(String username, String inputPassword) {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\yothi\\Downloads\\MEMBER.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 7) { // จำนวนคอลัมน์ในไฟล์ MEMBER.txt
                    String fileUsername = parts[3]; // อีเมล
                    String filePassword = parts[4]; // รหัสผ่านที่อยู่ในไฟล์

                    // ถอดรหัสรหัสผ่านที่เราต้องการจากตำแหน่งที่ 9, 10, 13, 14, 15, 16
                    StringBuilder extractedPassword = new StringBuilder();
                    extractedPassword.append(filePassword.charAt(9));
                    extractedPassword.append(filePassword.charAt(10));
                    extractedPassword.append(filePassword.charAt(13));
                    extractedPassword.append(filePassword.charAt(14));
                    extractedPassword.append(filePassword.charAt(15));
                    extractedPassword.append(filePassword.charAt(16));

                    // ตรวจสอบอีเมลและรหัสผ่านที่ป้อนโดยผู้ใช้
                    if (fileUsername.equals(username) && extractedPassword.toString().equals(inputPassword)) {
                        return true; // Login สำเร็จ
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading member data: " + e.getMessage());
        }
        // ข้อมูลไม่ตรง
        return false;
    }



    // ฟังก์ชันแสดงข้อมูลของผู้ใช้
    private static void displayUserInfo() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\yothi\\Downloads\\MEMBER.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 7) {
                    String fileUsername = parts[3]; // อีเมล
                    if (fileUsername.equals(loggedInUser)) {
                        String firstName = parts[1]; // ชื่อ
                        String lastName = parts[2];  // นามสกุล
                        String email = parts[3];
                        String phone = formatPhoneNumber(parts[5]);
                        String points = parts[6] + " Point";

                        // สร้างชื่อย่อในรูปแบบ "N. Lastname"
                        String nameInitial = firstName.charAt(0) + ". " + lastName;

                        System.out.println("===== SE STORE =====");
                        System.out.println("Hello, " + nameInitial);
                        System.out.println("Email: " + maskEmail(email));
                        System.out.println("Phone: " + phone);
                        System.out.println("You have " + points);
                        System.out.println("====================");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading member data: " + e.getMessage());
        }
    }

    // ฟังก์ชันแปลงหมายเลขโทรศัพท์ให้มีรูปแบบ
    private static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() == 10) {
            return phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6);
        }
        return phoneNumber;
    }

    // ฟังก์ชันแปลงอีเมลให้เป็นข้อความที่ปกปิด
    private static String maskEmail(String email) {
        String[] parts = email.split("@");
        if (parts.length == 2) {
            String localPart = parts[0];
            String domainPart = parts[1];

            // แปลงส่วนท้องถิ่น
            if (localPart.length() > 3) {
                localPart = localPart.substring(0, 2) + "***";
            }

            // แปลงส่วนโดเมน
            if (domainPart.length() > 2) {
                domainPart = domainPart.substring(0, 2) + "***";
            }

            return localPart + "@" + domainPart;
        }
        return email;
    }


    // ฟังก์ชันโหลดข้อมูล Category
    private static List<String> loadCategories() {
        List<String> categories = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\yothi\\Downloads\\CATEGORY.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    categories.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading categories: " + e.getMessage());
        }
        return categories;
    }

    // ฟังก์ชันโหลดข้อมูล Product
    private static Map<String, List<Product>> loadProducts() {
        Map<String, List<Product>> productsMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\yothi\\Downloads\\PRODUCT.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 5) {
                    String categoryCode = parts[4];
                    Product product = new Product(parts[1], Double.parseDouble(parts[2].substring(1)), Integer.parseInt(parts[3]));
                    productsMap.computeIfAbsent(categoryCode, k -> new ArrayList<>()).add(product);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
        return productsMap;
    }

    // ฟังก์ชันแสดง Category และจัดการเลือก Category
    private static void showCategories(Scanner scanner, List<String> categories, Map<String, List<Product>> products) {
        while (true) {
            displayCategoryMenu(categories);
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("Q")) {
                break;
            } else {
                handleCategorySelection(scanner, choice, categories, products);
            }
        }
    }

    // ฟังก์ชันแสดงเมนู Category
    private static void displayCategoryMenu(List<String> categories) {
        System.out.println("===== SE STORE's Product Categories =====");
        System.out.printf("%-4s %-20s\n", "#", "Category");
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("%-4d %-20s\n", i + 1, categories.get(i).split("\t")[1]);
        }
        System.out.println("=========================================");
        System.out.print("Select Category to Show Product (1-" + categories.size() + ") or Q for exit: ");
    }

    // ฟังก์ชันจัดการเมื่อเลือก Category
    private static void handleCategorySelection(Scanner scanner, String choice, List<String> categories, Map<String, List<Product>> products) {
        try {
            int categoryIndex = Integer.parseInt(choice) - 1;
            if (categoryIndex >= 0 && categoryIndex < categories.size()) {
                String categoryCode = categories.get(categoryIndex).split("\t")[0];
                displayProducts(scanner, categories.get(categoryIndex).split("\t")[1], products.get(categoryCode));
            } else {
                System.out.println("Invalid category number. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    // ฟังก์ชันแสดงข้อมูลสินค้า
    private static void displayProducts(Scanner scanner, String categoryName, List<Product> products) {
        System.out.println("===== SE STORE's Products in " + categoryName + " =====");
        if (products != null && !products.isEmpty()) {
            System.out.printf("%-20s %-10s %-10s\n", "Product Name", "Price", "Quantity");
            for (Product product : products) {
                System.out.printf("%-20s $%-9.2f %-10d\n", product.getName(), product.getPrice(), product.getQuantity());
            }
        } else {
            System.out.println("No products available");
        }
        System.out.println("==========================================");
    }
}
