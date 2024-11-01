import java.io.*;
import java.util.*;

public class Main {
    private static int loginCount = 0;
    private static String loggedInUser = "";
    private static final String CART_FILE = "C:\\Users\\yothi\\Downloads\\CART.txt";
    private static Member loggedInMember; // ประกาศตัวแปรสมาชิก

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Member> members = loadMembers(); // โหลดสมาชิกที่นี่
        List<Member> memberS = new ArrayList<>();  // ตัวอย่างการประกาศ List<Member>

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
                    if (displayUserInfo(members)) { // ส่ง members ไปที่ displayUserInfo
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

    private static void resetLogin() {
        loginCount = 0; // รีเซ็ตการพยายามเข้าสู่ระบบ
        loggedInUser = ""; // รีเซ็ตผู้ใช้ที่เข้าสู่ระบบ
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
                loggedInUser = username; // บันทึกอีเมลของผู้ใช้ที่เข้าสู่ระบบ
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

    public static List<Member> loadMembers() {
        List<Member> members = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\yothi\\Downloads\\MEMBER.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 7) {
                    String id = parts[0];
                    String firstName = parts[1];
                    String lastName = parts[2];
                    String email = parts[3];
                    String password = parts[4];
                    String phone = parts[5];
                    String points = parts[6];

                    Member member = new Member(id, firstName, lastName, email, password, phone, points);
                    members.add(member);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return members;
    }

    // ฟังก์ชันแสดงข้อมูลของผู้ใช้
    private static boolean displayUserInfo(List<Member> members) {
        for (Member member : members) {
            if (member.getEmail().equals(loggedInUser)) {
                String firstName = member.getFirstname(); // ชื่อ
                String lastName = member.getLastname();   // นามสกุล
                String email = member.getEmail();
                String phone = formatPhoneNumber(member.getPhone());
                String points = member.getPoints() + " Points";

                // ตรวจสอบสถานะบัญชี
                String password = member.getPassword(); // รหัสผ่านที่เข้ารหัส
                String accountStatus = ActiveCheck(password);

                if ("NON ACTIVE".equals(accountStatus)) {
                    System.out.println("===== LOGIN =====");
                    System.out.println("Email : " + email);
                    System.out.println("Password : " + getExtractedPassword(password));
                    System.out.println("====================");
                    System.out.println("Error! - Your Account is Expired!");

                    resetLogin(); // ใช้ฟังก์ชันสำหรับรีเซ็ตการเข้าสู่ระบบ
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
        System.out.println("User not found.");
        return false; // ไม่พบข้อมูลผู้ใช้
    }

    private static List<Member> members = new ArrayList<>();

    // ฟังก์ชันแสดงเมนูหลังจากล็อกอินสำเร็จ
    private static void showPostLoginMenu(Scanner scanner) {
        label:
        while (true) {
            // ตรวจสอบบทบาทของผู้ใช้จากรหัสผ่าน
            String role = accRole(getPasswordFromFile(loggedInUser)); // รับรหัสผ่านจากไฟล์

            // แสดงเมนูตามบทบาท
            if ("Staff".equals(role)) {
                System.out.println("1. Show Category");
                System.out.println("2. Add Member");
                System.out.println("3. Edit Member");
                System.out.println("4. Edit Product");
                System.out.println("5. Logout");
                System.out.println("====================");
                System.out.print("Select (1-5): ");
            } else if (role.equals("Regular") || role.equals("Silver") || role.equals("Gold")) {
                System.out.println("1. Show Category");
                System.out.println("2. Order Product");
                System.out.println("3. Logout");
                System.out.println("====================");
                System.out.print("Select (1-3): ");
            }

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
                        addMember(scanner, members);
                    } else if (role.equals("Regular") || role.equals("Silver") || role.equals("Gold")) {
                        // สั่งซื้อสินค้าorderProduct(scanner);
                    } else {
                        System.out.println("Invalid choice. Please select again.");
                    }
                    break;

                case "3":
                    if ("Staff".equals(role)) {
                        List<Member> members = loadMembers(); // โหลดสมาชิกทั้งหมด
                        System.out.println("===== SE STORE's Member =====");
                        System.out.printf("#\t%-23s\t%-30s\n", "Name", "Email");
                        for (int i = 0; i < members.size(); i++) {
                            System.out.printf("%-3d %-24s %-30s\n", i + 1, members.get(i).getFirstname() + " " + members.get(i).getLastname(), members.get(i).getEmail());
                        }
                        System.out.println("================================");
                        System.out.println("Type Member Number, You want to edit or Press Q to Exit");
                        System.out.print("Select (1-" + members.size() + ") : ");

                        String input = scanner.nextLine(); // รับค่า index ของสมาชิกที่ต้องการแก้ไข

                        if (input.equalsIgnoreCase("Q")) {
                            break;
                        }

                        try {
                            int memberIndex = Integer.parseInt(input) - 1;
                            if (memberIndex >= 0 && memberIndex < members.size()) {
                                Member selectedMember = members.get(memberIndex); // เลือกสมาชิกตาม index ที่ระบุ
                                editMemberData(scanner, selectedMember, members); // เรียกใช้งาน editMemberData โดยส่งข้อมูลที่ถูกต้อง
                            } else {
                                System.out.println("Invalid selection! Please try again.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input! Please enter a valid member number.");
                        }
                    } else if (role.equals("Regular") || role.equals("Silver") || role.equals("Gold")) {
                        // ล็อกเอาต์
                        loggedInUser = "";
                        loginCount = 0; // รีเซ็ตการเข้าสู่ระบบ
                        break label; // ออกจากลูปและกลับไปที่เมนูหลัก
                    } else {
                        System.out.println("Please select again.");
                    }
                    break;

                case "4":
                    if ("Staff".equals(role)) {
                        // โหลดสินค้าจากทุกหมวดหมู่
                        Map<String, List<Product>> productsEdit = loadProducts();

                        // รวมสินค้าจากทุกหมวดหมู่เป็น List เดียว
                        List<Product> allProducts = new ArrayList<>();
                        for (List<Product> productList : productsEdit.values()) {
                            allProducts.addAll(productList);
                        }

                        // เรียงสินค้าโดยใช้ id
                        allProducts.sort(Comparator.comparingInt(p -> Integer.parseInt(p.getId()))); // สมมุติว่า getId() คืนค่าหมายเลข id ของสินค้า

                        // แสดงรายการสินค้าตามลำดับ id
                        System.out.println("=========== SE STORE's Products ===========");
                        System.out.printf("%-4s %-15s %-10s %-8s\n", "#", "Name", "Price", "Quantity");
                        for (int i = 0; i < allProducts.size(); i++) {
                            Product product = allProducts.get(i);
                            System.out.printf("%-4d %-15s $%-9.2f %-8d\n", i + 1, product.getName(), product.getPrice() * 34, product.getQuantity());
                        }
                        System.out.println("============================================");
                        System.out.println("Type Product Number, You want to edit or Press Q to Exit");
                        System.out.print("Select (1-" + allProducts.size() + ") :");

                        // รับข้อมูลจากผู้ใช้เพื่อเลือกสินค้าที่ต้องการแก้ไข
                        String input = scanner.nextLine();

                        // ตรวจสอบว่าผู้ใช้ต้องการออก
                        if (input.equalsIgnoreCase("Q")) {
                            break; // ออกจากการแก้ไขสินค้า
                        }

                        // แปลง input เป็นหมายเลขสินค้า
                        try {
                            int selectedProductIndex = Integer.parseInt(input) - 1; // ลบ 1 เพื่อให้ตรงกับ index ของ List
                            if (selectedProductIndex >= 0 && selectedProductIndex < allProducts.size()) {
                                Product selectedProduct = allProducts.get(selectedProductIndex);
                                // เรียกเมธอดแก้ไขสินค้า
                                editProductData(scanner, selectedProduct, allProducts);
                            } else {
                                System.out.println("Invalid selection! Please try again.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input! Please enter a valid product number.");
                        }
                    }
                    break;

                case "5":
                    if ("Staff".equals(role)) {
                        // ล็อกเอาต์
                        loggedInUser = "";  //608854
                        loginCount = 0; // รีเซ็ตการเข้าสู่ระบบ
                        break label; // ออกจากลูปและกลับไปที่เมนูหลัก
                    }else {
                        displayMainMenu();
                    }
                    break;

                default:
                    System.out.println("Invalid choice. Please select again.");
                    break;
            }
        }
    }
    private static void orderProduct(Scanner scanner, String role, Member member) {
        List<Product> products = new ArrayList<>(); // เปลี่ยนจาก List<String[]> เป็น List<Product>
        CartManager cartManager = new CartManager();
        cartManager.loadCart(); // โหลดข้อมูลตะกร้าจากไฟล์

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\yothi\\Downloads\\PRODUCT.txt"))) {
            String line;
            int count = 1;
            System.out.println("=========== SE STORE's Products ===========");
            System.out.printf("#\t%-15s\tPrice (฿)\tQuantity\n", "Name");

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 4) {
                    double priceInBaht = Double.parseDouble(parts[2].replace("$", "")) * 34.00;
                    Product product = new Product(parts[0], parts[1], priceInBaht, Integer.parseInt(parts[3])); // สร้างอ็อบเจ็กต์ Product
                    products.add(product); // เพิ่มใน List<Product>
                    System.out.printf("%d\t%-15s\t%.2f\t\t%s\n", count, parts[1], priceInBaht, parts[3]);
                    count++;
                }
            }
            System.out.println("===========================================");
            orderProductMenu();

            while (true) {
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("Q")) {
                    cartManager.saveCart(); // บันทึกข้อมูลตะกร้าก่อนออก
                    System.out.println("Your cart has been saved!");
                    displayUserInfo(Collections.singletonList(member));
                    break;
                } else if (input.equals("1")) {
                    showHowToOrder();
                } else if (input.equals("2")) {
                    displayProductsByRole(products, role); // เปลี่ยนให้ส่ง List<Product>
                } else if (input.isEmpty()) {
                    // ถ้า input ว่างเปล่าไม่ทำอะไร
                } else {
                    // รับค่าข้อมูลการสั่งซื้อและอัปเดตสินค้าในตะกร้า
                    handleOrderInput(input, products, cartManager, member.getId());
                }
                System.out.print("Enter : ");
            }

        } catch (IOException e) {
            System.out.println("Error reading the product file.");
        }
    }

    // ฟังก์ชันแสดงสินค้าตามบทบาทของผู้ใช้
    private static void displayProductsByRole(List<Product> products, String role) {
        System.out.println("============ " + "SE STORE's Products" + " ============");
        System.out.printf("#\t%-15s\t%-16s\t%-10s\n", "Name", "Price (฿)", "Quantity");

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            double originalPrice = product.getPrice(); // ราคาปกติในบาท
            double displayedPrice;

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
        System.out.println("===========================================");
    }


    private static void handleOrderInput(String input, List<Product> productList, CartManager cartManager, String memberId) {
        String[] orderParts = input.split(" ");
        if (orderParts.length == 2) {
            String productId = productList.get(Integer.parseInt(orderParts[0]) - 1)[0];
            int newQuantity = Integer.parseInt(orderParts[1]);

            // ตรวจสอบสินค้าที่มีอยู่ในตะกร้า
            int currentQuantityInCart = cartManager.getCartQuantity(memberId, productId);

            // อัพเดตจำนวนในตะกร้า
            if (newQuantity > 0) {
                // ตรวจสอบจำนวนสินค้าคงคลัง
                int availableStock = cartManager.getAvailableStock(productId); // เพิ่มฟังก์ชันนี้ใน CartManager
                if (availableStock < newQuantity) {
                    System.out.println("จำนวนสินค้าที่คุณสั่งซื้อมากกว่าที่มีในสต็อก (มีอยู่ " + availableStock + " รายการ)");
                    return;
                }
                if (currentQuantityInCart > 0) {
                    // ถ้ามีสินค้าในตะกร้า
                    int difference = newQuantity - currentQuantityInCart;
                    cartManager.adjustCart(memberId, productId, newQuantity);
                    cartManager.updateProductStockForAdjustment(productId, -difference); // ลดจำนวนในสต็อก
                } else {
                    // ไม่มีสินค้าในตะกร้า ให้เพิ่มเข้าไป
                    cartManager.adjustCart(memberId, productId, newQuantity);
                    cartManager.updateProductStockForAdjustment(productId, -newQuantity); // ลดจำนวนในสต็อก
                }
            } else {
                // หาก newQuantity <= 0 ให้ลบสินค้าออกจากตะกร้า
                cartManager.adjustCart(memberId, productId, newQuantity);
                cartManager.updateProductStockForAdjustment(productId, currentQuantityInCart); // คืนสินค้าทั้งหมดกลับไปสต็อก
            }
        } else {
            System.out.println("Your input is invalid!");
        }
    }


    private static void orderProductMenu(){
        System.out.println("1. How to Order");
        System.out.println("2. List Products");
        System.out.println("Q. Exit");
    }

    private static void showHowToOrder() {
        System.out.println("How to Order:");
        System.out.println("To Add Product:");
        System.out.println("\tEnter the product number followed by the quantity.");
        System.out.println("\tExample: 1 50 (Adds 50 chips)");
        System.out.println("To Adjust Quantity:");
        System.out.println("\t+ to add more items: 1 +50 (Adds 50 more chips)");
        System.out.println("\t- to reduce items: 1 -50 (Removes 50 chips)");
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

    private static void addMember(Scanner scanner, List<Member> members) {
        System.out.println("===== Add Member =====");

        // รับชื่อจริง
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        if (firstName.length() <= 2) {
            System.out.println("Error! - Your Information are Incorrect");
            return;
        }

        // รับนามสกุล
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        if (lastName.length() <= 0) {
            System.out.println("Error! - Your Information are Incorrect");
            return;
        }

        // รับอีเมล
        System.out.print("Email: ");
        String email = scanner.nextLine();
        if (email.length() <= 2 || !email.contains("@")) {
            System.out.println("Error! - Your Information are Incorrect");
            return;
        }

        // รับเบอร์โทรศัพท์
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        if (phone.length() != 10 || !phone.matches("\\d+")) {
            System.out.println("Error! - Your Information are Incorrect");
            return;
        }

        String points = "0.00"; // กำหนดคะแนนเริ่มต้น

        // สร้าง ID ใหม่สำหรับสมาชิก
        String newMemberId = String.valueOf(members.size() + 9013); // ID เริ่มจาก 9013 และเพิ่มตามลำดับ
        // สร้าง Member ใหม่
        Member newMember = new Member(newMemberId, firstName, lastName, email, generateRandomPassword(), phone, points);
        // เพิ่มสมาชิกใหม่ในรายการ
        members.add(newMember);
        // บันทึกสมาชิกลงไฟล์
        addMemberFile(members);

        // สร้างรหัสผ่านแบบสุ่ม
        String encodedPassword = generateRandomPassword(); // สร้างรหัสผ่าน

        // ถอดรหัสรหัสผ่านตาม index ที่กำหนด
        StringBuilder extractedPassword = new StringBuilder();
        extractedPassword.append(encodedPassword.charAt(9));
        extractedPassword.append(encodedPassword.charAt(10));
        extractedPassword.append(encodedPassword.charAt(13));
        extractedPassword.append(encodedPassword.charAt(14));
        extractedPassword.append(encodedPassword.charAt(15));
        extractedPassword.append(encodedPassword.charAt(16));

        String randomPassword = generateRandomPassword();
        Member newMemberS = new Member(newMemberId, firstName, lastName, email, randomPassword, phone, points);
        members.add(newMemberS);

        // แสดงชื่อ + ตัวอักษรแรกของนามสกุล + รหัสผ่านที่ถอดรหัสแล้ว
        System.out.println(firstName + "'" + lastName.charAt(0) + " " + " Password is " +  extractedPassword.toString());

    }

    public static String generateRandomPassword() {
        StringBuilder password = new StringBuilder(19);
        Random random = new Random();
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYSZ";

        // สุ่มตัวอักษรภาษาอังกฤษสำหรับอินเด็กซ์ที่กำหนด
        for (int i = 0; i < 19; i++) { //9604708
            if (i == 9 || i == 10 || i == 13 || i == 14 || i == 15 || i == 16) {
                // กรณีนี้จะเป็นรหัสผ่าน 6 หลัก
                password.append(random.nextInt(10)); // ตัวเลข 0-9
            } else if (i == 0 || i == 1 || i == 3 || i == 4 || i == 5 || i == 7 || i == 8 || i == 11 || i == 12 || i == 17 || i == 18) {
                password.append(characters.charAt(random.nextInt(characters.length()))); // ตัวอักษรสุ่ม
            } else if (i == 2 || i == 6 ) {
                password.append("1"); // ตำแหน่งที่ 3 ต้องเป็น 1

            } else {
                password.append("0"); // กรณีอื่น ๆ จะกำหนดเป็น "0" หรือสามารถกำหนดเป็นอักขระอื่น ๆ ได้
            }
        }

        return password.toString();
    }

    public static void addMemberFile(List<Member> members) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\yothi\\Downloads\\MEMBER.txt", true))) {
            for (Member memberS : members) { // memberS ถูกประกาศในลูปนี้
                String line = String.join("\t",
                        memberS.getId(),
                        memberS.getFirstname(),
                        memberS.getLastname(),
                        memberS.getEmail(),
                        memberS.getPassword(),
                        memberS.getPhone(),
                        memberS.getPoints());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing member file: " + e.getMessage());
        }
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

    private static void editMemberData(Scanner scanner, Member selectedMember, List<Member> members) {
        System.out.println("==== Edit info of " + selectedMember.getFirstname() + " " + selectedMember.getLastname() + " ====");
        System.out.println("Type new info or Hyphen (-) for none edit.");

        System.out.print("Firstname: "); // แก้ไข Firstname
        String newFirstname = scanner.nextLine();
        if (!newFirstname.equals("-")) {
            if (newFirstname.length() <= 2 || !newFirstname.matches("[a-zA-Z]+")) {
                System.out.println("Error! - Your information is incorrect! ");
                return;
            }
            selectedMember.setFirstname(newFirstname);
        }

        System.out.print("Lastname: "); // แก้ไข Lastname
        String newLastname = scanner.nextLine();
        if (!newLastname.equals("-")) {
            if (newLastname.length() <= 2 || !newLastname.matches("[a-zA-Z]+")) {
                System.out.println("Error! - Your information is incorrect! ");
                return;
            }
            selectedMember.setLastname(newLastname);
        }

        System.out.print("Email: "); // แก้ไข Email
        String newEmail = scanner.nextLine();
        if (!newEmail.equals("-")) {
            if (newEmail.length() <= 2 || !newEmail.contains("@")) {
                System.out.println("Error! - Your information is incorrect! ");
                return;
            }
            selectedMember.setEmail(newEmail);
        }

        System.out.print("Phone: "); // แก้ไข Phone
        String newPhone = scanner.nextLine();
        if (!newPhone.equals("-")) {
            if (newPhone.length() != 10 || !newPhone.matches("\\d+")) {
                System.out.println("Error! - Your information is incorrect! ");
                return;
            }
            selectedMember.setPhone(newPhone);
        }

        // แสดงข้อความยืนยันการอัปเดตข้อมูล
        System.out.println("Success - Member has been updated!");

        // บันทึกข้อมูลที่แก้ไขกลับลงไฟล์
        editFileMemberData(members); // บันทึกข้อมูลสมาชิกทั้งหมดลงไฟล์
    }

    public static void editFileMemberData(List<Member> members) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\yothi\\Downloads\\MEMBER.txt"))) {
            for (Member member : members) {
                String line = String.join("\t",
                        member.getId(),
                        member.getFirstname(),
                        member.getLastname(),
                        member.getEmail(),
                        member.getPassword(),
                        member.getPhone(),
                        member.getPoints());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing member file: " + e.getMessage());
        }
    }


    // เมธอดสำหรับโหลด Category จากไฟล์
    private static List<String> loadCategories() {
        List<String> categories = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\yothi\\Downloads\\CATEGORY.txt"))) {
            String line;
            // อ่านไฟล์ทีละบรรทัด
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) { // เช็คว่าบรรทัดไม่ใช่บรรทัดว่าง
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
                    String id = parts[0];               // ดึง id จากคอลัมน์แรก
                    String name = parts[1];             // ชื่อสินค้า
                    double price = Double.parseDouble(parts[2].substring(1)); // ราคา
                    int quantity = Integer.parseInt(parts[3]); // จำนวน
                    String categoryCode = parts[4];     // หมวดหมู่สินค้า

                    // สร้าง Product และเก็บ id ด้วย
                    Product product = new Product(id, name, price, quantity, categoryCode);

                    // เพิ่ม Product เข้าไปใน Map ตาม Category
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

    private static void editProductData(Scanner scanner, Product product, List<Product> products) {
        System.out.println("==== Edit info of " + product.getName() + " ====");

        while (true) {
            // แก้ไขชื่อสินค้า
            System.out.print("Name: ");
            String newProductName = scanner.nextLine();
            if (!newProductName.equals("-")) {
                product.setName(newProductName); // อัปเดตชื่อสินค้า
            }

            // แก้ไขจำนวนสินค้า
            System.out.print("Quantity (+ or -): ");
            String newQuantity = scanner.nextLine();

            boolean updatedSuccessfully = false; // ตัวแปรสำหรับตรวจสอบว่าการอัปเดตสำเร็จ

            // ตรวจสอบการเปลี่ยนแปลงจำนวนสินค้าจากผู้ใช้
            if (newQuantity.charAt(0) == '+') { // ถ้าเป็น +
                if (isInteger(newQuantity.substring(1))) {
                    int quantityChange = Integer.parseInt(newQuantity.substring(1)); // แปลงเป็นตัวเลขจำนวนเต็ม
                    product.setQuantity(product.getQuantity() + quantityChange); // อัปเดตจำนวนสินค้า
                    updatedSuccessfully = true; // การอัปเดตสำเร็จ
                } else {
                    System.out.println("Error! - Your information is incorrect!"); // ข้อความแสดงข้อผิดพลาด
                }
            } else if (newQuantity.charAt(0) == '-') { // ถ้าเป็น -
                if (newQuantity.length() == 1) {
                    System.out.println("Success " + product.getName() + " has been updated!");
                    return; // ถ้ามีแค่ "-" ไม่เปลี่ยนแปลง
                } else if (isInteger(newQuantity.substring(1))) { // ตรวจสอบว่าเป็นจำนวนเต็มตามมา
                    int quantityChange = Integer.parseInt(newQuantity.substring(1)); // แปลงเป็นจำนวนเต็ม
                    int newTotalQuantity = product.getQuantity() - quantityChange; // ลบจำนวนจากจำนวนเดิม

                    // ตรวจสอบไม่ให้จำนวนสินค้าติดลบ
                    if (newTotalQuantity < 0) {
                        System.out.println("Error! - Your information is incorrect!"); // ข้อความแสดงข้อผิดพลาด
                    } else {
                        product.setQuantity(newTotalQuantity); // อัปเดตจำนวนสินค้า
                        updatedSuccessfully = true; // การอัปเดตสำเร็จ
                    }
                } else {
                    System.out.println("Error! - Your information is incorrect!"); // ข้อความแสดงข้อผิดพลาด
                }
            }
            // แสดงข้อความการอัปเดต
            editFileProduct(products); // บันทึกสินค้าหลังจากแก้ไข
            System.out.println("Success - " + product.getName() + " has been updated!"); // ข้อความแสดงความสำเร็จ
            showPostLoginMenu(scanner);
        }
    }

    // ฟังก์ชันสำหรับแก้ไขไฟล์สินค้าทั้งหมดและเขียนกลับไปยังไฟล์เดิม
    private static void editFileProduct(List<Product> products) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\yothi\\Downloads\\PRODUCT.txt"))) {
            for (Product product : products) {
                // เขียนข้อมูลแต่ละสินค้ากลับเข้าไฟล์โดยคงรูปแบบเดิม
                String line = String.format("%s\t%s\t$%.2f\t%d\t%s",
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getQuantity(),
                        product.getCategoryCode()
                );
                writer.write(line);
                writer.newLine(); // ขึ้นบรรทัดใหม่
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // ฟังก์ชันตรวจสอบว่าข้อความเป็นจำนวนเต็มหรือไม่ (ไม่มีทศนิยม)
    private static boolean isInteger(String str) {
        try {
            Integer.parseInt(str); // ลองแปลงข้อความเป็นจำนวนเต็ม
            return true; // ถ้าแปลงได้แสดงว่าเป็นจำนวนเต็ม
        } catch (NumberFormatException e) {
            return false; // ถ้าแปลงไม่ได้แสดงว่าไม่ใช่จำนวนเต็ม
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