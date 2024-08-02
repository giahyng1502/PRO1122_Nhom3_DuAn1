package FPT.PRO1122.Nhom3.DuAn1.model;

public class GioHang {
    private String id;
    private String ImagePath;

    private String Title;
    private double Price;
    private int Quantity;
    private double Total;

    public GioHang() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String  getId() {
        return id;
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
