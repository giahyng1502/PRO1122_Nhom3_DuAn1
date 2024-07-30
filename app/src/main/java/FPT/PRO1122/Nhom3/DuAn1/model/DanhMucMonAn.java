package FPT.PRO1122.Nhom3.DuAn1.model;

import java.io.Serializable;

public class DanhMucMonAn implements Serializable {
    private int Id;
    private String ImagePath;
    private String Name;

    public DanhMucMonAn() {
    }

    @Override
    public String toString() {
        return  Name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
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
