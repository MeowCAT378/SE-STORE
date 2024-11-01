import java.io.*;
import java.util.*;

public class Main {
    private static int loginCount = 0;
    private static String loggedInUser = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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
                    if (displayUserInfo()) {
                        // ถ้าบัญชีเป็น ACTIVE, แสดงหมวดหมู่และให้ผู้ใช้เลือก
                        while (true) {
                            showCategories(scanner, categories, products);
                        }
                    } else {
                        // หากบัญชีหมดอายุหรือไม่สามารถแสดงข้อมูลได้
                        loginCount = 0; // รีเซ็ตการพยายามเข้าสู่ระบบ
                        loggedInUser = ""; // รีเซ็ตผู้ใช้ที่เข้าสู่ระบบ
                        continue; // กลับไปที่เมนูหลัก
                    }

                } else {
                    // ล็อกอินไม่สำเร็จหรือบัญชีหมดอายุ จะกลับไปที่เมนูหลัก
                    loginCount = 0; // รีเซ็ตการพยายามเข้าสู่ระบบ
                    loggedInUser = ""; // รีเซ็ตผู้ใช้ที่เข้าสู่ระบบ
                }

            } else if (choice.equals("2")) {
                System.out.println("Exiting...");
                break; // ออกจากโปรแกรม
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
        while (loginCount < 3) {
            System.out.println("===== Login =====");
            System.out.print("Email: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (validateLogin(username, password)) {
                loggedInUser = username; // บันทึกอีเมลของผู้ใช้ที่เข้าสู่ระบบสำเร็จ
                return true; // ออกจากฟังก์ชัน login เมื่อเข้าสู่ระบบสำเร็จ
            } else {
                loginCount += 1;
                System.out.println("Error! - Email or Password is Incorrect (" + loginCount + ")");
            }
        }
        return false; // ล็อกอินไม่สำเร็จ
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
                    String extractedPassword = getExtractedPassword(filePassword);

                    // ตรวจสอบอีเมลและรหัสผ่านที่ป้อนโดยผู้ใช้
                    if (fileUsername.equals(username) && extractedPassword.equals(inputPassword)) {
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

    // ฟังก์ชันตรวจสอบสถานะบัญชี
    private static String ActiveCheck(String encodedPassword) {
        if (encodedPassword.length() > 2) {
            char activeChar = encodedPassword.charAt(2);
            switch (activeChar) {
                case '0':
                    return "NON ACTIVE";
                case '1':
                    return "ACTIVE";
                default:
                    return "UNKNOWN STATUS";
            }
        }
        return "INVALID PASSWORD LENGTH";
    }

    // ฟังก์ชันแปลงรหัสผ่านที่ถอดรหัสแล้ว
    private static String getExtractedPassword(String filePassword) {
        if (filePassword.length() >= 17) {
            StringBuilder extractedPassword = new StringBuilder();
            extractedPassword.append(filePassword.charAt(9));
            extractedPassword.append(filePassword.charAt(10));
            extractedPassword.append(filePassword.charAt(13));
            extractedPassword.append(filePassword.charAt(14));
            extractedPassword.append(filePassword.charAt(15));
            extractedPassword.append(filePassword.charAt(16));
            return extractedPassword.toString();
        }
        return "";
    }

    public static String accRole(String filePassword) {
        String role = "";

        // ตรวจสอบว่า `filePassword` มีความยาวเพียงพอหรือไม่
        if (filePassword.length() > 6) {
            // ตรวจสอบค่าของอักขระที่ตำแหน่ง 6 และกำหนดบทบาทตามนั้น
            char roleChar = filePassword.charAt(6);
            role = switch (roleChar) {
                case '0' -> "Staff";
                case '1' -> "Regular";
                case '2' -> "Silver";
                case '3' -> "Gold";
                default -> "Unknown Role";
            };
        } else {
            role = "Invalid Password Length";
        }
        return role;
    }

    // ฟังก์ชันแสดงข้อมูลของผู้ใช้
    private static boolean displayUserInfo() {
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

                        // ตรวจสอบสถานะบัญชี
                        String password = parts[4]; // รหัสผ่านที่เข้ารหัส
                        String accountStatus = ActiveCheck(password);

                        if ("NON ACTIVE".equals(accountStatus)) {
                            System.out.println("===== LOGIN =====");
                            System.out.println("Email : " + email);
                            System.out.println("Password : " + getExtractedPassword(password));
                            System.out.println("====================");
                            System.out.println("Error! - Your Account is Expired!");

                            // รีเซ็ตการพยายามเข้าสู่ระบบและผู้ใช้ที่เข้าสู่ระบบ
                            loginCount = 0;
                            loggedInUser = "";
                            return false; // บัญชีหมดอายุ
                        }

                        if ("ACTIVE".equals(accountStatus)) { // แสดงข้อมูลของผู้ใช้
                            // สร้างชื่อย่อในรูปแบบ "N. Lastname"
                            String role = accRole(password);
                            String nameInitial = firstName.charAt(0) + ". " + lastName + " (" + role + ")";
                            System.out.println("===== SE STORE =====");
                            System.out.println("Hello, " + nameInitial);
                            System.out.println("Email: " + maskEmail(email));
                            System.out.println("Phone: " + phone);
                            System.out.println("You have " + points);
                            System.out.println("====================");

                            // แสดงเมนูหลังจากแสดงข้อมูลผู้ใช้
                            showPostLoginMenu(new Scanner(System.in)); // สร้าง Scanner ใหม่
                            return true; // แสดงข้อมูลสำเร็จ
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading member data: " + e.getMessage());
        }
        return false; // ไม่พบข้อมูลผู้ใช้
    }

    // ฟังก์ชันแสดงเมนูหลังจากล็อกอินสำเร็จ
    private static void showPostLoginMenu(Scanner scanner) {
        label:
        while (true) {
            // ตรวจสอบบทบาทของผู้ใช้จากรหัสผ่าน
            String role = accRole(getPasswordFromFile(loggedInUser)); // รับรหัสผ่านจากไฟล์
            // แสดงเมนูตามบทบาท
            System.out.println("1. Show Category");
            if ("Staff".equals(role)) {
                System.out.println("2. Add Member");
            }
            System.out.println("3. Logout");
            System.out.println("====================");
            System.out.print("Select (1-" + (role.equals("Staff") ? "3" : "2") + "): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    // แสดง Categories
                    List<String> categories = loadCategories();
                    Map<String, List<Product>> products = loadProducts();
                    showCategories(scanner, categories, products);
                    break;
                case "2":
                    if ("Staff".equals(role)) {
                        // เพิ่มสมาชิกใหม่
                        addMember(scanner);
                    } else {
                        System.out.println("Invalid choice. Please select again.");
                    }
                    break;
                case "3":
                    // ล็อกเอาต์
                    loggedInUser = "";
                    loginCount = 0; // รีเซ็ตการพยายามเข้าสู่ระบบ

                    break label; // ออกจากลูปและกลับไปที่เมนูหลัก
                default:
                    System.out.println("Invalid choice. Please select again.");
                    break;
            }
        }
    }

    // ฟังก์ชันเพื่อรับรหัสผ่านจากไฟล์
    private static String getPasswordFromFile(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\yothi\\Downloads\\MEMBER.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 7) {
                    String fileUsername = parts[3];
                    if (fileUsername.equals(username)) {
                        return parts[4]; // รหัสผ่านที่เข้ารหัส
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading member data: " + e.getMessage());
        }
        return "";
    }

    private static void addMember(Scanner scanner) {

        System.out.println("===== Add Member =====");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        String points = "0.00";
        String password = generateRandomPassword();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\yothi\\Downloads\\MEMBER.txt", true))) {
            bw.write("\t" + firstName + "\t" + lastName + "\t" + email + "\t" + password + "\t" + phone + "\t");
            System.out.println("Success - New Member has been created!");
        } catch (IOException e) {
            System.out.println("Error! - Your Information are Incorrect!");
        }
    }

    private static String getValidInput(Scanner scanner, String prompt, int minLength, boolean mustContainAt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();

            if (input.length() <= minLength) {
                System.out.println("Error! - Your Information are Incorrect!");
            } else if (mustContainAt && !input.contains("@")) {
                System.out.println("Error! - Your Information are Incorrect!");
            } else {
                break;
            }
        }
        return input;
    }

    private static String getValidPhone(Scanner scanner) {
        String phone;
        while (true) {
            System.out.print("Enter Phone (10 digits): ");
            phone = scanner.nextLine().trim();

            if (phone.length() != 10 || !phone.matches("\\d+")) {
                System.out.println("Error! - Your Information are Incorrect!");
            } else {
                break;
            }
        }
        return phone;
    }

    // ฟังก์ชันสร้างรหัสผ่านสุ่ม
    private static String generateRandomPassword() {
        Random rand = new Random();
        return String.format("%06d", rand.nextInt(1000000)); // รหัสผ่าน 6 หลัก
    }

    private static String formatPhoneNumber(String phone) {
        if (phone.length() == 10) {
            return phone.substring(0, 3) + "-" + phone.substring(3, 6) + "-" + phone.substring(6);
        }
        return phone;
    }

    private static String maskEmail(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex > 0) {
            return email.substring(0, 2) + "***@" + email.substring(atIndex - 2, atIndex) + "***";
        }
        return email;
    }

    public static double getDiscount(String role) {
        return switch (role) {
            case "Silver" -> 0.05; // 5% discount
            case "Gold" -> 0.10; // 10% discount
            default -> 0.00; // No discount for Staff and Regular
        };
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
        String role = accRole(getPasswordFromFile(loggedInUser));
        System.out.println("============ " + categoryName + " ============");
        System.out.printf("#\t%-15s\t%-16s\t%-10s\n", "Name", "Price (฿)", "Quantity");

        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            double displayedPrice;
            double originalPrice = product.getPrice() * 34; // คำนวณราคาปกติ

            // คำนวณราคาที่แสดงตามบทบาท
            switch (role) {
                case "Staff", "Regular" -> {
                    displayedPrice = originalPrice; // ราคาไม่ลด
                    System.out.printf("%d\t%-12s\t%-8.2f\t%-8d\n", i + 1, product.getName(), displayedPrice, product.getQuantity());
                }
                case "Silver" -> {
                    displayedPrice = originalPrice * (1 - 0.05); // ลด 5%
                    System.out.printf("%d\t%-12s\t%-8.2f (%.2f)\t%-8d\n", i + 1, product.getName(), displayedPrice, originalPrice, product.getQuantity());
                }
                case "Gold" -> {
                    displayedPrice = originalPrice * (1 - 0.10); // ลด 10%
                    System.out.printf("%d\t%-12s\t%-8.2f (%.2f)\t%-8d\n", i + 1, product.getName(), displayedPrice, originalPrice, product.getQuantity());
                }
            }
        }

        System.out.println("================================");

        // ให้ผู้ใช้เลือกการเรียงลำดับ
        System.out.println("1. Show Name By DESC");
        System.out.println("2. Show Quantity By ASC");
        System.out.println("or Press Q to Exit :");
        System.out.print("Select (1-2 or Q): ");
        String sortChoice = scanner.nextLine();

        if (sortChoice.equalsIgnoreCase("Q")) {
            return; // ออกจากเมนู
        } else if (sortChoice.equals("1")) {
            productList.sort(Comparator.comparing(Product::getName).reversed()); // เรียงลำดับตามชื่อสินค้า Descending
        } else if (sortChoice.equals("2")) {
            productList.sort(Comparator.comparing(Product::getQuantity)); // เรียงลำดับตามปริมาณ Ascending
        } else {
            System.out.println("Please select 1-2 or Q");
            displayProducts(scanner, categoryName, productList); // เรียกใช้งานซ้ำถ้าทำการเลือกไม่ถูกต้อง
            return; // ออกจากฟังก์ชัน
        }
        // แสดงสินค้าตามลำดับที่เลือก
        displayProducts(scanner, categoryName, productList);
    }
}