import java.io.*;
import java.util.*;

public class Main {
    // Map เก็บหมวดหมู่สินค้า (เช่น หมวดอาหาร, เครื่องดื่ม)
    private static final Map<Integer, String> categories = new LinkedHashMap<>();
    // Map เก็บสินค้าจัดหมวดหมู่โดยเก็บสินค้าที่อยู่ในแต่ละหมวด
    private static final Map<Integer, List<Product>> productsByCategory = new HashMap<>();
    // ไฟล์เก็บข้อมูลสมาชิก
    private static final String MEMBER_FILE = "C:\\Users\\yothi\\Downloads\\MEMBER.txt";
    // ไฟล์เก็บข้อมูลสินค้า
    private static final String PRODUCT_FILE = "C:\\Users\\yothi\\Downloads\\PRODUCT.txt";
    //ไฟล์เก็บข้อมูลตะกร้า
    private static final String CART_FILE = "C:\\Users\\yothi\\Downloads\\CART.txt";
    // ตัวแปรเก็บจำนวนครั้งในการพยายามล็อกอิน
    private static int loginAttempts = 0;

    public static void main(String[] args) {
        loadCategories(); // โหลดข้อมูลหมวดหมู่จากไฟล์
        loadProducts();   // โหลดข้อมูลสินค้าและเชื่อมโยงกับหมวดหมู่สินค้า

        // วนลูปเพื่อแสดงเมนูต้อนรับและดำเนินการตามที่ผู้ใช้เลือก
        while (true) {
            displayWelcomeMenu();
        }
    }

    // ฟังก์ชันแสดงเมนูต้อนรับเมื่อเริ่มโปรแกรม
    private static void displayWelcomeMenu() {
        Scanner scanner = new Scanner(System.in); // สร้าง Scanner สำหรับรับค่าจากผู้ใช้
        System.out.println("===== SE STORE =====");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Select (1-2) : ");  // ขอให้ผู้ใช้เลือกตัวเลือกจากเมนู
        while (true) {
            String choice = scanner.next();
            while (!choice.equals('1') || !choice.equals('2')) {
                // ถ้าผู้ใช้เลือก 1 ให้ไปที่ฟังก์ชัน login
                if (choice.equals("1")) {
                    login(scanner);
                } else if (choice.equals("2")) {
                    // ถ้าผู้ใช้เลือก 2 ให้ออกจากโปรแกรม
                    System.out.println("Thank you for using our service :3");
                    System.exit(0);
                } else {
                    // ถ้าผู้ใช้ใส่ตัวเลือกที่ไม่ถูกต้อง ให้แสดงข้อความแจ้งเตือน
                    System.out.println("Invalid choice, please select 1 or 2.");
                    break;
                }
            }
        }
    }

    // ฟังก์ชัน login สำหรับตรวจสอบข้อมูลการเข้าสู่ระบบของผู้ใช้
    private static void login(Scanner scanner) {
        // วนลูปจนกว่าจะมีการเข้าสู่ระบบที่ถูกต้อง
        while (true) {
            System.out.println("===== LOGIN =====");
            System.out.print("Email : ");
            String inputEmail = scanner.next();  // รับอีเมลจากผู้ใช้
            System.out.print("Password : ");
            String inputPassword = scanner.next();  // รับรหัสผ่านจากผู้ใช้

            boolean found = false; // ตัวแปรตรวจสอบว่าพบสมาชิกหรือไม่
            try (BufferedReader br = new BufferedReader(new FileReader(MEMBER_FILE))) {
                String line;

                // อ่านไฟล์สมาชิกทีละบรรทัด
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("\t"); // แยกข้อมูลสมาชิกแต่ละส่วนด้วย tab
                    if (parts.length == 7) {
                        String email = parts[3];
                        String password = parts[4];

                        // ตรวจสอบว่าข้อมูลอีเมลและรหัสผ่านตรงกันหรือไม่
                        if (email.equals(inputEmail)) {
                            if (isPasswordCorrect(password, inputPassword)) {
                                found = true;
                                loginAttempts = 0; // รีเซ็ตจำนวนครั้งการล็อกอิน

                                // ตรวจสอบสถานะบัญชีและแสดงเมนูที่เหมาะสม
                                handleAccountStatus(parts, password, scanner);
                                break;
                            }
                        }
                    }
                }
            }catch (IOException e) {
                System.out.println("Error reading the member file.");
            }

            // ถ้าไม่พบอีเมลหรือรหัสผ่าน ให้แสดงข้อผิดพลาดและเพิ่มจำนวนการพยายามล็อกอิน
            if (!found) {
                handleLoginFailure(scanner);
            }
        }
    }

    private static void handleLoginFailure(Scanner scanner) {
        loginAttempts++;
        if (loginAttempts >= 3) {
            System.out.println("====================");
            System.out.println("Error! - Email or Password is Incorrect (" + loginAttempts + ")");
            System.out.println("Sorry, Please try again later :(");
            loginAttempts = 0; // รีเซ็ตจำนวนครั้งการล็อกอิน
            displayWelcomeMenu();
        }else {
            System.out.println("====================");
            System.out.println("Error! - Email or Password is Incorrect (" + loginAttempts + ")");
        }

    }

    // ฟังก์ชันตรวจสอบว่ารหัสผ่านที่ผู้ใช้กรอกถูกต้องหรือไม่
    private static boolean isPasswordCorrect(String actualPassword, String inputPassword) {
        // ดึงตัวเลขจากตำแหน่งที่เก็บรหัส 6 หลัก
        if (actualPassword.length() >= 17) {
            String correctPassword = "" + actualPassword.charAt(9) + actualPassword.charAt(10)
                    + actualPassword.charAt(13) + actualPassword.charAt(14)
                    + actualPassword.charAt(15) + actualPassword.charAt(16);

            return inputPassword.equals(correctPassword); // ตรวจสอบความถูกต้องของรหัสผ่าน
        }
        return false;
    }

    // ฟังก์ชันจัดการการแสดงผลตามสถานะบัญชี
    private static void handleAccountStatus(String[] parts, String password, Scanner scanner) {
        char accountStatus = password.charAt(2); // ตรวจสอบสถานะบัญชีจากรหัสผ่าน (ตำแหน่งที่ 2)
        String idMember = parts[0];
        String firstName = parts[1];
        String lastName = parts[2];
        String email = parts[3];
        String phone = parts[5];
        double points = Double.parseDouble(parts[6]);

        // ถ้าบัญชีสถานะ Active
        if (accountStatus == '1') {
            String role = getRole(password); // ดึงบทบาทจากรหัสผ่าน
            Member member = new Member(idMember, firstName, lastName, email, phone, points); // สร้าง object Member
            displayMenu(role, member); // แสดงเมนูตามบทบาทของผู้ใช้

            // วนลูปเพื่อรอคำสั่งจากผู้ใช้
            while (true) {
                String choice = scanner.next();
                // ถ้าผู้ใช้เป็น STAFF ให้แสดงเมนูเพิ่มเติม
                if (role.equals("STAFF")) {
                    if (choice.equals("1")) {
                        showCategories(scanner,password); // แสดงหมวดหมู่สินค้า
                    } else if (choice.equals("2")) {
                        addMember(scanner, member, role); // เพิ่มสมาชิกใหม่
                    } else if (choice.equals("3")) {
                        editMember(role,member, scanner); // แก้ไขสมาชิก
                    } else if (choice.equals("4")) {
                        editProduct(scanner,role,member);
                    }else if (choice.equals("5")){
                        displayWelcomeMenu(); // ออกจากระบบ
                    } else {
                        System.out.println("Invalid choice, please select 1, 2, 3, 4 or 5.");
                    }
                } else {  // ถ้าผู้ใช้เป็นสมาชิกธรรมดา
                    if (choice.equals("1")) {
                        showCategories(scanner,password); // แสดงหมวดหมู่สินค้า
                    } else if (choice.equals("2")) {
                        orderProduct(scanner, role, member);
                    } else if (choice.equals("3")){
                        displayWelcomeMenu(); // ออกจากระบบ
                        break;
                    } else {
                        System.out.println("Invalid choice, please select 1, 2 or 3.");
                    }
                }
            }
        } else if (accountStatus == '0') {  // Non-active (บัญชีหมดอายุ)
            System.out.println("====================");
            System.out.println("Error! - Your Account is Expired!");  // แสดงข้อผิดพลาดเมื่อบัญชีหมดอายุ
            System.out.println("====================");
        }
    }
    private static void orderProduct(Scanner scanner, String role, Member member) {
        List<String[]> productList = new ArrayList<>();
        CartManager cartManager = new CartManager();
        cartManager.loadCart(); // โหลดข้อมูลตะกร้าจากไฟล์

        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            int count = 1;
            System.out.println("=========== SE STORE's Products ===========");
            System.out.printf("#\t%-15s\tPrice (฿)\tQuantity\n", "Name");

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 4) {
                    double priceInBaht = Double.parseDouble(parts[2].replace("$", "")) * 34.00;
                    System.out.printf("%d\t%-15s\t%.2f\t\t%s\n", count, parts[1], priceInBaht, parts[3]);
                    productList.add(parts);
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
                    displayMenu(role, member);
                    break;
                } else if (input.equals("1")) {
                    showHowToOrder();
                } else if (input.equals("2")) {
                    listProducts(productList);
                } else if (input.isEmpty()) {
                    // ถ้า input ว่างเปล่าไม่ทำอะไร
                } else {
                    // รับค่าข้อมูลการสั่งซื้อและอัปเดตสินค้าในตะกร้า
                    handleOrderInput(input, productList, cartManager, member.getId());
                }
                System.out.print("Enter : ");
            }

        } catch (IOException e) {
            System.out.println("Error reading the product file.");
        }
    }

    private static void handleOrderInput(String input, List<String[]> productList, CartManager cartManager, String memberId) {
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

    private static void listProducts(List<String[]> productList) {
        System.out.println("=========== SE STORE's Products ===========");
        System.out.printf("#\t%-15s\tPrice (฿)\tQuantity\n", "Name");
        int count = 1;
        for (String[] product : productList) {
            double priceInBaht = Double.parseDouble(product[2].replace("$", "")) * 34.00;
            System.out.printf("%d\t%-15s\t%.2f\t\t%s\n", count, product[1], priceInBaht, product[3]);
            count++;
        }
        System.out.println("===========================================");
    }

    private static void editProduct(Scanner scanner, String role, Member member) {
        List<String[]> productList = new ArrayList<>();
        double exchangeRate = 34.00; // อัตราแลกเปลี่ยนจากดอลลาร์เป็นบาท

        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            System.out.println("=========== SE STORE's Products ===========");
            System.out.printf("#\t%-15s\tPrice (฿)\tQuantity\n", "Name");
            int count = 1;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 3) { // ตรวจสอบว่ามีข้อมูลสินค้าครบถ้วน
                    double priceInDollar = Double.parseDouble(parts[2].replace("$", "")); // เอา $ ออกแล้วแปลงเป็น double
                    double priceInBaht = priceInDollar * exchangeRate; // คูณด้วยอัตราแลกเปลี่ยน
                    System.out.printf("%d\t%-12s\t%-8.2f\t%-8s\n", count, parts[1], priceInBaht, parts[3]);
                    productList.add(parts);
                    count++;
                }
            }
            System.out.println("===========================================");
            System.out.println("Type Product Number, You want to edit or Press Q to Exit");
            System.out.print("Select (1-" + (count - 1) + ") : ");
            while (true) {
                String choice = scanner.next();
                if (choice.equalsIgnoreCase("Q")) {
                    return;
                }
                try {

                    int selectedIndex = Integer.parseInt(choice) - 1;
                    if (selectedIndex >= 0 && selectedIndex < productList.size()) {
                        editProductInfo(scanner, productList.get(selectedIndex), role, member);
                        break;
                    } else {
                        System.out.println("Invalid selection.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid selection.");
                }
            }

        } catch(IOException e){
            System.out.println("Error reading the product file.");
        }

    }


    private static void editProductInfo(Scanner scanner, String[] productData, String role, Member member) {
        System.out.println("==== Edit info of " + productData[1] + " ====");
        System.out.println("Type new info or Hyphen (-) for none edit.");
        System.out.print("Name : ");
        String newName = scanner.next();
        System.out.print("Quantity (+ or -) : ");
        String quantityInput = scanner.next();

        if (!newName.equals("-")) {
            productData[1] = newName; // เปลี่ยนชื่อสินค้า
        }

        int quantityChange = 0;
        if (!quantityInput.equals("-")) {
            if (quantityInput.startsWith("+") || quantityInput.startsWith("-")) {
                try {

                    quantityChange = Integer.parseInt(quantityInput);
                    int currentQuantity = Integer.parseInt(productData[3]);
                    int newQuantity = currentQuantity + quantityChange;

                    if (newQuantity < 0) {
                        newQuantity = 0;
                    }
                    productData[3] = String.valueOf(newQuantity); // ปรับปรุงจำนวน
                    System.out.println("Success - " + productData[1] + " has been updated!");
                    System.out.println("============================");
                    updateProductFile(productData);
                    displayMenu(role, member);
                } catch (NumberFormatException e) {
                    System.out.println("Error! - Your Information are Incorrect!");
                    System.out.println("============================");
                    displayMenu(role, member);
                }
            } else {
                // ถ้าไม่มี + หรือ - นำหน้าตัวเลข จะแสดงข้อความแสดงข้อผิดพลาด
                System.out.println("Error! - Your Information are Incorrect!");
                System.out.println("============================");
                displayMenu(role, member);
            }
        }else {
            System.out.println("Success - " + productData[1] + " has been updated!");
            System.out.println("============================");
            updateProductFile(productData);
            displayMenu(role, member);
        }
    }

    private static void updateProductFile(String[] updatedProduct) {
        List<String> allProducts = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts[0].equals(updatedProduct[0])) {
                    allProducts.add(String.join("\t", updatedProduct)); // แทนที่ข้อมูลที่แก้ไขแล้ว
                } else {
                    allProducts.add(line); // ข้อมูลสินค้าที่ไม่ได้แก้ไข
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the product file.");
        }

        // เขียนข้อมูลทั้งหมดกลับไปที่ไฟล์
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PRODUCT_FILE))) {
            for (String product : allProducts) {
                bw.write(product);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to the product file.");
        }
    }

    private static void editMember(String role, Member member, Scanner scanner) {
        List<String[]> memberList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(MEMBER_FILE))) {
            String line;
            System.out.println("===== SE STORE's Member =====");
            System.out.printf("#\t%-28s\t\t%s\n","Name", "Email");
            int count = 1;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 7) {
                    System.out.printf("%d\t%-28s\t\t%s\n", count, parts[1] + " " + parts[2], parts[3]); // แสดงชื่อและอีเมล
                    memberList.add(parts);
                    count++;
                }
            }
            System.out.println("============================");
            System.out.println("Type Member Number, You want to edit or Press Q to Exit");
            System.out.print("Select (1-" + (count - 1) + ") : ");

            String choice = scanner.next();
            if (choice.equalsIgnoreCase("Q")) {
                displayMenu(role,member); // กลับไปเมนูก่อนหน้า
            } else {
                try {
                    int selectedIndex = Integer.parseInt(choice) - 1;
                    if (selectedIndex >= 0 && selectedIndex < memberList.size()) {
                        editMemberInfo(scanner, memberList.get(selectedIndex), role, member);
                    } else {
                        System.out.println("Invalid selection.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the member file.");
        }
    }

    private static void editMemberInfo(Scanner scanner, String[] memberData, String role, Member member) {
        System.out.println("==== Edit info of " + memberData[1] + " " + memberData[2] + " ====");
        System.out.println("Type new info or Hyphen (-) for none edit.");
        System.out.print("Firstname : ");
        String newFirstname = scanner.next();
        System.out.print("Lastname : ");
        String newLastname = scanner.next();
        System.out.print("Email : ");
        String newEmail = scanner.next();
        System.out.print("Phone : ");
        String newPhone = scanner.next();

        if (!newFirstname.equals("-")) {
            memberData[1] = newFirstname;
        }
        if (!newLastname.equals("-")) {
            memberData[2] = newLastname;
        }
        if (!newEmail.equals("-")) {
            memberData[3] = newEmail;
        }
        if (!newPhone.equals("-")) {
            memberData[5] = newPhone;
        }
        // ตรวจสอบความถูกต้องของข้อมูลที่กรอกใหม่
        if (validateMemberInfo(memberData[1], memberData[2], memberData[3], memberData[5])) {
            updateMemberFile(memberData);  // อัปเดตข้อมูลลงไฟล์
            System.out.println("Success - Member has been updated!");
            System.out.println("============================");
            displayMenu(role, member);
        } else {
            System.out.println("Error! - Your Information is Incorrect!");
            System.out.println("============================");
            displayMenu(role, member);
        }
    }

    private static void updateMemberFile(String[] updatedMember) {
        List<String> allMembers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(MEMBER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts[0].equals(updatedMember[0])) {
                    allMembers.add(String.join("\t", updatedMember)); // แทนที่ข้อมูลที่แก้ไขแล้ว
                } else {
                    allMembers.add(line); // ข้อมูลสมาชิกที่ไม่ได้แก้ไข
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the member file.");
        }

        // เขียนข้อมูลทั้งหมดกลับไปที่ไฟล์
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MEMBER_FILE))) {
            for (String member : allMembers) {
                bw.write(member);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to the member file.");
        }
    }

    // ฟังก์ชันดึงบทบาทของผู้ใช้จากรหัสผ่าน
    private static String getRole(String password) {
        switch (password.charAt(6)) {
            case '0': return "STAFF";
            case '1': return "Regular Member";
            case '2': return "Silver Member";
            case '3': return "Gold Member";
            default: return "Unknown";
        }
    }

    // ฟังก์ชันแสดงเมนูหลังจากผู้ใช้เข้าสู่ระบบแล้ว
    private static void displayMenu(String role, Member member) {
        System.out.println("===== SE STORE =====");
        System.out.println("Hello, " + member.getMaskedName() + " (" + role + ")");
        System.out.println("Email: " + member.getMaskedEmail());
        System.out.println("Phone: " + member.getFormattedPhone());
        System.out.println("You have " + member.getPoints() + " Point");
        System.out.println("====================");

        switch (role) {
            case "STAFF":
                System.out.println("1. Show Category");
                System.out.println("2. Add Member");
                System.out.println("3. Edit Member");
                System.out.println("4. Edit Product");
                System.out.println("5. Logout");
                System.out.println("====================");
                System.out.print("Select (1-5) : ");
                break;
            default:
                System.out.println("1. Show Category");
                System.out.println("2. Order Product");
                System.out.println("3. Logout");
                System.out.println("====================");
                System.out.print("Select (1-3) : ");
                break;
        }
    }

    private static boolean addMember(Scanner scanner,Member member,String role) {
        System.out.println("===== ADD MEMBER =====");
        System.out.print("First Name: ");
        String firstName = scanner.next();
        System.out.print("Last Name: ");
        String lastName = scanner.next();
        System.out.print("Email: ");
        String email = scanner.next();
        System.out.print("Phone: ");
        String phone = scanner.next();


        if (validateMemberInfo(firstName, lastName, email, phone)) {
            double points = 0.00;
            String newId = getNextId();
            String password = generateRandomPassword();
            String newMemberData = generateMemberData(newId,firstName, lastName, email, phone, password,points);
            writeToFile(newMemberData, MEMBER_FILE);
            System.out.println("Success - New Member has been created!");
            System.out.println(firstName + "'s Password is " + password.charAt(9) + password.charAt(10)
                    + password.charAt(13)+ password.charAt(14)
                    + password.charAt(15) + password.charAt(16));
            displayMenu(role,member);
            return true;
        } else {
            System.out.println("Error! - Your Information is Incorrect!");
            displayMenu(role, member);
            return false;
        }
    }

    private static String getNextId() {
        int maxId = 9000; // ค่าเริ่มต้นสำหรับ ID
        try (BufferedReader br = new BufferedReader(new FileReader(MEMBER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 7) {
                    int id = Integer.parseInt(parts[0]);
                    if (id > maxId) {
                        maxId = id;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the member file.");
        }
        return String.valueOf(maxId + 1);
    }

    public static String generateRandomPassword() {
        char[] password = new char[19];
        // Randomize indices 0, 1, 3, 4, 5, 7, 8, 11, 12, 17, 18
        for (int i : new int[]{0, 1, 3, 4, 5, 7, 8, 11, 12, 17, 18}) {
            password[i] = getRandomChar();
        }
        // Randomize digits for indices 9, 10, 13, 14, 15, 16
        for (int i : new int[]{9, 10, 13, 14, 15, 16}) {
            password[i] = getRandomDigit();
        }
        // Set account status to 1 (active) at index 2
        password[2] = '1';
        // Set role to 1 (Regular Member) at index 6
        password[6] = '1';

        return new String(password);
    }

    private static char getRandomChar() {
        return (char) ('A' + new Random().nextInt(26)); // Random uppercase letter
    }

    private static char getRandomDigit() {
        return (char) ('0' + new Random().nextInt(10)); // Random digit
    }

    public static boolean validateMemberInfo(String firstName, String lastName, String email, String phone) {
        return firstName.length() > 2 &&
                lastName.length() > 2 &&
                email.length() > 2 && email.contains("@") &&
                phone.length() == 10 && phone.matches("\\d+");
    }

    private static String generateMemberData(String id, String firstName, String lastName, String email, String phone, String password,double points) {
        // ใช้สำหรับสร้างข้อมูลสมาชิกใหม่
        return id + "\t" + firstName + "\t" + lastName + "\t" + email + "\t" + password + "\t" + phone + "\t" + String.format("%.2f", points);
    }

    public static void writeToFile(String data, String filename) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(data + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ฟังก์ชันแสดงหมวดหมู่สินค้า
    private static void showCategories(Scanner scanner, String password) {
        while (true) {
            System.out.println("===== SE STORE's Product Categories =====");
            int index = 1;
            for (Map.Entry<Integer, String> entry : categories.entrySet()) {
                System.out.printf("%d\t%s\n", index++, entry.getValue());
            }
            System.out.println("=========================================");
            System.out.print("Select Category to Show Product (1-" + categories.size() + ") or Q for exit: ");
            String choice = scanner.next(); // รับหมวดหมู่ที่เลือกจากผู้ใช้

            if (choice.equalsIgnoreCase("Q")) { // ถ้าผู้ใช้เลือก Q กลับไปเมนูหลัก
                displayWelcomeMenu();
                return;
            }
            try {
                int categoryIndex = Integer.parseInt(choice); // แปลงตัวเลือกเป็นตัวเลข
                if (categoryIndex >= 1 && categoryIndex <= categories.size()) {
                    int categoryId = (int) categories.keySet().toArray()[categoryIndex - 1]; // หา categoryId
                    showProductsInCategory(categoryId, scanner, password); // แสดงสินค้าในหมวดหมู่ที่เลือก
                } else {
                    System.out.println("Invalid category number.");
                }
            } catch (NumberFormatException e) { // จัดการการป้อนค่าที่ไม่ใช่ตัวเลข
                System.out.println("Invalid input. Please select a number or 'Q' to exit.");
            }
        }
    }

    private static double calculateDiscountedPrice(double originalPrice, String role) {
        double discount = 0;
        switch (role) {
            case "Silver Member":
                discount = 0.05; // ลด 5%
                break;
            case "Gold Member":
                discount = 0.10; // ลด 10%
                break;
            // ไม่มีการลดราคาสำหรับ Staff และ Regular Members
            case "STAFF":
            case "Regular Member":
            default:
                return originalPrice;
        }
        return originalPrice * (1 - discount);
    }

    // ฟังก์ชันแสดงสินค้าจากรายการ
    private static void displayProductList(List<Product> products, int categoryId, String password) {
        String role = getRole(password);
        if (role.equals("STAFF") || role.equals("Regular Member")) {
            System.out.println("============ " + categories.get(categoryId) + " ============");
            System.out.printf("#\t%-12s\t%-8s\t%-8s\n", "Name", "Price (฿)", "Quantity");
        }else if(role.equals("Silver Member") || role.equals("Gold Member")) {
            System.out.println("============ " + categories.get(categoryId) + " ============");
            System.out.printf("#\t%-12s\t%-16s\t   %-8s\n", "Name", "Price (฿)", "Quantity");
        }

        int index = 1;
        for (Product product : products) {
            double originalPrice = product.getPrice();
            double discountedPrice = calculateDiscountedPrice(originalPrice, role);
            if (role.equals("STAFF") || role.equals("Regular Member")) {
                System.out.printf("%d\t%-12s\t%-8.2f\t%-8d\n", index++, product.name, originalPrice, product.quantity);
            }else if(role.equals("Silver Member") || role.equals("Gold Member")) {
                System.out.printf("%-3d\t%-15s\t%-8.2f(%7.2f)\t%5d\n", index++, product.name, discountedPrice, originalPrice, product.quantity);
            }
        }
        System.out.println("================================");
    }

    // ฟังก์ชันแสดงสินค้าภายในหมวดหมู่
    private static void showProductsInCategory(int categoryId, Scanner scanner, String password) {
        String role = getRole(password);
        List<Product> products = productsByCategory.get(categoryId);
        if (products != null && !products.isEmpty()) {
            if (role.equals("STAFF") || role.equals("Regular Member")) {
                System.out.println("============ " + categories.get(categoryId) + " ============");
                System.out.printf("#\t%-12s\t%-8s\t%-8s\n", "Name", "Price (฿)", "Quantity");
            }else if(role.equals("Silver Member") || role.equals("Gold Member")) {
                System.out.println("============ " + categories.get(categoryId) + " ============");
                System.out.printf("#\t%-12s\t%-16s\t   %-8s\n", "Name", "Price (฿)", "Quantity");
            }
            int index = 1;
            for (Product product : products) {
                double originalPrice = product.getPrice();
                double discountedPrice = calculateDiscountedPrice(originalPrice, role);
                if (role.equals("STAFF") || role.equals("Regular Member")) {
                    System.out.printf("%d\t%-12s\t%-8.2f\t%-8d\n", index++, product.name, originalPrice, product.quantity);
                }else if(role.equals("Silver Member") || role.equals("Gold Member")) {
                    System.out.printf("%-3d\t%-15s\t%-8.2f(%7.2f)\t%5d\n", index++, product.name, discountedPrice, originalPrice, product.quantity);
                }
            }
            System.out.println("================================");

            // แสดงเมนูเพิ่มเติมให้เลือกการเรียงลำดับ
            while (true) {
                System.out.println("1. Show Name By DESC");
                System.out.println("2. Show Quantity By ASC");
                System.out.print("or Press Q to Exit : ");
                String choice = scanner.next();

                if (choice.equalsIgnoreCase("1")) {
                    // เรียงลำดับตามชื่อสินค้าแบบ DESC
                    products.sort(Comparator.comparing(Product::getName).reversed());
                    displayProductList(products, categoryId, password);
                } else if (choice.equalsIgnoreCase("2")) {
                    // เรียงลำดับตามจำนวนสินค้าแบบ ASC
                    products.sort(Comparator.comparingInt(Product::getQuantity));
                    displayProductList(products, categoryId, password);
                } else if (choice.equalsIgnoreCase("Q")) {
                    break; // ออกจากเมนูการเรียงลำดับ
                } else {
                    System.out.println("Invalid choice, please select 1 or 2 or Q.");
                    System.out.println("================================");
                }
            }
        } else {
            System.out.println("No products found for this category.");
        }
    }

    // ฟังก์ชันโหลดหมวดหมู่จากไฟล์
    private static void loadCategories() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\yothi\\Downloads\\CATEGORY.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 2) {
                    int categoryId = Integer.parseInt(parts[0]); // รหัสหมวดหมู่
                    String categoryName = parts[1];              // ชื่อหมวดหมู่
                    categories.put(categoryId, categoryName);    // เก็บใน Map
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading categories file.");
            e.printStackTrace();
        }
    }

    // ฟังก์ชันโหลดสินค้าและเชื่อมต่อกับหมวดหมู่
    private static void loadProducts() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\yothi\\Downloads\\PRODUCT.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 5) {
                    int productId = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2].replace("$", "")); // แปลงราคาจากดอลลาร์
                    int quantity = Integer.parseInt(parts[3]);
                    int categoryId = Integer.parseInt(parts[4]);

                    Product product = new Product(productId, name, price, quantity); // สร้าง object Product
                    productsByCategory.computeIfAbsent(categoryId, k -> new ArrayList<>()).add(product); // จัดกลุ่มสินค้าในหมวดหมู่
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading products file.");
            e.printStackTrace();
        }
    }
}