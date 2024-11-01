import java.io.*;
import java.util.*;

public class CartManager {
    private static final String CART_FILE = "C:\\Users\\yothi\\Downloads\\CART.txt";
    private static final String PRODUCT_FILE = "C:\\Users\\yothi\\Downloads\\PRODUCT.txt";

    private Map<String, Cart> cartMap = new HashMap<>(); // เก็บตะกร้าสินค้าเป็น Map

    public void loadCart() {
        try (BufferedReader br = new BufferedReader(new FileReader(CART_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                String userId = parts[0];
                String idProduct = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                cartMap.put(idProduct, new Cart(userId, idProduct, quantity)); // เพิ่มข้อมูลลงใน cartMap
            }
        } catch (IOException e) {
            System.out.println("Error reading the cart file.");
        }
    }

    public int getAvailableStock(String productId) {
        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] product = line.split("\t");
                if (product[0].equals(productId)) {
                    return Integer.parseInt(product[3]); // คืนค่าจำนวนสินค้าที่มีอยู่
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading product stock.");
        }
        return 0; // ถ้าไม่พบสินค้า
    }


    public void saveCart() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CART_FILE))) {
            for (Cart cart : cartMap.values()) {
                bw.write(cart.getUserId() + "\t" + cart.getIdProduct() + "\t" + cart.getQuantity());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to the cart file.");
        }
    }

    public void adjustCart(String userId, String idProduct, int quantityChange) {
        Cart cart = cartMap.get(idProduct);
        if (cart != null) {
            // สินค้าอยู่ในตะกร้าแล้ว ให้เพิ่มลดจำนวนในสินค้า
            int newQuantity = quantityChange; // จำนวนใหม่ที่ได้รับการปรับ
            if (newQuantity <= 0) {
                cartMap.remove(idProduct); // ถ้าจำนวนในตะกร้าเหลือ 0 หรือน้อยกว่า ลบออกจากตะกร้า
                System.out.println("ไม่มีสินค้านี้ใน CART");
            } else {
                cart.setQuantity(newQuantity);
                System.out.println("ปรับจำนวนสินค้าในตะกร้าเป็น " + newQuantity);
            }
        } else {
            // ถ้ายังไม่มีสินค้าในตะกร้า ให้เพิ่มใหม่
            if (quantityChange > 0) {
                cartMap.put(idProduct, new Cart(userId, idProduct, quantityChange));
            } else {
                System.out.println("ไม่มีสินค้านี้ใน CART");
            }
        }
    }

    public int getCartQuantity(String userId, String productId) {
        Cart cart = cartMap.get(productId);
        return (cart != null) ? cart.getQuantity() : 0;
    }


    public void updateProductStockForAdjustment(String productId, int quantityChange) {
        try (BufferedReader br = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            List<String[]> updatedProducts = new ArrayList<>();
            String line;

            while ((line = br.readLine()) != null) {
                String[] product = line.split("\t");
                if (product[0].equals(productId)) {
                    int currentStock = Integer.parseInt(product[3]);
                    int newStock = currentStock + quantityChange; // ปรับจำนวนสินค้าในสต็อก
                    product[3] = String.valueOf(newStock);
                    System.out.println("จำนวนสินค้าที่เหลือในสต็อกคือ " + newStock);
                }
                updatedProducts.add(product);
            }

            // เขียนข้อมูลที่อัปเดตกลับลงในไฟล์ PRODUCT.txt
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(PRODUCT_FILE))) {
                for (String[] product : updatedProducts) {
                    bw.write(String.join("\t", product));
                    bw.newLine();
                }
            }

        } catch (IOException e) {
            System.out.println("Error updating product stock.");
        }
    }

}