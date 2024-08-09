package FPT.PRO1122.Nhom3.DuAn1.model;

public class Cart {
    private String cartId;
    private String ImagePath;
    private int foodID;
    private String Title;
    private double Price;
    private int Quantity;
    private double Total;

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public Cart() {
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }


//    public void setId(int id) {
//        this.id = id;
//    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public double getPrice() {
        return Price;
    }
    //
    public void setPrice(double price) {
        Price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getTotal() {
        return Price * Quantity;
    }

    public void setTotal(double total) {
        Total = total;
    }
}

