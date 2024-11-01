/*public class OrderHandler {
    private Store store; // ประกาศตัวแปร store

    public OrderHandler() {
        this.store = new Store(); // สร้าง instance ของ Store
    }

    public void processOrder(String userID_current, String productID_choose) {
        int index = 0;
        int index_Order = -1;
        boolean newOrder = true;

        // ตรวจสอบในตะกร้า
        for (Cart item : store.getCarts()) { // ใช้เมธอด getCarts()
            if (item.getUserID().equalsIgnoreCase(userID_current) && item.getIdProduct().equalsIgnoreCase(productID_choose)) {
                newOrder = false;
                index_Order = index;
                break; // ออกจากลูปเมื่อเจอสินค้าแล้ว
            }
            index++;
        }

        if (index_Order == -1) {
            System.out.println("Product not found in cart for this user.");
        }
    }
}
*/