package FPT.PRO1122.Nhom3.DuAn1.model;

import java.io.Serializable;

public class DanhMucMonAn implements Serializable {
    private int Id;
    private String ImagePath;
    private String Name;


    public DanhMucMonAn() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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
