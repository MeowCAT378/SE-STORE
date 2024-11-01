public class Product {
    private String id;
    private String name;
    private double price;
    private int quantity;
    private String categoryCode; // เพิ่มฟิลด์นี้สำหรับรหัสหมวดหมู่สินค้า

    // Constructor
    public Product(String id, String name, double price, int quantity, String categoryCode) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.categoryCode = categoryCode; // กำหนดค่า categoryCode
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategoryCode() { // สร้าง getter สำหรับ categoryCode
        return categoryCode;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }
}
