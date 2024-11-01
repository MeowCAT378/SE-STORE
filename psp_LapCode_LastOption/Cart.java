public class Cart {
    private String userId;
    private String idProduct;
    private int quantity;

    public Cart(String userId, String idProduct, int quantity) {
        this.userId = userId;
        this.idProduct = idProduct;
        this.quantity = quantity;
    }

    public String getUserId() {
        return userId;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}