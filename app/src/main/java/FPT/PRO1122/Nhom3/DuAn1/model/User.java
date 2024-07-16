package FPT.PRO1122.Nhom3.DuAn1.model;

public class User {
    private String phoneNumber;
    private String username;
    private String password;

    public User() {
    }

    public User(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User(String phoneNumber, String username, String password) {
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
