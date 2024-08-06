package FPT.PRO1122.Nhom3.DuAn1.model;

import java.util.List;

public class OrderHistory {
    private String orderId;
    private String user;
    private List<GioHang> productList; // GioHang là lớp đại diện cho một sản phẩm
    private double totalAmount;
    private String phone;
    private String address;
    private String orderDate;
    private int status;

    public OrderHistory() {
        // Required empty constructor
    }

    // Getters and setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<GioHang> getProductList() {
        return productList;
    }

    public void setProductList(List<GioHang> productList) {
        this.productList = productList;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getStatus() {

        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



}
