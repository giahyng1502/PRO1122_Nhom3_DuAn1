package FPT.PRO1122.Nhom3.DuAn1.model;

public class User {
    private String userId;
    private String phoneNumber;
    private String password;
    private String name;
    private String email;
    private String address;
    private String ImageAvatar;
    private int role;

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
    public User() {
    }

    public User(String userId, String phoneNumber, String password, String name, String email, String address, String imageAvatar) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
        ImageAvatar = imageAvatar;
        this.role = 1;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageAvatar() {
        return ImageAvatar;
    }

    public void setImageAvatar(String imageAvatar) {
        ImageAvatar = imageAvatar;
    }
}
