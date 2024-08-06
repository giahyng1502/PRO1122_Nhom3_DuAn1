package FPT.PRO1122.Nhom3.DuAn1.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class GioHang implements Parcelable {
    private String id;
    private String ImagePath;

    private String Title;
    private double Price;
    private int Quantity;
    private double Total;

    public GioHang() {
    }

    protected GioHang(Parcel in) {
        id = in.readString();
        ImagePath = in.readString();
        Title = in.readString();
        Price = in.readDouble();
        Quantity = in.readInt();
        Total = in.readDouble();
    }

    public static final Creator<GioHang> CREATOR = new Creator<GioHang>() {
        @Override
        public GioHang createFromParcel(Parcel in) {
            return new GioHang(in);
        }

        @Override
        public GioHang[] newArray(int size) {
            return new GioHang[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(ImagePath);
        dest.writeString(Title);
        dest.writeDouble(Price);
        dest.writeInt(Quantity);
        dest.writeDouble(Total);
    }
}
