package FPT.PRO1122.Nhom3.DuAn1.model;

public class DanhMucMonAn {
    private int id;
    private String ImagePath;
    private String Name;

    public DanhMucMonAn() {
    }

    @Override
    public String toString() {
        return  Name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
