package FPT.PRO1122.Nhom3.DuAn1.model;

public class MonAn {
    private String monAnID;
    private String tenMon;
    private String moTa;
    private String giaMon;
    private String imageMon;

    public MonAn(String monAnID, String tenMon, String moTa, String giaMon, String imageMon) {
        this.monAnID = monAnID;
        this.tenMon = tenMon;
        this.moTa = moTa;
        this.giaMon = giaMon;
        this.imageMon = imageMon;
    }

    public MonAn() {
    }

    public String getMonAnID() {
        return monAnID;
    }

    public void setMonAnID(String monAnID) {
        this.monAnID = monAnID;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getGiaMon() {
        return giaMon;
    }

    public void setGiaMon(String giaMon) {
        this.giaMon = giaMon;
    }

    public String getImageMon() {
        return imageMon;
    }

    public void setImageMon(String imageMon) {
        this.imageMon = imageMon;
    }
}
