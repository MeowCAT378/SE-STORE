/*import java.io.*;
import java.util.*;

public class Store {
    private List<Cart> carts; // ประกาศตัวแปร carts
    public Product[] products; // รายการสินค้า

    // คอนสตรัคเตอร์
    public Store() {
        this.carts = new ArrayList<>(); // สร้าง ArrayList สำหรับ carts
    }

    // เพิ่มเมธอดเพื่อเข้าถึง carts
    public List<Cart> getCarts() {
        return carts;
    }

    // ฟังก์ชันสำหรับอัปเดตไฟล์
    public void updateFile_Order() {
        // เขียนโค้ดเพื่ออัปเดตไฟล์คำสั่งซื้อ
        // ตัวอย่างการเขียน:
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("CART.txt"))) {
            for (Cart order : carts) { // สมมติว่า carts เป็น List<Cart> ของคุณ
                writer.write(order.getUserID() + "\t" + order.getIdProduct() + "\t" + order.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating order file: " + e.getMessage());
        }
    }

    public void creatNewOrder(String userID, String productID, int quantity) {
        // สร้างออเดอร์ใหม่
        Cart newOrder = new Cart(userID, productID, quantity);

        // เพิ่มออเดอร์เข้าไปในลิสต์หรือฐานข้อมูล
        this.carts.add(newOrder);

        // หรือถ้าคุณต้องการบันทึกลงไฟล์
        saveOrderToFile(newOrder);

    }

    // ฟังก์ชันเพื่อบันทึกออเดอร์ลงไฟล์
    public void saveOrderToFile(Cart order) {
        // ตรวจสอบว่ามีไฟล์อยู่แล้วหรือไม่
        File file = new File("CART.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            // เขียนข้อมูลการสั่งซื้อไปยังไฟล์
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(order.getUserID() + "\t" + order.getIdProduct() + "\t" + order.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving order: " + e.getMessage());
        }
    }

    public void updateFile_edit() {
        File file = new File("CART.txt");

        try {
            if (!file.exists()) {
                file.createNewFile(); // สร้างไฟล์ใหม่ถ้าไม่มี
            }

            // เขียนข้อมูลตะกร้าสินค้าลงในไฟล์
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Cart cart : carts) { // สมมติว่า carts เป็น List<Cart> ของคุณ
                    writer.write(cart.getUserID() + "\t" + cart.getIdProduct() + "\t" + cart.getQuantity());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error updating cart file: " + e.getMessage());
        }
    }
*/