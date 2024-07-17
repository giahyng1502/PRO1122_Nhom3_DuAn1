package FPT.PRO1122.Nhom3.DuAn1.model;

public class Profile {
    private String name;
    private String address;
    private String email;
    private String gender;

    public Profile() {}

    public Profile(String name, String address, String email, String gender) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
