package FPT.PRO1122.Nhom3.DuAn1.model;

import java.util.List;

public class OrderHistory {
    private String orderId;
    private String userId;
    private List<MonAnByThien> productList; // Product là lớp đại diện cho một sản phẩm
    private double totalAmount;
    private String orderDate;
    private int status;

    // Constructor không tham số
    public OrderHistory() {
        // Firebase cần một constructor không tham số
    }

    // Constructor với tất cả các thuộc tính
    public OrderHistory(String orderId, String userId, List<MonAnByThien> productList, double totalAmount, String orderDate, int status) {
        this.orderId = orderId;
        this.userId = userId;
        this.productList = productList;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.status = status;
    }

    // Getters và Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<MonAnByThien> getProductList() {
        return productList;
    }

    public void setProductList(List<MonAnByThien> productList) {
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
